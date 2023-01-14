package com.example.caopanhia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

 import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response;
 import com.android.volley.VolleyError;
 import com.android.volley.toolbox.JsonArrayRequest;
 import com.android.volley.toolbox.JsonObjectRequest;
 import com.android.volley.toolbox.Volley;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
 import org.json.JSONObject;





import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import javax.net.ssl.HttpsURLConnection;

//TODO: os pedidos à API devem estar na classe Singleton, neste momento já a devia ter criado
public class API extends AppCompatActivity {
    private TextView subText;
    RequestQueue requestQueue;
    MqttAndroidClient client;
    static String MQTTHOST = "tcp://192.168.1.103:1883";
    static String topicStr = "testeAndroid";
    Vibrator vibrator;

    Ringtone myRingtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        myRingtone = RingtoneManager.getRingtone(this.getApplicationContext(), uri);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        subText = findViewById(R.id.ApiText);
        requestQueue = Volley.newRequestQueue(this);
        String clientId = MqttClient.generateClientId();
       // client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST,clientId);
        client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.mqttdashboard.com:1883",
                        clientId);


        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(API.this, "connected", Toast.LENGTH_LONG).show();
                    Subscribe();
                }


                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(API.this, "connection failed", Toast.LENGTH_LONG).show();

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
            Toast.makeText(API.this, message.toString(), Toast.LENGTH_LONG).show();

            vibrator.vibrate(500);

            myRingtone.play();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public  void pub(View v){
        String topic = topicStr;
        String message = "Hello world";

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }





    public void Subscribe() {
        try {
            client.subscribe(topicStr, 0);
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void con(View v){
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(API.this, "connected", Toast.LENGTH_LONG).show();
                    Subscribe();
                }


                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(API.this, "connection failed", Toast.LENGTH_LONG).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }



    }
    public void diconn(View v) {
        try {
            IMqttToken token = client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(API.this, "disconnected", Toast.LENGTH_LONG).show();
                    Subscribe();
                }


                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(API.this, "not disconnected", Toast.LENGTH_LONG).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

