package com.example.caopanhia.listeners;

public interface LoginListener {
    void onLoginSuccess(String token, String role, String username, int id_user);

    void onLoginError();
}
