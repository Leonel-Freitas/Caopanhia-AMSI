package com.example.caopanhia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.caopanhia.adaptadores.ListaCaesAdapter;
import com.example.caopanhia.adaptadores.ListaMarcacoesAdapter;
import com.example.caopanhia.listeners.MarcacaoListener;
import com.example.caopanhia.modelo.Cao;
import com.example.caopanhia.modelo.MarcacaoVeterinaria;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;

import java.util.ArrayList;


public class AppointmentListFragment extends Fragment implements MarcacaoListener {
    private ListView lvMarcacoes;

    public AppointmentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_appointment_list, container, false);
        setHasOptionsMenu(true);

        lvMarcacoes= view.findViewById(R.id.lvMarcacoes);



        SingletonGestorCaopanhia.getInstance(getContext()).setMarcacoesListener(this);
        SingletonGestorCaopanhia.getInstance(getContext()).getAllMarcacoesAPI(getContext());
       return view;
    }




    @Override
    public void onRefreshListaMarcacoes(ArrayList<MarcacaoVeterinaria> listaMarcacoes) {
        if (listaMarcacoes!=null)
            lvMarcacoes.setAdapter(new ListaMarcacoesAdapter(getContext(), listaMarcacoes));
    }
}