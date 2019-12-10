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

import com.exoapp.basededatos.DatabaseAccess;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class ActivitySplashScreen extends Activity {
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

        TextView tvVersionApp = findViewById(R.id.tvVersionApp);
        TextView tvVersionBD = findViewById(R.id.tvVersionBD);

        tvVersionApp.setText("Versión de la App: " + ObtenerVersionApp());
        tvVersionBD.setText("Versión de la BD: " + ObtenerVersionBD());
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

    private String ObtenerVersionApp() {
        String versionApp = "";
        try {
            //Obtener version app
            PackageInfo packageInfo;
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionApp = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede cargar la version actual de la app", Toast.LENGTH_LONG).show();
        }

        return versionApp;
    }


    private String ObtenerVersionBD() {
        //Revisa si hay acutalizacioens de la bd y obtiene la version de la bd
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.abrir();
        String versionBD = databaseAccess.VersionBD();
        databaseAccess.cerrar();

        return versionBD;
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
        Intent intent = new Intent(ActivitySplashScreen.this, ActivitySelecfacultad.class);
        startActivity(intent);
        seAbrióNextActivity = true;
        finish();
    }
}
