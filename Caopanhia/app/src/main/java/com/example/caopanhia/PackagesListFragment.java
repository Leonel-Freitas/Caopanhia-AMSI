package com.example.caopanhia;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.caopanhia.adaptadores.ListaEncomendasAdapter;
import com.example.caopanhia.adaptadores.ListaMarcacoesAdapter;
import com.example.caopanhia.listeners.EncomendasListener;
import com.example.caopanhia.modelo.Encomenda;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;

import java.util.ArrayList;

public class PackagesListFragment extends Fragment implements EncomendasListener {
    private ListView lvPackages;
    public static final int ACT_DETALHES = 1;


    public PackagesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_packages_list, container, false);
       setHasOptionsMenu(true);

       lvPackages = view.findViewById(R.id.lvPackages);
       lvPackages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
               Intent intent = new Intent(getContext(), DetalhesPackagesActivity.class);
               intent.putExtra("ID_ENCOMENDA",(int) id);
               startActivityForResult(intent, ACT_DETALHES);
           }
       });

        SingletonGestorCaopanhia.getInstance(getContext()).setEncomendasListener(this);
        SingletonGestorCaopanhia.getInstance(getContext()).getAllEncomendasAPI(getContext());


       return view;
    }

    @Override
    public void onRefreshListaEncomendas(ArrayList<Encomenda> listaEncomedas) {
        if (listaEncomedas!=null)
            lvPackages.setAdapter(new ListaEncomendasAdapter(getContext(), listaEncomedas));
    }
}