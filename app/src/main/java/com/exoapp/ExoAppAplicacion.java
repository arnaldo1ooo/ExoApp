package com.exoapp;

import android.app.Application;

import com.exoapp.utilidades.Tema;
import com.google.android.gms.ads.MobileAds;

public class ExoAppAplicacion extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Tema.AplicarGuardado(this); // modo claro/oscuro elegido por el usuario
        MobileAds.initialize(this); // inicializa el SDK de AdMob
    }
}
