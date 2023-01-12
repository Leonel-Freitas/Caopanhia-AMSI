package com.example.caopanhia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.caopanhia.listeners.DetalhesCaoListener;
import com.example.caopanhia.modelo.Cao;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesPetActivity extends AppCompatActivity implements DetalhesCaoListener {
    private Cao cao;
    private EditText etNome, etAnoNascimento, etGenero, etMicroship, etCastrado;
    private FloatingActionButton fabGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pet);

        int id = getIntent().getIntExtra("ID_CAO", 0);
        cao = SingletonGestorCaopanhia.getInstance(getApplicationContext()).getCao(id);

        etNome = findViewById(R.id.etNome);
        etAnoNascimento = findViewById(R.id.etAnoNascimento);
        etGenero = findViewById(R.id.etGenero);
        etMicroship = findViewById(R.id.etMicroship);
        etCastrado = findViewById(R.id.etCastrado);
        fabGuardar = findViewById(R.id.fabSave);
        SingletonGestorCaopanhia.getInstance(getApplicationContext()).setDetalhesCaoListener(this);

        if (cao != null){
            carregarCao();
            fabGuardar.setImageResource(R.drawable.ic_action_save);
        }else{
            setTitle("Adicionar Cão");
            fabGuardar.setImageResource(R.drawable.ic_action_add);
        }

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Verificar se o cao é valido e fazer o post ou o update
            }
        });

    }

    private void carregarCao() {
        //TODO
    }

    @Override
    public void onRefreshDetalhes(int operacao) {
        Intent intent = new Intent();
        intent.putExtra(ClientMainActivity.OPERACAO, operacao);
        setResult(RESULT_OK, intent);
        finish();
    }
}