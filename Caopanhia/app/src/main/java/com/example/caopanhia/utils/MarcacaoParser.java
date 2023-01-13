package com.example.caopanhia.utils;

import com.example.caopanhia.modelo.MarcacaoVeterinaria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarcacaoParser {
    public static ArrayList<MarcacaoVeterinaria> parseJasonMarcacao(JSONArray response){
        ArrayList<MarcacaoVeterinaria> marcacoes = new ArrayList<>();
        try{
            for(int i=0; i<response.length(); i++){
                JSONObject marcacao = (JSONObject) response.get(i);
                int id = marcacao.getInt("id");
                String data = marcacao.getString("data");
                String hora = marcacao.getString("hora");
                String vet = marcacao.getString("vet");
                String cao = marcacao.getString("cao");

                MarcacaoVeterinaria auxmarcacao = new MarcacaoVeterinaria(id, data, hora, vet, cao);
                marcacoes.add(auxmarcacao);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return marcacoes;
    }

    public static MarcacaoVeterinaria parserJasonMarcacao(String response){
        MarcacaoVeterinaria auxmarcacao = null;
        try{
            JSONObject marcacao = new JSONObject(response);
            int id = marcacao.getInt("id");
            String data = marcacao.getString("data");
            String hora = marcacao.getString("hora");
            String vet = marcacao.getString("vet");
            String cao = marcacao.getString("cao");

            auxmarcacao = new MarcacaoVeterinaria(id, data, hora, vet, cao);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return auxmarcacao;
    }
}
