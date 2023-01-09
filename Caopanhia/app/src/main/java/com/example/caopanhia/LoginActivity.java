package com.example.caopanhia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caopanhia.listeners.LoginListener;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;
import com.example.caopanhia.utils.Utilities;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private EditText etEmail, etPassword;
    private final int MINIMO_PASSWORD = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    private boolean emailValidation(String email){
        if (email == null)
            return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean passwordValidation(String password){
        if(password==null)
            return false;
        return password.length() >= MINIMO_PASSWORD;
    }

    public void onClickLogin(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(!emailValidation(email)){
            etEmail.setError("Email Inválido");

            return;
        }
        if(!passwordValidation(password)){
            etPassword.setError("Password Inválida");
        }

        if(!Utilities.isConnectionInternet(getApplicationContext())){
            Toast.makeText(getApplicationContext(), "Erro: Sem ligação à internet!", Toast.LENGTH_LONG).show();
        }else {
            SingletonGestorCaopanhia.getInstance(getApplicationContext()).efetuarLoginAPI(email, password);
        }
        //TODO Verificar dados do login na API e separar cliente de vet



    }

    @Override
    public void onLoginSuccess(String token) {
        //TODO  Armazene o token de acesso em um arquivo de preferencias
        Intent intent = new Intent(this, ClientMainActivity.class);
        //intent.putExtra("USERNAME", email); //TODO passar o username do utilizador
        startActivity(intent);
        finish();

    }

    @Override
    public void onLoginError() {
        Toast.makeText(this, "Erro Login", Toast.LENGTH_SHORT).show();
    }
}