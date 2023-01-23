package com.example.caopanhia;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.example.caopanhia.adaptadores.ListaMarcacoesAdapter;
import com.example.caopanhia.listeners.MarcacoesListener;
import com.example.caopanhia.modelo.MarcacaoVeterinaria;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;

import java.util.ArrayList;


public class AppointmentListFragment extends Fragment implements MarcacoesListener {
    private ListView lvAppointments;
    public static final int ACT_DETALHES = 1;


    public AppointmentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_appointment_list, container, false);
       setHasOptionsMenu(true);
        SingletonGestorCaopanhia.getInstance(getContext()).setMarcacoesListener(this);
        SingletonGestorCaopanhia.getInstance(getContext()).getAllMarcacoesAPI(getContext());

       return view;
    }

    @Override
    public void onRefreshListaMarcacoes(ArrayList<MarcacaoVeterinaria> listaMarcacoes) {
        if (listaMarcacoes!=null)
            lvAppointments.setAdapter(new ListaMarcacoesAdapter(getContext(), listaMarcacoes));
    }
}