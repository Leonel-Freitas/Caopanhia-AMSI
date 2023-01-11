package com.example.caopanhia.listeners;

public interface LoginListener {
    void onLoginSuccess(String token, String role, String username);
    void onLoginError();
}
