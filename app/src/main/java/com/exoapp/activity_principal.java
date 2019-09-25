package com.exoapp;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.exoapp.correlatividad.correuniversidad.activity_correuniversidad;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class activity_principal extends AppCompatActivity {
    TextView VersionActual;
    private InterstitialAd mInterstitialAd;
    private AdView AdView1;
    boolean SeActivoPublicidad = false;


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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // El bundle sera guardado y enviado al onCreate() de la Activity.
        savedInstanceState.putBoolean("SeActivoPublicidad", SeActivoPublicidad);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Al volver a restaurar el intent volvemos al ultimo estado de esta variable savedInstanceState.
        SeActivoPublicidad = savedInstanceState.getBoolean("SeActivoPublicidad");
        Log.d("Estado guardado",SeActivoPublicidad+"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        //Activar icono en actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        VersionActual = (TextView) findViewById(R.id.tvVersionPrincipal);

        PublicidadInterstitial();
        MetodoBanner();
        ObtenerVersionApp();
    }

    public void Evaluaciones(View view) {
        Intent intent = new Intent(view.getContext(), activity_tipo_evalu.class);
        startActivityForResult(intent, 0);
    }

    public void Bonificacion(View view) {
        Intent intent = new Intent(view.getContext(), activity_bonificacion.class);
        startActivityForResult(intent, 0);
    }

    public void Correlatividad(View view) {
        Intent intent = new Intent(view.getContext(), activity_correuniversidad.class);
        startActivityForResult(intent, 0);
    }


    private void ObtenerVersionApp() {
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
    }


    private void MetodoBanner() {
        AdView1 = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView1.loadAd(adRequest);
        AdView1.setAdListener(new AdListener() {
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
    }


    private void PublicidadInterstitial() {
        if (SeActivoPublicidad == false) {
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

                    }
                });
            } catch (Exception e) {
                Log.d("Error", "Error metodo PublicidadInterstitial() " + e);
            }
            SeActivoPublicidad = true;
        }
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


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
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


