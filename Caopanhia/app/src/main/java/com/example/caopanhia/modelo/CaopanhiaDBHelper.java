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


    // Nome dos campos comuns entre as tabelas
    private static final String ID = "id";

    // Nome dos campos da tabela dos caes
    private static final String NOME = "nome", ANO_NASCIMENTO = "ano_nascimento", GENERO = "genero", MICROSHIP = "microship", CASTRADO = "castrado";
    // Nome dos campos dos users
    private static final String USERNAME = "username", PASSWORD = "password", EMAIL = "email";

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
                        GENERO + " TEXT NOT NULL," +
                        MICROSHIP + " TEXT NOT NULL," +
                        CASTRADO + " TEXT NOT NULL" + " )";

        sqLiteDatabase.execSQL(sqlCreateTableCaes);


        String sqlCreateTableUsers =
                "CREATE TABLE " + TABLE_USERS + "( " +
                        ID + " INTEGER PRIMARY KEY," +
                        USERNAME + " TEXT NOT NULL," +
                        PASSWORD + " TEXT NOT NULL," +
                        EMAIL + " TEXT NOT NULL" + " )";

        sqLiteDatabase.execSQL(sqlCreateTableUsers);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlDropTableCaes = "DROP TABLE IF EXISTS " + TABLE_CAES;
        String sqlDropTableUsers = "DROP TABLE IF EXISTS " + TABLE_USERS;

        sqLiteDatabase.execSQL(sqlDropTableCaes);
        sqLiteDatabase.execSQL(sqlDropTableUsers);

        onCreate(sqLiteDatabase);
    }

    //region CAES

    public Cao adicionarCaoDB(Cao cao)
    {
        ContentValues values = new ContentValues();
        values.put(ID, cao.getId());
        values.put(NOME, cao.getNome());
        values.put(ANO_NASCIMENTO, cao.getAnoNascimento());
        values.put(GENERO, cao.getGenero());
        values.put(MICROSHIP, cao.getMicroship());
        values.put(CASTRADO, cao.getCastrado());

        // devolve -1 em caso de erro, ou o id do novo cao (long)
        db.insert(TABLE_CAES, null, values);

        return cao;
    }

    public boolean editarCaoDB(Cao cao)
    {
        ContentValues values = new ContentValues();
        values.put(NOME, cao.getNome());
        values.put(ANO_NASCIMENTO, cao.getAnoNascimento());
        values.put(GENERO, cao.getGenero());
        values.put(MICROSHIP, cao.getMicroship());
        values.put(CASTRADO, cao.getCastrado());

        // devolve o número de linhas atualizadas
        return db.update(TABLE_CAES, values, ID+"=?", new String[]{String.valueOf(cao.getId())})==1;
    }

    public boolean removerCaoDB(Cao cao)
    {
        // db.delete devolve o número de linhas eliminadas
        return db.delete(TABLE_CAES, ID+"=?",  new String[]{String.valueOf(cao.getId())})==1;
    }

    public void removerAllCaesDB()
    {
        db.delete(TABLE_CAES, null, null);
    }

    public ArrayList<Cao> getAllCaesDB()
    {
        ArrayList<Cao> caes = new ArrayList<>();

        Cursor cursor = db.query(TABLE_CAES, new String[]{NOME, ANO_NASCIMENTO, GENERO, MICROSHIP, CASTRADO, ID},
                null, null, null, null, null);

        if(cursor.moveToFirst())
        {
            do {
                Cao caoAux = new Cao(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

                 caes.add(caoAux);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return caes;
    }

    //endregion

    //region Users

    public User adicionarUserDB(User user)
    {
        ContentValues values = new ContentValues();
        values.put(ID, user.getId());
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        values.put(EMAIL, user.getEmail());

        // devolve -1 em caso de erro, ou o id do novo cao (long)
        db.insert(TABLE_USERS, null, values);

        return user;
    }


    public void removerAllUsersDB()
    {
        db.delete(TABLE_USERS, null, null);
    }

    public ArrayList<User> getAllUsersDB()
    {
        ArrayList<User> users = new ArrayList<>();

        Cursor cursor = db.query(TABLE_USERS, new String[]{USERNAME, PASSWORD, EMAIL, ID},
                null, null, null, null, null);

        if(cursor.moveToFirst())
        {
            do {
                User userAux = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

                users.add(userAux);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return users;
    }

    //endregion

}
