package com.exofpune;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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

import java.io.File;

import java.io.IOException;
import java.text.DecimalFormat;


public class activity_tipo_evalu extends AppCompatActivity {
    Button btnopc1;
    Button btnopc2;
    Button btnopc3;
    Button btnopc4;
    TextView VersionActual;


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

        comenzarActualizar();

        EliminarFichero("/download/app.apk");

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    //Al usar el action overflow
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ao1) {
            if (EstaConectadoInternet() == true) {
                Log.d("Opciones", "Abriendo comprobar actualizaciones");
                comenzarActualizar();
                return true;
            } else {
                Toast toast = Toast.makeText(this, "No se detectó ninguna conexión a internet", Toast.LENGTH_SHORT);
                toast.show();
                Log.d("Opciones", "Sin conexion a internet");
                return true;
            }
        }

        if (id == R.id.ao2) {
            Log.d("Opciones", "Abriendo acerca de");
            Intent i = new Intent(this, activity_acercade.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public Boolean EstaConectadoInternet() {
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
    }


    private Autoupdater autoupdater;
    private Context context;

    public void comenzarActualizar() {
        //Para tener el contexto mas a mano.
        context = this;
        //Creamos el Autoupdater.
        autoupdater = new Autoupdater(this);
        //Ponemos a correr el ProgressBar.
        //Ejecutamos el primer metodo del Autoupdater.
        autoupdater.DownloadData(finishBackgroundDownload);
    }

    /**
     * Codigo que se va a ejecutar una vez terminado de bajar los datos.
     */


    private Runnable finishBackgroundDownload = new Runnable() {
        @Override
        public void run() {//Cuando descarguetodo

            //Comprueba que haya nueva versión.
            if (autoupdater.isNewVersionAvailable()) {
                MensajeActualizacion();
            } else {
                //No existen Actualizaciones.ees
                Log.d("", "No hay actualizaciones");
                Toast.makeText(getApplicationContext(), "No hay actualizaciones", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private ProgressDialog ProgresoDescarga;

    private void MensajeActualizacion() {
        //Crea mensaje con datos de versión.
        String msj = "Se encontró una nueva actualizacion\n\n";

        msj += "\nVersion actual: " + autoupdater.getCurrentVersionName() + "\nVersion actual del codigo: (" + autoupdater.getCurrentVersionCode() + ")\n\n";
        msj += "\nVersion nueva: " + autoupdater.getLatestVersionName() + "\nVersion nueva del codigo: (" + autoupdater.getLatestVersionCode() + ")";
        msj += "\n\n\nDesea Actualizar?";
        //Crea ventana de alerta.
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
        dialog1.setMessage(msj);
        dialog1.setCancelable(false);
        dialog1.setNegativeButton("Más tarde", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        //Establece el boton de Aceptar y que hacer si se selecciona.
        dialog1.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("Inicio", "Inicio de descarga de actualizacion");

                try {
                    VerificarPermisos(activity_tipo_evalu.this);  //Verifica si tiene permisos para escribir en memoria

                    autoupdater.InstallNewVersion(null); //Se ejecuta el Autoupdater con la orden de instalar. Se puede poner un listener o no

                    ProgresoDescarga = new ProgressDialog(activity_tipo_evalu.this);
                    ProgresoDescarga.setMessage("Descargando actualización ExoFPUNE " + autoupdater.getLatestVersionName());
                    ProgresoDescarga.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    ProgresoDescarga.setCancelable(false);
                    ProgresoDescarga.setIndeterminate(false);
                    ProgresoDescarga.setProgress(0);
                    ProgresoDescarga.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar Actualización", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    ProgresoDescarga.show();
                    final DecimalFormat df = new DecimalFormat("#.##");
                    final double TiempoTotalProgreso = 100;
                    final Thread t = new Thread() {
                        @Override
                        public void run() {
                            int jumpTime = 0;
                            String TamañoTotal = "";
                            String TamañoDescargado = "";
                            double ProgresoDescarga1 = 0;
                            String VelDescarga = "";
                            while (jumpTime < TiempoTotalProgreso) {
                                ProgresoDescarga1 = autoupdater.getTamañoDescargado(); //El valor anterior

                                Log.d("TamañoDescargado2", String.valueOf(TamañoDescargado));
                                try {
                                    jumpTime = (int) ((autoupdater.getTamañoDescargado() * 100) / autoupdater.getTamañoTotal());
                                    TamañoTotal = df.format(autoupdater.getTamañoTotal() / 1048576);
                                    TamañoDescargado = df.format(autoupdater.getTamañoDescargado() / 1048576);
                                    ProgresoDescarga.setProgressNumberFormat(TamañoDescargado + " MB / " + TamañoTotal + " MB       " + VelDescarga);
                                    //ProgresoDescarga.setProgressPercentFormat(null); //Oculta el porcentaje
                                    ProgresoDescarga.setProgress(jumpTime);
                                    sleep(1000); //Actualiza cada 1 segundo;
                                    VelDescarga = df.format((autoupdater.getTamañoDescargado() - ProgresoDescarga1) / 1024) + " KB/Seg";
                                    Log.d("VelocidadDescarga", VelDescarga);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            ProgresoDescarga.dismiss();//Oculta el ProgressDialog
                        }
                    };
                    t.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);  //Vibrar
        v.vibrate(1000);
        //Muestra la ventana esperando respuesta.
        dialog1.show();

    }


    public void EliminarFichero(String Directorio) {
        File file = new File(Directorio);

        if (file.exists()) {
            String deleteCmd = "rm -r " + Environment.getExternalStorageDirectory() + Directorio; //SD --> mas directorio ejemplo /download/apk.app
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
                Log.d("EliminarFichero", "Fichero eliminado con éxito ");
            } catch (IOException e) {
                Log.d("EliminarFichero", "" + e);
            }
        }
    }


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



