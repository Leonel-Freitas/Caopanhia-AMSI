package com.example.caopanhia;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeClientFragment extends Fragment {

    public HomeClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_client, container, false);

        return view;
    }


    public void onClickPetsList(View view) {

    }

    public void onClickAppointmentList(View view) {
        //TODO Ligação entre fragmentos
    }

    public void onClickPackagesList(View view) {
        //TODO Ligação entre fragmentos
    }

}