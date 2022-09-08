package com.exoapp;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class ActivityTipoEvalu extends AppCompatActivity {
    Button btnopc1;
    Button btnopc2;
    Button btnopc3;
    Button btnopc4;
    Button btnopc5;
    Button btnopc6;
    TextView VersionActual;
    private AdView AdView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_evalu);
        getSupportActionBar().setTitle("Tipo de evaluación");

        btnopc1 = findViewById(R.id.btnopc1);
        btnopc2 = findViewById(R.id.btnopc2);
        btnopc3 = findViewById(R.id.btnopc3);
        btnopc4 = findViewById(R.id.btnopc4);
        btnopc5 = findViewById(R.id.btnopc5);
        btnopc6 = findViewById(R.id.btnopc6);
        VersionActual = findViewById(R.id.tvVersionApp);

        MetodoBanner();

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
                int totalPuntos1 = 15;
                int totalPuntos2 = 15;
                int totalPuntos3 = 10;

                Intent intent = new Intent(v.getContext(), ActivityEvalu.class);
                intent.putExtra("ex_TotalPuntos1", String.valueOf(totalPuntos1));
                intent.putExtra("ex_TotalPuntos2", String.valueOf(totalPuntos2));
                intent.putExtra("ex_TotalPuntos3", String.valueOf(totalPuntos3));
                intent.putExtra("ex_EsPersonalizado", "No");
                startActivityForResult(intent, 0);
            }
        });

        btnopc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPuntos1 = 20;
                int totalPuntos2 = 20;
                int totalPuntos3 = 0;

                Intent intent = new Intent(v.getContext(), ActivityEvalu.class);
                intent.putExtra("ex_TotalPuntos1", String.valueOf(totalPuntos1));
                intent.putExtra("ex_TotalPuntos2", String.valueOf(totalPuntos2));
                intent.putExtra("ex_TotalPuntos3", String.valueOf(totalPuntos3));
                intent.putExtra("ex_EsPersonalizado", "No");
                startActivityForResult(intent, 0);
            }
        });

        btnopc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPuntos1 = 10;
                int totalPuntos2 = 10;
                int totalPuntos3 = 20;

                Intent intent = new Intent(v.getContext(), ActivityEvalu.class);
                intent.putExtra("ex_TotalPuntos1", String.valueOf(totalPuntos1));
                intent.putExtra("ex_TotalPuntos2", String.valueOf(totalPuntos2));
                intent.putExtra("ex_TotalPuntos3", String.valueOf(totalPuntos3));
                intent.putExtra("ex_EsPersonalizado", "No");
                startActivityForResult(intent, 0);
            }
        });

        btnopc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPuntos1 = 15;
                int totalPuntos2 = 20;
                int totalPuntos3 = 5;

                Intent intent = new Intent(v.getContext(), ActivityEvalu.class);
                intent.putExtra("ex_TotalPuntos1", String.valueOf(totalPuntos1));
                intent.putExtra("ex_TotalPuntos2", String.valueOf(totalPuntos2));
                intent.putExtra("ex_TotalPuntos3", String.valueOf(totalPuntos3));
                intent.putExtra("ex_EsPersonalizado", "No");
                startActivityForResult(intent, 0);
            }
        });

        btnopc5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPuntos1 = 10;
                int totalPuntos2 = 20;
                int totalPuntos3 = 5;

                Intent intent = new Intent(v.getContext(), ActivityEvalu.class);
                intent.putExtra("ex_TotalPuntos1", String.valueOf(totalPuntos1));
                intent.putExtra("ex_TotalPuntos2", String.valueOf(totalPuntos2));
                intent.putExtra("ex_TotalPuntos3", String.valueOf(totalPuntos3));
                intent.putExtra("ex_EsPersonalizado", "No");
                startActivityForResult(intent, 0);
            }
        });

        btnopc6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPuntos1 = 15;
                int totalPuntos2 = 20;
                int totalPuntos3 = 10;

                Intent intent = new Intent(v.getContext(), ActivityEvalu.class);
                intent.putExtra("ex_TotalPuntos1", String.valueOf(totalPuntos1));
                intent.putExtra("ex_TotalPuntos2", String.valueOf(totalPuntos2));
                intent.putExtra("ex_TotalPuntos3", String.valueOf(totalPuntos3));
                intent.putExtra("ex_EsPersonalizado", "No");
                startActivityForResult(intent, 0);
            }
        });
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_principal, menu);
        return true;
    }

    //Al usar el action overflow
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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



