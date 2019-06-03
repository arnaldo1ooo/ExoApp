package com.exofpune;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class activity_tipo_evalu extends AppCompatActivity {
    Button btnopc1;
    Button btnopc2;
    Button btnopc3;
    Button btnopc4;
    TextView VersionActual;
    private InterstitialAd mInterstitialAd;

    //impedir retroceder a activity anterior
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_evalu);

        PublicidadInterstitial();


        btnopc1 = (Button) findViewById(R.id.btnopc1);
        btnopc2 = (Button) findViewById(R.id.btnopc2);
        btnopc3 = (Button) findViewById(R.id.btnopc3);
        btnopc4 = (Button) findViewById(R.id.btnopc4);
        VersionActual = (TextView) findViewById(R.id.tvVersionPrincipal);


        //Obtener version actual de la app
        try {
            PackageInfo packageInfo;
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            VersionActual.setText("v" + String.valueOf(packageInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede cargar la version actual!", Toast.LENGTH_LONG).show();
        }


        btnopc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), activity_evalu.class);
                intent.putExtra("TotalPuntos1", "15");
                intent.putExtra("TotalPuntos2", "15");
                intent.putExtra("TotalPuntos3", "10");
                intent.putExtra("EsPersonalizado", "No");
                startActivityForResult(intent, 0);
            }
        });

        btnopc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_evalu.class);
                intent.putExtra("TotalPuntos1", "20");
                intent.putExtra("TotalPuntos2", "20");
                intent.putExtra("TotalPuntos3", "0");
                intent.putExtra("EsPersonalizado", "No");
                startActivityForResult(intent, 0);
            }
        });

        btnopc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_evalu.class);
                intent.putExtra("TotalPuntos1", "10");
                intent.putExtra("TotalPuntos2", "10");
                intent.putExtra("TotalPuntos3", "20");
                intent.putExtra("EsPersonalizado", "No");
                startActivityForResult(intent, 0);
            }
        });

        btnopc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), activity_perso.class);
               startActivityForResult(intent, 0);
            }
        });
    }

    private void PublicidadInterstitial() {
        MobileAds.initialize(this,"ca-app-pub-8474453660271942/1150495372");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8474453660271942/1150495372");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() { // Código que se ejecutará cuando un anuncio termine de cargarse.
                if ( mInterstitialAd.isLoaded()) {
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

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fb, menu);
        return true;
    }

    //Al usar el action overflow
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ao1) {
            Intent i = new Intent(this, activity_politicas_privacidad.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.ao2) {
            Log.d("Opciones", "Abriendo acerca de");
            Intent i = new Intent(this, activity_acercade.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*public Boolean EstaConectadoInternet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");
            int val = p.waitFor();
            boolean reachable = (val == 0);
            Log.d("Internet", String.valueOf(reachable));
            return reachable;

        } catch (Exception e) {//No se detecto internet
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }*/


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param ElActivity
     */
    public static void VerificarPermisos(Activity ElActivity) {
        Log.d("Verificando permisos..", "");
        // Check if we have write Permiso
        int Permiso = ActivityCompat.checkSelfPermission(ElActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Permiso != PackageManager.PERMISSION_GRANTED) {
            // We don't have Permiso so prompt the user
            ActivityCompat.requestPermissions(
                    ElActivity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}



