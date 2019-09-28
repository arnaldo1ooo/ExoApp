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


public class splash_screen extends Activity {
    private TextView versionApp;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PublicidadInterstitial();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        versionApp = findViewById(R.id.tvVersionSplash);

        try {
            PackageInfo packageInfo;
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionApp.setText("v" + String.valueOf(packageInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede cargar la version actual!", Toast.LENGTH_LONG).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }else{
                    AbrirNextActivity();
                }
            }
        }, 6000);
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
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show(); //Mostrar el Interstittial luego de crearlo
                        onSaveInstanceState(null);
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
                    AbrirNextActivity();
                }
            });
        } catch (Exception e) {
            Log.d("Error", "Error metodo PublicidadInterstitial() " + e);
        }
    }

    private void AbrirNextActivity() {
        Intent intent = new Intent(splash_screen.this, activity_principal.class);
        startActivity(intent);
    }
}
