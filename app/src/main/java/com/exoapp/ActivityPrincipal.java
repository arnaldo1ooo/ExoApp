package com.exoapp;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.exoapp.basededatos.DatabaseAccess;
import com.exoapp.conversion.ActivityConversion;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ActivityPrincipal extends AppCompatActivity {
    private AdView AdView1;



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // El bundle sera guardado y enviado al onCreate() de la Activity.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Pregunta si realmente quieres salir de app al oprimir atras (Android 16+ ya no envia KEYCODE_BACK)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                CerrarAplicacion();
            }
        });

        //Activar icono en actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        TextView tvVersionApp = findViewById(R.id.tvVersionApp);
        TextView tvVersionBD = findViewById(R.id.tvVersionBD);

        tvVersionApp.setText("Versión de la App: " + ObtenerVersionApp());
        tvVersionBD.setText("Versión de la BD: " + ObtenerVersionBD());

        MetodoBanner();
    }

    public void Evaluaciones(View view) {
        Intent intent = new Intent(view.getContext(), ActivityTipoEvalu.class);
        startActivity(intent);
    }

    public void Bonificacion(View view) {
        Intent intent = new Intent(view.getContext(), ActivityBonificacion.class);
        startActivity(intent);
    }

    public void PorcAPunto(View view) {
        Intent intent = new Intent(view.getContext(), ActivityConversion.class);
        startActivity(intent);
    }

    public void Comentarios(View view) {
        Intent intent = new Intent(view.getContext(), ActivityComentarios.class);
        startActivity(intent);
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
            public void onAdClosed() {
                // Código a ejecutar cuando el usuario está a punto de regresar
                // a la aplicación después de pulsar en un anuncio.
            }
        });
    }


    //Cargar menu al actionbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_principal, menu);
        return true;
    }
    //Items de menu del actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        //Se usa if en vez de switch porque los R.id ya no son constantes (AGP 8+/9)
        int id = item.getItemId();

        if (id == R.id.configuracion) {
            Intent i = new Intent(this, ActivityConfiguracion.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.escala) {
            Log.d("Opciones", "Abriendo escala");
            Intent i = new Intent(this, ActivityEscala.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.politicas) {
            Log.d("Opciones", "Abriendo politicas de privacidad");
            Intent i = new Intent(this, ActivityPoliticasPrivacidad.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.acercade) {
            Log.d("Opciones", "Abriendo acerca de");
            Intent i = new Intent(this, ActivityAcercade.class);
            startActivity(i);
            return true;
        }

        Toast.makeText(this, "Error en menu actionbar " + item.getItemId(), Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);

    }

    private void CerrarAplicacion() {
        com.exoapp.utilidades.Dialogos.Confirmar(this, R.drawable.ic_dialogo_salir,
                getString(R.string.dlg_salir_titulo), getString(R.string.dlg_salir_mensaje),
                getString(R.string.dlg_quedarme), null,
                getString(R.string.dlg_salir), new Runnable() {
                    @Override
                    public void run() {
                        finish(); // Manda la app a segundo plano / la cierra
                    }
                }, true);
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

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
}



