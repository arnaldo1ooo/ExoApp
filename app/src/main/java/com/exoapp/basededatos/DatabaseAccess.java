package com.exoapp.basededatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAccess {
    private android.database.sqlite.SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new com.exoapp.basededatos.MySQLiteAssetHelper(context);
    }

    //Para que devuelva una sola instancia de la db
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //Para abrir la db
    public void abrir() {
        try {
            this.db = openHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d("Abrir bd", "Error al intentar abrir bd: " + e);
        }
    }

    //Para cerrar la db
    public void cerrar() {
        if (db != null) {
            this.db.close();
        }
    }

    //Para obtener la version actual de la BD
    public String VersionBD() {
        String version;
        if (db != null) {
            version = this.db.getVersion() + "";
        } else {
            this.db = openHelper.getWritableDatabase();
            version = this.db.getVersion() + "";
        }
        return version;
    }

    //Para buscar el registro por medio del indentificador
   public String[] ConsultaUnicaUniver(int identificador) {
        String tabla = "universidad";
        String columnas = "uni_codigo, uni_descripcion, uni_sigla";
        String sql = "SELECT " + columnas + " FROM " + tabla + " WHERE uni_codigo = '" + identificador + "'";
        Cursor cursor = db.rawQuery(sql, new String[]{});

        Log.d("Buscar registro", "SQL ejecutado: " + sql);
        Log.d("Buscar registros", "Cantidad resultados " + cursor.getCount());
        Log.d("Version de BD", db.getVersion() + "");

        String[] Array = new String[3];
        while (cursor.moveToNext() == true) {
            Array[0] = cursor.getString(0); //Columna 1
            Array[1] = cursor.getString(1); //Columna 2
            Array[2] = cursor.getString(2); //Columna 3
        }
        return Array;
    }

    //Para buscar el registro por medio del indentificador
    public String[] ConsultaUnicaFacu(int identificador) {
        String tabla = "facultad";
        String columnas = "fac_descripcion, fac_sigla, fac_universidad, fac_exoneracion, fac_nota2, fac_nota3, fac_nota4, fac_nota5";
        String sql = "SELECT " + columnas + " FROM " + tabla + " WHERE uni_codigo = '" + identificador + "'";
        Cursor cursor = db.rawQuery(sql, new String[]{});

        Log.d("Buscar registro", "SQL ejecutado: " + sql);
        Log.d("Buscar registros", "Cantidad resultados " + cursor.getCount());
        Log.d("Version de BD", db.getVersion() + "");

        String[] Array = new String[8];
        while (cursor.moveToNext() == true) {
            Array[0] = cursor.getString(0); //Columna 1
            Array[1] = cursor.getString(1); //Columna 2
            Array[2] = cursor.getString(2); //Columna 3
            Array[3] = cursor.getString(3); //Columna 4
            Array[4] = cursor.getString(4); //Columna 5
            Array[5] = cursor.getString(5); //Columna 6
            Array[6] = cursor.getString(6); //Columna 7
            Array[7] = cursor.getString(7); //Columna 8
        }
        return Array;
    }


    public Cursor ConsultaAllFacultad(int uniselect) {
        String tabla = "facultad";
        String columnas = "fac_codigo, fac_descripcion, fac_sigla, fac_universidad, fac_exoneracion, fac_nota2, fac_nota3, fac_nota4, fac_nota5";
        String sql = "SELECT " + columnas + " FROM " + tabla + " WHERE fac_universidad = " + uniselect;
        Cursor cursor = db.rawQuery(sql, new String[]{});

        Log.d("ConsultaAllFacultad", "SQL ejecutado: " + sql);
        Log.d("ConsultaAllFacultad", "Cantidad resultados: " + cursor.getCount());
        Log.d("ConsultaAllFacultad", "Version BD: " + db.getVersion() + "");

        return cursor;
    }
}
