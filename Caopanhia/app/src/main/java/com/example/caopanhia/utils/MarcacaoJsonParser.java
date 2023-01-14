package com.example.caopanhia.utils;

import com.example.caopanhia.modelo.MarcacaoVeterinaria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarcacaoJsonParser {

    public static ArrayList<MarcacaoVeterinaria> parserJasonMarcacao(JSONArray response){
        ArrayList<MarcacaoVeterinaria> marcacoes = new ArrayList<>();
        try{
            for(int i=0; i<response.length(); i++){
                JSONObject marcacao = (JSONObject) response.get(i);
                int id = marcacao.getInt("id");
                String data = marcacao.getString("data");
                String hora = marcacao.getString("hora");
                String nomeClient = marcacao.getString("nomeClient");
                String nomeVet = marcacao.getString("nomeVet");
                String nomeCao = marcacao.getString("nomeCao");

                MarcacaoVeterinaria marcacaoAux = new MarcacaoVeterinaria(id, data, hora, nomeClient, nomeVet, nomeCao);
                marcacoes.add(marcacaoAux);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return marcacoes;
    }



    public static MarcacaoVeterinaria parserJasonMarcacao(String response){
        MarcacaoVeterinaria auxMarcacao = null;
        try{
            JSONObject marcacao = new JSONObject(response);
            int id = marcacao.getInt("id");
            String data = marcacao.getString("data");
            String hora = marcacao.getString("hora");
            String nomeClient = marcacao.getString("nomeClient");
            String nomeVet = marcacao.getString("nomeVet");
            String nomeCao = marcacao.getString("nomeCao");


            auxMarcacao = new MarcacaoVeterinaria(id, data, hora, nomeClient, nomeVet, nomeCao);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return auxMarcacao;
    }


}
