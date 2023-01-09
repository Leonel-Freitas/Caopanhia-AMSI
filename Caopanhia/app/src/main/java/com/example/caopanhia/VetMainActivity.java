package com.example.caopanhia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class VetMainActivity extends AppCompatActivity {
    private TextView tvUsernameVet;
    public static final String SHARED = "USER_TOKEN";
    public static final String TOKEN = "TOKEN";
    public static final String USERNAME = "USERNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_main);

        String token = getIntent().getStringExtra(TOKEN);
        if (token!=null){
            SharedPreferences userToken = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userToken.edit();
            editor.putString(TOKEN, token);
            editor.apply();
        }

        carregarTitulo();
    }

    private void carregarTitulo(){
        String username = getIntent().getStringExtra(USERNAME);

        tvUsernameVet = findViewById(R.id.tvUsernameVet);
        tvUsernameVet.setText(getString(R.string.txt_dr) + username);
    }

    public void onClickLogout(View view) {
        SharedPreferences userToken = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        userToken.edit().clear().apply(); //ou .commit()
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}