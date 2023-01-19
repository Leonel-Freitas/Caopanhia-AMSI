package com.example.caopanhia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.caopanhia.adaptadores.ListaMarcacoesAdapter;
import com.example.caopanhia.listeners.MarcacoesListener;
import com.example.caopanhia.modelo.MarcacaoVeterinaria;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class VetMainActivity extends AppCompatActivity implements MarcacoesListener {
    private TextView tvUsernameVet;
    private ListView lvConsultas;
    public static final int ACT_DETALHES = 1;
    public static final String SHARED = "USER_TOKEN";
    public static final String TOKEN = "TOKEN";
    public static final String ID_USER = "ID_USER";
    public static final String USERNAME = "USERNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_main);

        String token = getIntent().getStringExtra(TOKEN);
        int id_user = getIntent().getIntExtra(ID_USER, 0);
        String username = getIntent().getStringExtra(USERNAME);
        if (token!=null){
            SharedPreferences userToken = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userToken.edit();
            editor.putString(TOKEN, token);
            editor.putInt(ID_USER, id_user);
            editor.putString(USERNAME, username);
            editor.apply();
        }

        carregarTitulo();


        //Carregar a lista de consultas

        lvConsultas = findViewById(R.id.lvConsultas);

        lvConsultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(), DetalhesAppointmentActivity.class);
                intent.putExtra("ID_MARCACAO",(int) id);
                startActivityForResult(intent, ACT_DETALHES);
            }
        });

        SingletonGestorCaopanhia.getInstance(this).setMarcacoesListener(this);
        SingletonGestorCaopanhia.getInstance(this).getAllMarcacoesAPI(this);
    }

    private void carregarTitulo(){
        SharedPreferences userToken = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        String username = userToken.getString(USERNAME, "");

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

    @Override
    public void onRefreshListaMarcacoes(ArrayList<MarcacaoVeterinaria> listaMarcacoes) {
        if (listaMarcacoes!=null)
            lvConsultas.setAdapter(new ListaMarcacoesAdapter(this, listaMarcacoes));
    }
}