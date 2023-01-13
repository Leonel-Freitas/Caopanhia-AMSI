package com.example.caopanhia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.caopanhia.adaptadores.ListaCaesAdapter;
import com.example.caopanhia.listeners.CaesListener;
import com.example.caopanhia.modelo.Cao;
import com.example.caopanhia.modelo.SingletonGestorCaopanhia;
import com.example.caopanhia.utils.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class PetsListFragment extends Fragment implements CaesListener {
    private ListView lvPets;
    private FloatingActionButton fabNewPet;
    public static final int ACT_DETALHES = 1;


    public PetsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pets_list, container, false);
        setHasOptionsMenu(true);

        lvPets = view.findViewById(R.id.lvPets);
        fabNewPet=view.findViewById(R.id.fabNewPet);

        lvPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(getContext(), DetalhesPetActivity.class);
                intent.putExtra("ID_CAO",(int) id);
                startActivityForResult(intent, ACT_DETALHES);

            }
        });

        fabNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetalhesPetActivity.class);
                startActivityForResult(intent, ACT_DETALHES);
            }
        });


        SingletonGestorCaopanhia.getInstance(getContext()).setCaesListener(this);
        SingletonGestorCaopanhia.getInstance(getContext()).getAllCaesAPI(getContext());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == ACT_DETALHES){
            SingletonGestorCaopanhia.getInstance(getContext()).getAllCaesAPI(getContext());
            switch (intent.getIntExtra(ClientMainActivity.OPERACAO, 0)){
                case ClientMainActivity.ADD:
                    Toast.makeText(getContext(), "Cão adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                    break;
                case ClientMainActivity.EDIT:
                    Toast.makeText(getContext(), "Detalhes do cão modificados com sucesso!", Toast.LENGTH_SHORT).show();
                    break;
                case ClientMainActivity.DELETE:
                    Toast.makeText(getContext(), "Cão removido com sucesso!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }



    @Override
    public void onRefreshListaCaes(ArrayList<Cao> listaCaes) {
        if (listaCaes!=null)
            lvPets.setAdapter(new ListaCaesAdapter(getContext(), listaCaes));
    }
}