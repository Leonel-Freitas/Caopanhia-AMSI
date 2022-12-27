package com.example.caopanhia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private final int MINIMO_PASSWORD = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: estava a carregar a vista errada
        setContentView(R.layout.activity_login);

     //   etEmail = findViewById(R.id.etEmail);
      // etPassword = findViewById(R.id.etPassword);
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
            return;
        }

        //TODO Verificar dados do login na API e separar cliente de vet


        Intent intent = new Intent(this, ClientMainActivity.class);
        intent.putExtra("USERNAME", email); //TODO passar o username do utilizador
        startActivity(intent);
        finish();
    }
}