package com.exoapp.utilidades;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.exoapp.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Metodos {

    public void OcultarTeclado(View v, Activity elActivity) {
        InputMethodManager imm = (InputMethodManager) elActivity.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /*private void MetodoBanner(AdView elAdview, int elElementoAdview) {
        elAdview = findViewById(elElementoAdview);
        AdRequest adRequest = new AdRequest.Builder().build();
        elAdview.loadAd(adRequest);
        elAdview.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Código a ejecutar cuando un anuncio termina de cargarse.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Código a ejecutar cuando falla una solicitud de anuncio.
            }

            @Override
            public void onAdOpened() {
                // Código que se ejecutará cuando un anuncio abra una
                // superposición que cubre la pantalla.
            }

            @Override
            public void onAdClicked() {
                // Código que se ejecutará cuando el usuario
                // haga clic en un anuncio.
            }

            @Override
            public void onAdLeftApplication() {
                // Código a ejecutar cuando el usuario
                // ha abandonado la aplicación.
            }

            @Override
            public void onAdClosed() {
                // Código a ejecutar cuando el usuario está a punto de regresar
                // a la aplicación después de pulsar en un anuncio.
            }
        });
    }*/
}
