package com.example.caopanhia.modelo;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.caopanhia.PetsListFragment;
import com.example.caopanhia.listeners.CaesListener;
import com.example.caopanhia.listeners.DetalhesCaoListener;
import com.example.caopanhia.listeners.DetalhesEncomendaListener;
import com.example.caopanhia.listeners.DetalhesMarcacaoListener;
import com.example.caopanhia.listeners.EncomendasListener;
import com.example.caopanhia.listeners.LoginListener;
import com.example.caopanhia.listeners.MarcacoesListener;
import com.example.caopanhia.utils.CaoJsonParser;
import com.example.caopanhia.utils.EncomendaJsonParser;
import com.example.caopanhia.utils.MarcacaoJsonParser;
import com.example.caopanhia.utils.UserJsonParser;
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
    private ArrayList<MarcacaoVeterinaria> marcacoes;
    private ArrayList<Encomenda> encomendas;
    private ArrayList<User> users;
    private static SingletonGestorCaopanhia instance=null;
    private static RequestQueue volleyQueue=null;
    private CaopanhiaDBHelper caopanhiaDB = null;
    //private static String TOKEN="h-thDu-IuI4_MZ7D5iABfLvrLaEFaFMD";
    private static final String mUrlAPICaes="http://10.0.2.2/caopanhia/backend/web/api/caes",
            mUrlAPILogin="http://10.0.2.2/caopanhia/backend/web/api/login/post",
            mUrlAPIMarcacoes="http://10.0.2.2/caopanhia/backend/web/api/marcacoesveterinarias",
            mUrlAPIEncomendas="http://10.0.2.2/caopanhia/backend/web/api/encomendas";

    private CaesListener caesListener;
    private DetalhesCaoListener detalhesCaoListener;
    private LoginListener loginListener;
    private MarcacoesListener marcacoesListener;
    private DetalhesMarcacaoListener detalhesMarcacaoListener;
    private EncomendasListener encomendasListener;
    private DetalhesEncomendaListener detalhesEncomendaListener;

    public static synchronized SingletonGestorCaopanhia getInstance(Context context){
        if(instance == null){
            instance = new SingletonGestorCaopanhia(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorCaopanhia(Context context){
        caes = new ArrayList<>();
        marcacoes = new ArrayList<>();
        caopanhiaDB = new CaopanhiaDBHelper(context);
    }






    //region Login

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public User getUserBD(String email, String password){
        users = caopanhiaDB.getAllUsersDB();
        for(User u:users){
            if(u.getEmail().equals(email)){
                if (u.getPassword().equals(password)){
                    return u;
                }
            }
        }
        return null;
    }

    public User getUserById(int id){
        users = caopanhiaDB.getAllUsersDB();
        for(User u:users){
            if(u.getId()==id){
                return u;
            }
        }
        return null;
    }

    public void adicionarUserBD(User user){
        caopanhiaDB.adicionarUserDB(user);
    }

    public void efetuarLoginAPI(String email, String  password, final Context context){
        if(!Utilities.isConnectionInternet(context)){

            User userLogged = getUserBD(email, password);
            if (userLogged != null){
                loginListener.onLoginSuccess(userLogged.getToken(), userLogged.getRole(), userLogged.getUsername(), userLogged.getId());
            }else{
                Toast.makeText(context, "Ligue-se à internet para realizar login", Toast.LENGTH_LONG).show();
                loginListener.onLoginError();
            }
        }else{
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPILogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject json = new JSONObject(response);
                        String token = json.getString("token");
                        String role = json.getString("role");
                        String username = json.getString("username");
                        int id_user = json.getInt("id_user");

                        if(getUserById(id_user) != null){
                            caopanhiaDB.removerUserDB(id_user);
                        }
                        //User user = new User(id_user, username, password, email, role, token);
                        adicionarUserBD(UserJsonParser.parserJasonUser(response, email, password));

                        if(loginListener!=null){
                            loginListener.onLoginSuccess(token, role, username, id_user);
                        }
                    } catch (JSONException e) {
                        loginListener.onLoginError();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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

    }

    //endregion

    //region Caes

    public void setCaesListener(CaesListener caesListener){
        this.caesListener = caesListener;
    }

    public void setDetalhesCaoListener(DetalhesCaoListener detalhesCaoListener) {
        this.detalhesCaoListener = detalhesCaoListener;
    }


    public Cao getCao(int id){
        for(Cao c:caes){
            if(c.getId()==id){
                return c;
            }
        }
        return null;
    }


    public void adicionarCaesBD(ArrayList<Cao> caes, int id_user){
        caopanhiaDB.removerAllCaesDB(id_user);
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
    public void getAllCaesAPI(final Context context){
        if(!Utilities.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação à internet!", Toast.LENGTH_LONG).show();

            if(caesListener!=null){
                SharedPreferences userToken = context.getSharedPreferences(ClientMainActivity.SHARED, Context.MODE_PRIVATE);
                int id_user = userToken.getInt(ClientMainActivity.ID_USER, 0);
                caes = caopanhiaDB.getAllCaesDB(id_user);
                caesListener.onRefreshListaCaes(caopanhiaDB.getAllCaesDB(id_user));
            }
        }else{
            SharedPreferences userToken = context.getSharedPreferences(ClientMainActivity.SHARED, Context.MODE_PRIVATE);
            int id_user = userToken.getInt(ClientMainActivity.ID_USER, 0);
            String token = userToken.getString(ClientMainActivity.TOKEN, "");
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPICaes+"/caespessoais/"+id_user+"?access-token="+token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    caes = CaoJsonParser.parserJasonCao(response);
                    adicionarCaesBD(caes, id_user);

                    if(caesListener!=null){
                        caesListener.onRefreshListaCaes(caes);
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


    public void adicionarCaesAPI(final Cao cao, final Context context){
        if(!Utilities.isConnectionInternet(context)){
            Toast.makeText(context, "Erro: Sem ligação à internet!", Toast.LENGTH_LONG).show();
        }else{
            SharedPreferences userToken = context.getSharedPreferences(ClientMainActivity.SHARED, Context.MODE_PRIVATE);
            int id_user = userToken.getInt(ClientMainActivity.ID_USER, 0);
            String token = userToken.getString(ClientMainActivity.TOKEN, "");
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPICaes+"?access-token="+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    adicionarCaoBD(CaoJsonParser.parserJasonCao(response));

                    if(detalhesCaoListener!=null){
                        detalhesCaoListener.onRefreshCaoDetalhes(ClientMainActivity.ADD);
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
                    params.put("nome", cao.getNome());
                    params.put("anoNascimento", String.valueOf(cao.getAnoNascimento()));
                    params.put("idUserProfile", String.valueOf(id_user));
                    params.put("genero", cao.getGenero());
                    params.put("microship", cao.getMicroship());
                    params.put("castrado", cao.getCastrado());
                    params.put("adotado", "sim");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }


    public void removerCaoAPI(final Cao cao, final Context context){
        if(!Utilities.isConnectionInternet(context)){
            Toast.makeText(context, "Erro: Sem ligação à internet!", Toast.LENGTH_LONG).show();
        }else{
            SharedPreferences userToken = context.getSharedPreferences(ClientMainActivity.SHARED, Context.MODE_PRIVATE);
            String token = userToken.getString(ClientMainActivity.TOKEN, "");
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPICaes + '/' + cao.getId() + "?access-token="+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    removerCaoDB(cao.getId());

                    if(detalhesCaoListener!=null){
                        detalhesCaoListener.onRefreshCaoDetalhes(ClientMainActivity.DELETE);
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
            SharedPreferences userToken = context.getSharedPreferences(ClientMainActivity.SHARED, Context.MODE_PRIVATE);
            String token = userToken.getString(ClientMainActivity.TOKEN, "");
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPICaes+'/'+cao.getId()+ "?access-token="+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    editarCaoDB(cao);

                    if(detalhesCaoListener!=null){
                        detalhesCaoListener.onRefreshCaoDetalhes(ClientMainActivity.EDIT);
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
                    params.put("nome", cao.getNome());
                    params.put("anoNascimento", String.valueOf(cao.getAnoNascimento()));
                    params.put("genero", cao.getGenero());
                    params.put("microship", cao.getMicroship());
                    params.put("castrado", cao.getCastrado());
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }


    //endregion

    //region Marcacoes

    public void setMarcacoesListener(MarcacoesListener marcacoesListener){
        this.marcacoesListener = marcacoesListener;
    }

    public void setDetalhesMarcacaoListener(DetalhesMarcacaoListener detalhesMarcacaoListener) {
        this.detalhesMarcacaoListener = detalhesMarcacaoListener;
    }

    public MarcacaoVeterinaria getMarcacao(int id){
        for(MarcacaoVeterinaria m:marcacoes){
            if(m.getId()==id){
                return m;
            }
        }
        return null;
    }

    public void adicionarMarcacaoBD(MarcacaoVeterinaria m){
        caopanhiaDB.adicionarMarcacaoDB(m);
    }

    public void adicionarMarcacoesBD(ArrayList<MarcacaoVeterinaria> marcacoes){
        caopanhiaDB.removerAllMarcacoesDB();
        for(MarcacaoVeterinaria m :marcacoes) {
            adicionarMarcacaoBD(m);
        }
    }

    //endregion

    //region Marcacoes_API

    public void getAllMarcacoesAPI(final Context context){
        if(!Utilities.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação à internet!", Toast.LENGTH_LONG).show();

            if(marcacoesListener!=null){
                marcacoes = caopanhiaDB.getAllMarcacoesDB();
                marcacoesListener.onRefreshListaMarcacoes(caopanhiaDB.getAllMarcacoesDB());
            }
        }else{
            SharedPreferences userToken = context.getSharedPreferences(ClientMainActivity.SHARED, Context.MODE_PRIVATE);
            int id_user = userToken.getInt(ClientMainActivity.ID_USER, 0);
            String token = userToken.getString(ClientMainActivity.TOKEN, "");
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIMarcacoes+"/futurasconsultas/"+id_user+"?access-token="+token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    marcacoes = MarcacaoJsonParser.parserJasonMarcacao(response);
                    adicionarMarcacoesBD(marcacoes);

                    if(marcacoesListener!=null){
                        marcacoesListener.onRefreshListaMarcacoes(marcacoes);
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

    //endregion

    //region Encomendas

    public void setEncomendasListener(EncomendasListener encomendasListener){
        this.encomendasListener = encomendasListener;
    }

    public void setDetalhesEncomendasListener(DetalhesEncomendaListener detalhesEncomendasListener) {
        this.detalhesEncomendaListener = detalhesEncomendasListener;
    }

    public Encomenda getEncomenda(int id){
        for(Encomenda e:encomendas){
            if(e.getId()==id){
                return e;
            }
        }
        return null;
    }

    //endregion

    //region EncomendasAPI

    public void getAllEncomendasAPI(final Context context){
        if(!Utilities.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação à internet!", Toast.LENGTH_LONG).show();

        }else{
            SharedPreferences userToken = context.getSharedPreferences(ClientMainActivity.SHARED, Context.MODE_PRIVATE);
            int id_user = userToken.getInt(ClientMainActivity.ID_USER, 0);
            String token = userToken.getString(ClientMainActivity.TOKEN, "");
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIEncomendas+"/historico/"+id_user+"?access-token="+token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    encomendas = EncomendaJsonParser.parserJasonEncomenda(response);

                    if(encomendasListener!=null){
                        encomendasListener.onRefreshListaEncomendas(encomendas);
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

    //endregion
}
