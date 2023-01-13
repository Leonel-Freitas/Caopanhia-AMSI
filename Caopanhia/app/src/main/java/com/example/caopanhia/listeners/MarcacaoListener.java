package com.example.caopanhia.listeners;

import com.example.caopanhia.modelo.MarcacaoVeterinaria;

import java.util.ArrayList;

public interface MarcacaoListener {
    void onRefreshListaMarcacoes(ArrayList<MarcacaoVeterinaria> listaMarcacoes);
}
