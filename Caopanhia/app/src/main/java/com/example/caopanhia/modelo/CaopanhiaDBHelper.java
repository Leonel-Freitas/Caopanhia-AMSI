package com.example.caopanhia.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CaopanhiaDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DB_CAOPANHIA";
    private static final int DB_VERSION = 1;

    private final SQLiteDatabase db;

    private static final String TABLE_CAES = "caes";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_MARCACOES = "marcacoes";


    // Nome dos campos comuns entre as tabelas
    private static final String ID = "id";

    // Nome dos campos da tabela dos caes
    private static final String NOME = "nome", ANO_NASCIMENTO = "ano_nascimento", ID_USER_PROFILE = "id_user_profile", GENERO = "genero", MICROSHIP = "microship", CASTRADO = "castrado", ADOTADO = "adotado";
    // Nome dos campos dos users
    private static final String USERNAME = "username", PASSWORD = "password", EMAIL = "email", ROLE="role", TOKEN = "token";
    // Nome dos campos das marcacoes
    private static final String DATA = "data", HORA = "hora", NOME_CLIENT = "nome_client", NOME_VET = "nome_vet", NOME_CAO = "nome_cao";

    public CaopanhiaDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateTableCaes =
                "CREATE TABLE " + TABLE_CAES + "( " +
                        ID + " INTEGER PRIMARY KEY," +
                        NOME + " TEXT NOT NULL," +
                        ANO_NASCIMENTO + " INTEGER NOT NULL," +
                        ID_USER_PROFILE + " INTEGER NOT NULL," +
                        GENERO + " TEXT NOT NULL," +
                        MICROSHIP + " TEXT NOT NULL," +
                        CASTRADO + " TEXT NOT NULL," +
                        ADOTADO + " TEXT NOT NULL" + " )";

        sqLiteDatabase.execSQL(sqlCreateTableCaes);


        String sqlCreateTableUsers =
                "CREATE TABLE " + TABLE_USERS + "( " +
                        ID + " INTEGER PRIMARY KEY," +
                        USERNAME + " TEXT NOT NULL," +
                        PASSWORD + " TEXT NOT NULL," +
                        EMAIL + " TEXT NOT NULL," +
                        ROLE + " TEXT NOT NULL," +
                        TOKEN + " TEXT NOT NULL" + " )";

        sqLiteDatabase.execSQL(sqlCreateTableUsers);

        String sqlCreateTableMarcacoes =
                "CREATE TABLE " + TABLE_MARCACOES + "( " +
                        ID + " INTEGER PRIMARY KEY," +
                        DATA + " TEXT NOT NULL," +
                        HORA + " TEXT NOT NULL," +
                        NOME_CLIENT + " TEXT NOT NULL," +
                        NOME_VET + " TEXT NOT NULL," +
                        NOME_CAO + " TEXT NOT NULL" + " )";

        sqLiteDatabase.execSQL(sqlCreateTableMarcacoes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlDropTableCaes = "DROP TABLE IF EXISTS " + TABLE_CAES;
        String sqlDropTableUsers = "DROP TABLE IF EXISTS " + TABLE_USERS;
        String sqlDropTableMarcacoes = "DROP TABLE IF EXISTS " + TABLE_MARCACOES;

        sqLiteDatabase.execSQL(sqlDropTableCaes);
        sqLiteDatabase.execSQL(sqlDropTableUsers);
        sqLiteDatabase.execSQL(sqlDropTableMarcacoes);

        onCreate(sqLiteDatabase);
    }

    //region CAES

    public Cao adicionarCaoDB(Cao cao)
    {
        ContentValues values = new ContentValues();
        values.put(ID, cao.getId());
        values.put(NOME, cao.getNome());
        values.put(ANO_NASCIMENTO, cao.getAnoNascimento());
        values.put(ID_USER_PROFILE, cao.getIdUserProfile());
        values.put(GENERO, cao.getGenero());
        values.put(MICROSHIP, cao.getMicroship());
        values.put(CASTRADO, cao.getCastrado());
        values.put(ADOTADO, cao.getAdotado());

        // devolve -1 em caso de erro, ou o id do novo cao (long)
        db.insert(TABLE_CAES, null, values);

        return cao;
    }

    public boolean editarCaoDB(Cao cao)
    {
        ContentValues values = new ContentValues();
        values.put(NOME, cao.getNome());
        values.put(ANO_NASCIMENTO, cao.getAnoNascimento());
        values.put(ID_USER_PROFILE, cao.getIdUserProfile());
        values.put(GENERO, cao.getGenero());
        values.put(MICROSHIP, cao.getMicroship());
        values.put(CASTRADO, cao.getCastrado());
        values.put(ADOTADO, cao.getAdotado());

        // devolve o número de linhas atualizadas
        return db.update(TABLE_CAES, values, ID+"=?", new String[]{String.valueOf(cao.getId())})==1;
    }

    public boolean removerCaoDB(Cao cao)
    {
        // db.delete devolve o número de linhas eliminadas
        return db.delete(TABLE_CAES, ID+"=?",  new String[]{String.valueOf(cao.getId())})==1;
    }

    public void removerAllCaesDB(int id_user)
    {
        db.delete(TABLE_CAES, ID_USER_PROFILE+"="+id_user, null);
    }

    public ArrayList<Cao> getAllCaesDB(int idUser)
    {
        ArrayList<Cao> caes = new ArrayList<>();

        Cursor cursor = db.query(TABLE_CAES, new String[]{ID, ANO_NASCIMENTO, ID_USER_PROFILE, NOME, GENERO, MICROSHIP, CASTRADO, ADOTADO},
                null, null, null, null, null);

        if(cursor.moveToFirst())
        {
            do {
                Cao caoAux = new Cao(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));

                if (caoAux.getIdUserProfile() == idUser){
                    caes.add(caoAux);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }
        return caes;
    }

    //endregion

    //region USERS

    public User adicionarUserDB(User user)
    {
        ContentValues values = new ContentValues();
        values.put(ID, user.getId());
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        values.put(EMAIL, user.getEmail());
        values.put(ROLE, user.getRole());
        values.put(TOKEN, user.getToken());

        // devolve -1 em caso de erro, ou o id do novo user (long)
        db.insert(TABLE_USERS, null, values);

        return user;
    }


    public boolean removerUserDB(int id)
    {
        // db.delete devolve o número de linhas eliminadas
        return db.delete(TABLE_USERS, ID+"=?",  new String[]{id+""})==1;
    }

    public ArrayList<User> getAllUsersDB()
    {
        ArrayList<User> users = new ArrayList<>();

        Cursor cursor = db.query(TABLE_USERS, new String[]{ID, USERNAME, PASSWORD, EMAIL, ROLE, TOKEN},
                null, null, null, null, null);

        if(cursor.moveToFirst())
        {
            do {
                User userAux = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

                users.add(userAux);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return users;
    }

    //endregion

    //region MARCACOES

    public MarcacaoVeterinaria adicionarMarcacaoDB(MarcacaoVeterinaria marcacao)
    {
        ContentValues values = new ContentValues();
        values.put(ID, marcacao.getId());
        values.put(DATA, marcacao.getData());
        values.put(HORA, marcacao.getHora());
        values.put(NOME_CLIENT, marcacao.getNomeClient());
        values.put(NOME_VET, marcacao.getNomeVet());
        values.put(NOME_CAO, marcacao.getNomeCao());

        // devolve -1 em caso de erro, ou o id da nova marcacao (long)
        db.insert(TABLE_MARCACOES, null, values);

        return marcacao;
    }

    public void removerAllMarcacoesDB()
    {
        db.delete(TABLE_MARCACOES, null, null);
    }

    public ArrayList<MarcacaoVeterinaria> getAllMarcacoesDB()
    {
        ArrayList<MarcacaoVeterinaria> marcacoes = new ArrayList<>();

        Cursor cursor = db.query(TABLE_MARCACOES, new String[]{ID, DATA, HORA, NOME_CLIENT, NOME_VET, NOME_CAO},
                null, null, null, null, null);

        if(cursor.moveToFirst())
        {
            do {
                MarcacaoVeterinaria marcacaoAux = new MarcacaoVeterinaria(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

                marcacoes.add(marcacaoAux);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return marcacoes;
    }

    //endregion

}
