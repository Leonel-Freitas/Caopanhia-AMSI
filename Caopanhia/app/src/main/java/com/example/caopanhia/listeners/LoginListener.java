package com.example.caopanhia.listeners;

public interface LoginListener {
    void onLoginSuccess(String token);
    void onLoginError();
}
