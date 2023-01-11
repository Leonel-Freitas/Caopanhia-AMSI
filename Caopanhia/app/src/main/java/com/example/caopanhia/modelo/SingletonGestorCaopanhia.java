package com.example.caopanhia.modelo;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.caopanhia.ClientMainActivity;
import com.example.caopanhia.listeners.CaesListener;
import com.example.caopanhia.listeners.DetalhesListener;
import com.example.caopanhia.listeners.LoginListener;
import com.example.caopanhia.utils.CaoJsonParser;
import com.example.caopanhia.utils.Utilities;
import com.google.android.gms.common.internal.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorCaopanhia {
    private ArrayList<Cao> caes;
    private static SingletonGestorCaopanhia instance=null;
    private static RequestQueue volleyQueue=null;
    private CaopanhiaDBHelper caopanhiaDB = null;
    private static String TOKEN="";
    private static final String mUrlAPICaes="http://10.0.2.2/Caopanhia-PLSI-SIS/caopanhia/backend/web/api/caes?access-token=" + TOKEN,
            mUrlAPILogin="http://10.0.2.2/Caopanhia-PLSI-SIS/caopanhia/backend/web/api/login/post";

    private CaesListener caesListener;
    private DetalhesListener detalhesListener;
    private LoginListener loginListener;

    //1.2.2.Devem criar um atributo livrosBD do tipo CaoBDHelper;
    public static synchronized SingletonGestorCaopanhia getInstance(Context context){
        if(instance == null){
            instance = new SingletonGestorCaopanhia(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    //3.3. No SingletonGestorCaos adicione os seguintes livros no gerarDadosDinamico():
    private SingletonGestorCaopanhia(Context context){
        caes = new ArrayList<>();
        //1.2.3. No construtor, devem iniciar a instância do CaoBDHelper, deixando de usar o método gerarDadosDinamico();
        caopanhiaDB = new CaopanhiaDBHelper(context);
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    //Login Na API

    public void efetuarLoginAPI(String email, String  password){
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPILogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("Teste"+response);
                   try {
                        JSONObject json = new JSONObject(response);
                        String token = json.getString("token");
                        String role = json.getString("role");
                        TOKEN = token;
                        String username = json.getString("username");
                        if(loginListener!=null){
                        loginListener.onLoginSuccess(token, role, username);
                        TOKEN = token;
                            System.out.println(mUrlAPICaes);
                        }
                    } catch (JSONException e) {
                        loginListener.onLoginError();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //loginListener.onLoginError();
                    System.out.println("TESTE-ERRO"+error.getMessage());
                    loginListener.onLoginError();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            volleyQueue.add(request);
            }
    



















    //region Caes

    public void setCaesListener(CaesListener caesListener){
          this.caesListener = caesListener;
      }

      public void setDetalhesListener(DetalhesListener detalhesListener) {
        this.detalhesListener = detalhesListener;
     }

      public ArrayList<Cao> CaesBD() {
         caes = caopanhiaDB.getAllCaesDB();
         return new ArrayList(caes);
     }

    //8.2.1. Para aceder de forma correta ao livro selecionado, deve implementar o método Cao getCao(int idCao)
    //na classe SingletonGestorCaos.
    //8.2.2. Para passar o id do livro selecionado para a nova atividade, necessita criar nessa classe uma constante
    //que servirá de chave ao bundle Extra do intent.
    public Cao getCao(int id){
        for(Cao c:caes){
            if(c.getId()==id){
                return c;
            }
        }
        return null;
    }

    // 1.2.4. Após isso, devem alterar os métodos adicionarCao, removerCao, editarCao e getCaos, renomeando-os para
    // adicionarCaoBD, removerCaoBD, editarCaoBD e getCaosBD, de modo a utilizar os métodos da base de dados;
    public void adicionarCaoBD(ArrayList<Cao> Caes){
        caopanhiaDB.removerAllCaesDB();
        for(Cao c :caes) {
            adicionarCaoBD(c);
        }
    }

    public void adicionarCaoBD(Cao c){
        caopanhiaDB.adicionarCaoDB(c);
    }

    public void editarCaoDB(Cao c){
        Cao auxCao = getCao(c.getId());
        if(auxCao!=null){
            caopanhiaDB.editarCaoDB(c);
        }
    }

    public void removerCaoDB(int id) {
        Cao auxCao = getCao(id);
        if (auxCao != null) {
            caopanhiaDB.removerCaoDB(auxCao);
        }
    }

    //endregion

    //region Caes_API
    public void adicionarCaesAPI(final Cao cao, final Context context){
        if(!Utilities.isConnectionInternet(context)){
            Toast.makeText(context, "Erro: Sem ligação à internet!", Toast.LENGTH_LONG).show();
        }else{
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPICaes, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    adicionarCaoBD(CaoJsonParser.parserJasonCao(response));

                    if(detalhesListener!=null){
                        //detalhesListener.onRefreshDetalhes(ClientMainActivity.ADD);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params=new HashMap<>();
                    params.put("token", TOKEN);
                    params.put("Nome", cao.getNome());
                    params.put("Ano Nascimento", String.valueOf(cao.getAnoNascimento()));
                    params.put("Genero", cao.getGenero());
                    params.put("Microship", cao.getMicroship());
                    params.put("Castrado", cao.getCastrado());
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void getAllCaesAPI(final Context context){
        if(!Utilities.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação à internet!", Toast.LENGTH_LONG).show();

            if(caesListener!=null){
                caesListener.onRefreachListaCaes(caopanhiaDB.getAllCaesDB());
            }
        }else{
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPICaes, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    caes = CaoJsonParser.parserJasonCao(response);
                    adicionarCaoBD(caes);

                    if(caesListener!=null){
                        caesListener.onRefreachListaCaes(caes);
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void removerCaoAPI(final Cao livro, final Context context){
        if(!Utilities.isConnectionInternet(context)){
            Toast.makeText(context, "Erro: Sem ligação à internet!", Toast.LENGTH_LONG).show();
        }else{
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPICaes + '/' + livro.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    removerCaoDB(livro.getId());

                    if(detalhesListener!=null){
                        //detalhesListener.onRefreshDetalhes(MenuMainActivity.DELETE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void editarCaoAPI(final Cao cao, final Context context){
        if(!Utilities.isConnectionInternet(context)){
            Toast.makeText(context, "Erro: Sem ligação à internet!", Toast.LENGTH_LONG).show();
        }else{
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPICaes+'/'+cao.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    editarCaoDB(cao);

                    if(detalhesListener!=null){
                        //detalhesListener.onRefreshDetalhes(MenuMainActivity.EDIT);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params=new HashMap<>();
                    params.put("token", TOKEN);
                    params.put("Nome", cao.getNome());
                    params.put("Ano Nascimento", String.valueOf(cao.getAnoNascimento()));
                    params.put("Genero", cao.getGenero());
                    params.put("Microship", cao.getMicroship());
                    params.put("Castrado", cao.getCastrado());
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }


    //endregion
}
