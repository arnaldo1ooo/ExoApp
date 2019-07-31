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
        BaseDeDatos.execSQL("CREATE TABLE universidad(uni_codigo int PRIMARY KEY, uni_tipo_institucion int, uni_direccion String, uni_correo String, uni_telef String)");
        BaseDeDatos.execSQL("INSERT INTO universidad(uni_tipo_institucion, uni_direccion, uni_correo, uni_telef) " +
                "values('UNIVERSIDAD NACIONAL DEL ESTE','1', 'Km 7 Monday, Ciudad del Este', 'une@hotmail.com', '0973658622')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
