package com.elise.filmotec;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by Elise on 02/05/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String DB_Path="/data/data/com.elise.filmotec/";
    private static String DB_Name="filmotec.sql";
    private SQLiteDatabase filmotec;
    private final Context myContext;
    public DatabaseHandler(Context context) {
        super(context, DB_Name, null, 1);
        this.myContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    private boolean checkDatabase(){
        SQLiteDatabase checkDB=null;
        try{
            String myPath=DB_Path+DB_Name;
            checkDB=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e){

        }
        if (checkDB != null) checkDB.close();
        return checkDB != null ? true : false;

    }
    private void copyDatabase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DB_Name);
        String outFileName=DB_Path+DB_Name;
        OutputStream myOutput=new FileOutputStream(outFileName);
        byte[] buffer=new byte [1024];
        int length;
        while ((length =myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDatabase() throws SQLException{
        String myPath=DB_Path+DB_Name;
        filmotec=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    public void ExeSQLData(String sql) throws SQLException{
        filmotec.execSQL(sql);
    }
    public Cursor QueryData(String query) throws SQLException{
        return filmotec.rawQuery(query,null);
    }

    @Override
    public synchronized void close() {
        if (filmotec !=null)
            filmotec.close();
        super.close();
    }
    public void checkAndCopyDatabase (){
        boolean dbExist=checkDatabase();
        if (dbExist) {
            Log.d("TAG", "La base de données existe déjà");

        }else{
            this.getReadableDatabase();
            try{
                copyDatabase();
            }catch (IOException e){
                Log.d("TAG", "Erreur lors de la copie de la base de données");

            }

        }
    }
}


    /*public static final String DATABASE_NAME="films.db";

    public static final String FILM_TABLE_NAME = "Film";
    public static final String FILM_KEY = "NumFilm";
    public static final String FILM_TITRE = "Titre";
    public static final String FILM_RESUME = "Resume";
    public static final String FILM_PAYS = "Pays";
    public static final String FILM_ANNEE = "Annee";

    public static final String FILM_TABLE_CREATE =
            "CREATE TABLE " + FILM_TABLE_NAME + " (" +
                    FILM_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FILM_TITRE + " TEXT, " +
                    FILM_RESUME + " TEXT," + FILM_PAYS + " TEXT, " + FILM_ANNEE + " TEXT);";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(FILM_TABLE_CREATE);

        // C'est dans cette méthode que vous devrez lancer les instructions pour créer les différentes tables et éventuellement les remplir avec des données initiales.
    }

    public static final String FILM_TABLE_DROP = "DROP TABLE IF EXISTS " + FILM_TABLE_NAME + ";";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FILM_TABLE_DROP);
        onCreate(db);
    }
}*/
