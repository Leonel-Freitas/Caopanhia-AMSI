package com.example.caopanhia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;
import com.google.android.material.navigation.NavigationView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ClientMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    public static final String SHARED = "USER_TOKEN";
    public static final String TOKEN = "TOKEN";
    public static final String USERNAME = "USERNAME";
    public static final String ID_USER = "ID_USER";
    public static final String OPERACAO = "OPERACAO";
    public static final int ADD = 10, EDIT = 20, DELETE = 30;
    RequestQueue requestQueue;
    MqttAndroidClient client;
    static String topicStr = "testeAndroid";
    Vibrator vibrator;
    Ringtone myRingtone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        String token = getIntent().getStringExtra(TOKEN);
        int id_user = getIntent().getIntExtra(ID_USER, 0);
        if (token != null){
            SharedPreferences userToken = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userToken.edit();
            editor.putString(TOKEN, token);
            editor.putInt(ID_USER, id_user);
            editor.apply();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.clientdDawerLayout);
        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.ndOpen, R.string.ndClose);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        carregarCabecalho();

        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        loadHomeFragment();


        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        myRingtone = RingtoneManager.getRingtone(this.getApplicationContext(), uri);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        requestQueue = Volley.newRequestQueue(this);
        String clientId = MqttClient.generateClientId();
        // client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST,clientId);
        client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.mqttdashboard.com:1883",
                        clientId);
        try {
            IMqttToken tokenmqtt = client.connect();
            tokenmqtt.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Subscribe();
                }


                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems


                }
            });
        } catch (MqttException e) {
        e.printStackTrace();

        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                Toast.makeText(ClientMainActivity.this, message.toString(), Toast.LENGTH_LONG).show();

                vibrator.vibrate(500);

                myRingtone.play();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public void Subscribe() {
        try {
            client.subscribe(topicStr, 0);
            SingletonGestorCaopanhia.getInstance(getApplicationContext()).getAllMarcacoesAPI(this);
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    private void carregarCabecalho(){
        String username = getIntent().getStringExtra(USERNAME);

        View headerView = navigationView.getHeaderView(0);
        TextView tvUsername = headerView.findViewById(R.id.tvUsername);
        tvUsername.setText(username);
    }

    private boolean loadHomeFragment() {
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setCheckable(true);
        return onNavigationItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item .getItemId()) {
            case R.id.navHome:
                fragment = new HomeClientFragment();
                setTitle(item.getTitle());
                break;
            case R.id.navPet:
                fragment = new PetsListFragment();
                setTitle(item.getTitle());
                break;
            case R.id.navAppointment:
                fragment = new AppointmentListFragment();
                setTitle(item.getTitle());
                break;
            case R.id.navPackages:
                fragment = new PackagesListFragment();
                setTitle(item.getTitle());
                break;
            case R.id.navMap:
                fragment = new MapsFragment();
                setTitle(item.getTitle());
                break;
            case R.id.navInfoCaopanhia:
                fragment = new InfoFragment();
                setTitle(item.getTitle());
                break;
            case R.id.navInfoApp:
                fragment = new AppInfoFragment();
                setTitle(item.getTitle());
                break;
            case R.id.navLogout:

                SharedPreferences userToken = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
                userToken.edit().clear().apply(); //ou .commit()
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();


                break;
        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}