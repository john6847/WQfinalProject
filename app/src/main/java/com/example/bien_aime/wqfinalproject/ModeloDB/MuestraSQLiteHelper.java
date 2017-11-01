package com.example.bien_aime.wqfinalproject.ModeloDB;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bien-aime on 9/6/2017.
 */

public class MuestraSQLiteHelper extends SQLiteOpenHelper {
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate = "CREATE TABLE Muestras (codigo INTEGER PRIMARY KEY AUTOINCREMENT, nombreParametro TEXT, valor TEXT, fecha TEXT, dispositivo TEXT)";

    public MuestraSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MuestraSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
