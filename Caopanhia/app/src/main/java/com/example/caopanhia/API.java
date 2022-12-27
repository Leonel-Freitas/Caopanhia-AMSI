package com.example.caopanhia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

 import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response;
 import com.android.volley.VolleyError;
 import com.android.volley.toolbox.JsonArrayRequest;
 import com.android.volley.toolbox.JsonObjectRequest;
 import com.android.volley.toolbox.Volley;
 import org.json.JSONArray;
 import org.json.JSONObject;





import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

//TODO: os pedidos à API devem estar na classe Singleton, neste momento já a devia ter criado
public class API extends AppCompatActivity {
    private TextView ApiText;
    RequestQueue requestQueue;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_api);

            ApiText = findViewById(R.id.ApiText);
            ApiText.setText("");
            requestQueue = Volley.newRequestQueue(this);
        }




        private boolean isLigadoInternet(){
             ConnectivityManager cm = (ConnectivityManager)
               this.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
             return isConnected;
            }


    public void onClickLogin(View view) {

    }

    public void onClickJSonArrayRequest(View view) {
        if(isLigadoInternet()) {
            String url = "http://192.168.1.103/Caopanhia-PLSI-SIS/caopanhia/backend/web/api/caes/";
         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
               Request.Method.GET, url, null,
                 response -> {
                           Toast.makeText(getApplicationContext(),
                                     "Array com " + response.length() +
                                            " elementos", Toast.LENGTH_SHORT).show();
                             ApiText.setText(response.toString());
                             },
                 error -> ApiText.setText(
                        "Erro ao efetuar o pedido JSonArray!!")
         );
        //Adding JsonArrayRequest to request queue
         requestQueue.add(jsonArrayRequest);
         }else {
        Toast.makeText(this,
                "Erro de Ligação! Não está ligado à internet",
                Toast.LENGTH_SHORT).show();
         }
 }



    public void onClickJSonRequest(View view) {
        if(isLigadoInternet()){
            String url = "http://192.168.1.103/yii2test/backend/web/api/user?access-token=0zY-WF23ttBL2Fk76Cmegw_2FWdtPnWs";
            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(
                            Request.Method.GET, url, null,
                            response -> ApiText.setText(
                                    response.toString()),
                            error -> ApiText.setText(
                                    "Erro ao efetuar o pedido JSon!!")
                    );
            //Adding JsonObjet request to request queue
            requestQueue.add(jsonObjectRequest);
        } else {
            Toast.makeText(this,
                    "Erro de Ligação! Não está ligado à internet",
                    Toast.LENGTH_SHORT).show();
        }
    }

}