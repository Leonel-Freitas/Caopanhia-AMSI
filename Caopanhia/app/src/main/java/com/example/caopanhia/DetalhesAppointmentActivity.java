package com.example.caopanhia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;

import com.example.caopanhia.listeners.DetalhesMarcacaoListener;
import com.example.caopanhia.modelo.MarcacaoVeterinaria;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;

public class DetalhesAppointmentActivity extends AppCompatActivity implements DetalhesMarcacaoListener {
    private MarcacaoVeterinaria marcacao;
    private EditText etData, etHora, etNomeClient, etNomeVet, etNomeCao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_appointment);

        int id = getIntent().getIntExtra("ID_MARCACAO", 0);
        marcacao = SingletonGestorCaopanhia.getInstance(getApplicationContext()).getMarcacao(id);

        etData = findViewById(R.id.etData);
        etHora = findViewById(R.id.etHora);
        etNomeClient = findViewById(R.id.etNomeClient);
        etNomeVet = findViewById(R.id.etNomeVet);
        etNomeCao = findViewById(R.id.etNomeCao);
        SingletonGestorCaopanhia.getInstance(getApplicationContext()).setDetalhesMarcacaoListener(this);

        carregarMarcacao();
    }

    private void carregarMarcacao() {
        Resources res = getResources();
        String titulo = String.format(res.getString(R.string.act_Consulta), "#"+marcacao.getId());
        setTitle(titulo);
        etData.setText(marcacao.getData());
        etHora.setText(marcacao.getHora());
        etNomeClient.setText(marcacao.getNomeClient());
        etNomeVet.setText(marcacao.getNomeVet());
        etNomeCao.setText(marcacao.getNomeCao());
    }

    @Override
    public void onRefreshMarcacaoDetalhes() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}