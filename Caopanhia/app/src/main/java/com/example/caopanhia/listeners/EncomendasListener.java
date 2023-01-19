package com.example.caopanhia.listeners;


import com.example.caopanhia.modelo.Encomenda;

import java.util.ArrayList;

public interface EncomendasListener {
    void onRefreshListaEncomendas(ArrayList<Encomenda> listaEncomedas);
}
