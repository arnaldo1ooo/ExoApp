package com.exoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class ActivitySplashScreen extends Activity {
    private TextView versionApp;
    private InterstitialAd mInterstitialAd;
    private Boolean seAbrióNextActivity = false;

    //Para evitar que se cierre al oprimir boton atras
    @Override
    public void onBackPressed() {
        Log.d("BotonAtras", "Se oprimió el botón atrás");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        versionApp = findViewById(R.id.tvVersionSplash);

        ObtenerVersion();
        PublicidadInterstitial();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("Splash Screen", "Se terminó el tiempo de espera");
                if (seAbrióNextActivity == false) {
                    Log.d("Interstitial", "No está cargando");
                    AbrirNextActivity();
                }
            }
        }, 5000);
    }

    private void ObtenerVersion() {
        try {
            PackageInfo packageInfo;
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionApp.setText("v" + String.valueOf(packageInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede cargar la version actual!", Toast.LENGTH_LONG).show();
        }
    }

    private void PublicidadInterstitial() {
        try {
            MobileAds.initialize(this, "ca-app-pub-8474453660271942/1150495372");

            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-8474453660271942/1150495372");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() { // Código que se ejecutará cuando un anuncio termine de cargarse.
                    Log.d("Interstititial", "Se carga el metodo onAdLoaded");
                    if (seAbrióNextActivity == false) {
                        AbrirNextActivity();
                    }
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show(); //Mostrar el Interstittial luego de crearlo
                    }
                }

                @Override
                public void onAdOpened() {// Código que se ejecutará cuando se muestre el anuncio.

                }

                @Override
                public void onAdFailedToLoad(int errorCode) {// Código que se ejecutará cuando una solicitud de anuncio falle.

                }

                @Override
                public void onAdLeftApplication() {// Código que se ejecutará cuando el usuario haya abandonado la aplicación.
                }

                @Override
                public void onAdClosed() {// Código que se ejecutará cuando el anuncio intersticial esté cerrado.
                    Log.d("Interstititial", "Se carga el metodo onAdClosed");
                    if (seAbrióNextActivity == false) {
                        AbrirNextActivity();
                    }
                }
            });
        } catch (Exception e) {
            Log.d("Error", "Error metodo PublicidadInterstitial() " + e);
        }
    }

    private void AbrirNextActivity() {
        Intent intent = new Intent(ActivitySplashScreen.this, ActivityPrincipal.class);
        startActivity(intent);
        seAbrióNextActivity = true;
        finish();
    }
}
