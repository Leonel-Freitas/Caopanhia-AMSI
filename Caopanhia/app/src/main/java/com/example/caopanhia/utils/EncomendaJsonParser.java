package com.example.caopanhia.utils;

import com.example.caopanhia.modelo.Encomenda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EncomendaJsonParser {

    public static ArrayList<Encomenda> parserJasonEncomenda(JSONArray response){
        ArrayList<Encomenda> encomendas = new ArrayList<>();
        try{
            for(int i=0; i<response.length(); i++){
                JSONObject encomenda = (JSONObject) response.get(i);
                int id = encomenda.getInt("id");
                float valorTotal = encomenda.getLong("valorTotal");
                String data = encomenda.getString("data");
                String estado = encomenda.getString("estado");

                Encomenda encomendaAux = new Encomenda(id, valorTotal, data, estado);
                encomendas.add(encomendaAux);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return encomendas;
    }



    public static Encomenda parserJasonEncomenda(String response){
        Encomenda auxEncomenda = null;
        try{
            JSONObject encomenda = new JSONObject(response);
            int id = encomenda.getInt("id");
            float valorTotal = encomenda.getLong("valorTotal");
            String data = encomenda.getString("data");
            String estado = encomenda.getString("estado");


            auxEncomenda = new Encomenda(id, valorTotal, data, estado);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return auxEncomenda;
    }


}
