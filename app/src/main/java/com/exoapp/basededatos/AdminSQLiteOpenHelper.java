package com.exoapp.basededatos;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        //BaseDeDatos.execSQL("CREATE TABLE ciudad(codigo int PRIMARY KEY, descripcion text)");
        BaseDeDatos.execSQL("CREATE TABLE favorito(fav_codigo int PRIMARY KEY, fav_universidad int, fav_facultad int, fav_carrera int, fav_malla int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
