package com.example.caopanhia.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.caopanhia.modelo.Cao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CaoJsonParser {

    public static ArrayList<Cao> parserJasonCao(JSONArray response){
        ArrayList<Cao> caes = new ArrayList<>();
        try{
            for(int i=0; i<response.length(); i++){
                JSONObject cao = (JSONObject) response.get(i);
                int id = cao.getInt("id");
                int anoNascimento = Integer.parseInt(cao.getString("anoNascimento"));
                int id_user_profile = cao.getInt("idUserProfile");
                String nome = cao.getString("nome");
                String genero = cao.getString("genero");
                String microship = cao.getString("microship");
                String castrado = cao.getString("castrado");
                String adotado = cao.getString("adotado");

                Cao auxcao = new Cao(id, anoNascimento, id_user_profile, nome, genero, microship, castrado, adotado);
                caes.add(auxcao);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return caes;
    }



    public static Cao parserJasonCao(String response){
        Cao auxcao = null;
        try{
            JSONObject cao = new JSONObject(response);
            int id = cao.getInt("id");
            int anoNascimento = Integer.parseInt(cao.getString("anoNascimento"));
            int id_user_profile = cao.getInt("idUserProfile");
            String nome = cao.getString("nome");
            String genero = cao.getString("genero");
            String microship = cao.getString("microship");
            String castrado = cao.getString("castrado");
            String adotado = cao.getString("adotado");


            auxcao = new Cao(id, anoNascimento, id_user_profile, nome, genero, microship, castrado, adotado);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return auxcao;
    }


}
