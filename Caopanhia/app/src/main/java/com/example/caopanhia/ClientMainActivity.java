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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ClientMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    public static final String SHARED = "USER_TOKEN";
    public static final String TOKEN = "TOKEN";
    public static final String USERNAME = "USERNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        String token = getIntent().getStringExtra(TOKEN);
        if (token != null){
            SharedPreferences userToken = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userToken.edit();
            editor.putString(TOKEN, token);
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
                Toast.makeText(this, "MAPA", Toast.LENGTH_SHORT).show();
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