package com.exoapp.utilidades;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Modo claro / oscuro de la app. Se guarda en SharedPreferences y se aplica al iniciar la app
 * (ExoAppAplicacion) y cada vez que el usuario lo cambia en Configuracion.
 */
public class Tema {
    public static final int SISTEMA = 0; // sigue el modo del telefono
    public static final int CLARO = 1;
    public static final int OSCURO = 2;

    private static final String ARCHIVO = "ajustes";
    private static final String CLAVE_TEMA = "tema";

    public static int Leer(Context context) {
        return context.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE).getInt(CLAVE_TEMA, SISTEMA);
    }

    /* Guarda la eleccion y la aplica (las pantallas abiertas se vuelven a dibujar solas). */
    public static void Guardar(Context context, int modo) {
        context.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE).edit().putInt(CLAVE_TEMA, modo).apply();
        AplicarModo(modo);
    }

    public static void AplicarGuardado(Context context) {
        AplicarModo(Leer(context));
    }

    private static void AplicarModo(int modo) {
        if (modo == CLARO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (modo == OSCURO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
