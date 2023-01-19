package com.example.caopanhia.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.caopanhia.modelo.Cao;
import com.example.caopanhia.modelo.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserJsonParser {

    public static User parserJasonUser(String response, String email, String password){
        User auxUser = null;
        try{
            JSONObject user = new JSONObject(response);
            int id = user.getInt("id_user");
            String username = user.getString("username");
           // String password = user.getString("password");
           // String email = user.getString("email");
            String role = user.getString("role");
            String token = user.getString("token");


            auxUser = new User(id, username, password, email, role, token);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return auxUser;
    }


}
