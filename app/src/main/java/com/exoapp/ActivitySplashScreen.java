package com.exoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;

import com.exoapp.basededatos.DatabaseAccess;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import static android.content.ContentValues.TAG;


public class ActivitySplashScreen extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private Handler handlerSplash;
    private Runnable runnableSplash;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Para evitar que se cierre al oprimir boton atras (Android 16+ ya no llama a onBackPressed)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // No hacer nada
            }
        });

        TextView tvVersionApp = findViewById(R.id.tvVersionApp);
        TextView tvVersionBD = findViewById(R.id.tvVersionBD);

        tvVersionApp.setText("Versión de la App: " + ObtenerVersionApp());
        tvVersionBD.setText("Versión de la BD: " + ObtenerVersionBD());

        CargarInterstitial();
        handlerSplash = new Handler(Looper.getMainLooper());
        runnableSplash = new Runnable() {
            @Override
            public void run() {
                IniciarSiguienteActivity();
            }
        };
        handlerSplash.postDelayed(runnableSplash, 6000);
    }

    @Override
    protected void onDestroy() {
        //Cancelar la espera para que no se abra la pantalla principal dos veces (rotacion o salida anticipada)
        if (handlerSplash != null) {
            handlerSplash.removeCallbacks(runnableSplash);
        }
        super.onDestroy();
    }

    protected void onStart() { // Metodo al iniciar el activity
        super.onStart();
        //CargarIntestitialAd(); //Iniciamos el Anuncio Interstitial
    }

    // ------------- METODOS -------------

    private void IniciarSiguienteActivity() {
        //Ejecutar el siguiente activity
        if (isFinishing()) {
            return;
        }
        Intent intent = new Intent(ActivitySplashScreen.this, ActivityPrincipal.class);
        startActivity(intent);
        finish(); //El splash no debe quedar en la pila: al salir de Principal se cierra la app
    }

    private String ObtenerVersionApp() { //Obtener version app
        String versionApp = "";
        try {
            PackageInfo packageInfo;
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionApp = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede cargar la version actual de la app", Toast.LENGTH_LONG).show();
        }
        return versionApp;
    }


    private String ObtenerVersionBD() { //Revisa si hay acutalizacioens de la bd y obtiene la version de la bd
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.abrir();
        String versionBD = databaseAccess.VersionBD();
        databaseAccess.cerrar();

        return versionBD;
    }


    private void CargarInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.admob_interstitial_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // un ad esta cargado.

                mInterstitialAd = interstitialAd;
                Log.i(TAG, "Se cargó Interstitial");

                if (mInterstitialAd != null && !isFinishing() && !isDestroyed()) { //Mostrar el Interstitial (solo si el splash sigue abierto)
                    mInterstitialAd.show(ActivitySplashScreen.this);
                } else {
                    Log.d("TAG", "El anuncio intersticial aún no esta listo.");
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    public void CargarIntestitialAd() { //ID de ejemplo interstitial ca-app-pub-3940256099942544/1033173712 //Exoapp ca-app-pub-8474453660271942/1150495372
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, getString(R.string.admob_interstitial_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i(TAG, "onAdLoaded");

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Se llama cuando se descarta el contenido de pantalla completa.
                        Log.d("---IntestitialAd", "El anuncio fue descartado.");
                        IniciarSiguienteActivity();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Se llama cuando el contenido de pantalla completa no se muestra.
                        Log.d("---IntestitialAd", "El ad fallo al mostrarse");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Se llama cuando se muestra contenido en pantalla completa.
                        mInterstitialAd = null; // Asegúrese de establecer su referencia en nula para que no muestre una segunda vez
                        Log.d("---IntestitialAd", "Se mostró el ad");
                    }
                });

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(ActivitySplashScreen.this);
                } else {
                    Log.d("---IntestitialAd", "El interstitial aun no esta listo");
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.d("---IntestitialAd", "EonAdFailedToLoad Error al cargar Interstitial");
                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
                IniciarSiguienteActivity();
            }
        });
    }

}

