package com.example.caopanhia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.caopanhia.listeners.DetalhesCaoListener;
import com.example.caopanhia.modelo.Cao;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class DetalhesPetActivity extends AppCompatActivity implements DetalhesCaoListener {
    private Cao cao;
    private EditText etNome, etAnoNascimento, etGenero, etMicroship, etCastrado;
    private FloatingActionButton fabGuardar;
    public static final int MAX_CHAR_NOME = 30, MAX_CHAR_GENERO = 10, YES_NO_CHAR = 3;

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
                if(isCaoValido()){
                    if(cao != null){
                        cao.setNome(etNome.getText().toString());
                        cao.setAnoNascimento(Integer.parseInt(etAnoNascimento.getText().toString()));
                        cao.setGenero(etGenero.getText().toString());
                        cao.setMicroship(etMicroship.getText().toString());
                        cao.setCastrado(etCastrado.getText().toString());
                        SingletonGestorCaopanhia.getInstance(getApplicationContext()).editarCaoAPI(cao, getApplicationContext());
                    }else{
                        SharedPreferences userToken = getSharedPreferences(ClientMainActivity.SHARED, Context.MODE_PRIVATE);
                        int id_user = userToken.getInt(ClientMainActivity.ID_USER, 0);
                        Cao caoAux = new Cao(0, Integer.parseInt(etAnoNascimento.getText().toString()), id_user, etNome.getText().toString(), etGenero.getText().toString(), etMicroship.getText().toString(), etCastrado.getText().toString(), "sim");
                        SingletonGestorCaopanhia.getInstance(getApplicationContext()).adicionarCaesAPI(caoAux, getApplicationContext());
                    }
                }
            }
        });

    }

    private boolean isCaoValido() {
        String Nome = etNome.getText().toString();
        String AnoNascimento = String.valueOf(etAnoNascimento.getText());
        String Genero = etGenero.getText().toString();
        String Microship = etMicroship.getText().toString();
        String Castrado = etCastrado.getText().toString();

        if(Nome.length() > MAX_CHAR_NOME){
            etNome.setError("Erro: Nome Inválido");
            return false;
        }
        if(Genero.length() > MAX_CHAR_GENERO){
            etGenero.setError("Erro: Genero Inválido");
            return false;
        }
        if(Microship.length() != YES_NO_CHAR){
            etMicroship.setError("Erro: Microship Inválido");
            return false;
        }
        if(Castrado.length() != YES_NO_CHAR){
            etCastrado.setError("Erro: Castração Inválida");
            return false;
        }else{
            if(Integer.parseInt(AnoNascimento) < 2000 || Integer.parseInt(AnoNascimento) > Calendar.getInstance().get(Calendar.YEAR)){
                etAnoNascimento.setError("Erro: Ano de Nascimento Inválido");
                return false;
            }
        }

        return true;
    }

    private void carregarCao() {
        Resources res = getResources();
        String nome = String.format(res.getString(R.string.act_Nome), cao.getNome());
        setTitle(nome);
        etNome.setText(cao.getNome());
        etAnoNascimento.setText(cao.getAnoNascimento()+"");
        etGenero.setText(cao.getGenero());
        etMicroship.setText(cao.getMicroship());
        etCastrado.setText(cao.getCastrado());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(cao!=null){
            getMenuInflater().inflate(R.menu.menu_detalhes_cao,menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemDelete:
                dialogRemover();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogRemover() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover Cao")
                .setMessage("Tem a certeza que deseja apagar o cao?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //caso resposta = SIM
                        SingletonGestorCaopanhia.getInstance(getApplicationContext()).removerCaoAPI(cao, getApplicationContext());
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //caso resposta = NÃO
                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    @Override
    public void onRefreshCaoDetalhes(int operacao) {
        Intent intent = new Intent();
        intent.putExtra(ClientMainActivity.OPERACAO, operacao);
        setResult(RESULT_OK, intent);
        finish();
    }
}