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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private EditText etEmail, etPassword;
    private final int MINIMO_PASSWORD = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        SingletonGestorCaopanhia.getInstance(this).setLoginListener(this);

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




    }

    @Override
    public void onLoginSuccess(String token, String role, String username, int id_user) {
        if (Objects.equals(role, "client")){
        Intent intent = new Intent(this, ClientMainActivity.class);
            intent.putExtra(ClientMainActivity.USERNAME, username);
            intent.putExtra(ClientMainActivity.ID_USER, id_user);
            intent.putExtra(ClientMainActivity.TOKEN, token);
            startActivity(intent);
            finish();
        }else if (Objects.equals(role, "vet")){
            Intent intent = new Intent(this, VetMainActivity.class);
            intent.putExtra(VetMainActivity.USERNAME, username);
            intent.putExtra(VetMainActivity.ID_USER, id_user);
            intent.putExtra(VetMainActivity.TOKEN, token);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Não tens permmissão para entrar na aplicação", Toast.LENGTH_SHORT).show();
            etEmail.setText("");
            etPassword.setText("");

        }

    }

    @Override
    public void onLoginError() {
        Toast.makeText(this, "Credenciais erradas", Toast.LENGTH_SHORT).show();
        etEmail.setText("");
        etPassword.setText("");
    }
}