package com.example.caopanhia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;

import com.example.caopanhia.listeners.DetalhesEncomendaListener;
import com.example.caopanhia.modelo.Encomenda;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;

public class DetalhesPackagesActivity extends AppCompatActivity implements DetalhesEncomendaListener {
    private Encomenda encomenda;
    private EditText etNumeroEncomenda, etData, etValorPago, etEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_packages);

        int id = getIntent().getIntExtra("ID_ENCOMENDA", 0);
        encomenda = SingletonGestorCaopanhia.getInstance(getApplicationContext()).getEncomenda(id);

        etNumeroEncomenda = findViewById(R.id.etNumeroEncomenda);
        etData = findViewById(R.id.etData);
        etValorPago = findViewById(R.id.etValorPago);
        etEstado = findViewById(R.id.etEstado);
        SingletonGestorCaopanhia.getInstance(getApplicationContext()).setDetalhesEncomendasListener(this);

        carregarEncomenda();
    }

    private void carregarEncomenda() {
        Resources res = getResources();
        String titulo = String.format(res.getString(R.string.act_Encomenda), "#"+encomenda.getId());
        setTitle(titulo);
        etNumeroEncomenda.setText("#"+encomenda.getId());
        etData.setText(encomenda.getData());
        etValorPago.setText(encomenda.getValorTotal()+"€");
        etEstado.setText(encomenda.getEstado());
    }

    @Override
    public void onRefreshEncomendaDetalhes() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}