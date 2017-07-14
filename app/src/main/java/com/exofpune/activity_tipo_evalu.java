package com.exofpune;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class activity_tipo_evalu extends AppCompatActivity {


    Button btnopc1;
    Button btnopc2;
    Button btnopc3;
    TextView VersionActual;
    TextView tvdesc;
    ProgressBar pb1;


    public static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ExoFPUNE";
    public static File Dir = new File(path);


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




        btnopc1 = (Button) findViewById(R.id.btnopc1);
        btnopc2 = (Button) findViewById(R.id.btnopc2);
        btnopc3 = (Button) findViewById(R.id.btnopc3);
        VersionActual = (TextView) findViewById(R.id.tvVersionPrincipal);
        tvdesc = (TextView) findViewById(R.id.tvDesc);
        pb1 = (ProgressBar) findViewById(R.id.pb1);




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
                Date FechaActual = new Date();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Date FechaDate = null;
                try {
                    FechaDate = formato.parse("18/08/2017");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (FechaActual.before(FechaDate)) {
                    Intent intent = new Intent(v.getContext(), activity_evalu1.class);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(activity_tipo_evalu.this, "La versión de prueba de ExoFPUNE caducó", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnopc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_evalu2.class);
                startActivityForResult(intent, 0);
            }
        });

        btnopc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_perso.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //Al usar el action overflow
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ao1 || id == R.id.ao3) {
            Log.d("myTag", "Abriendo acerca de");
            Intent i = new Intent(this, activity_acercade.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.ao2) {
            if (EstaConectadoInternet() == true) {
                comenzarActualizar();

                Log.d("myTag", "Abriendo comprobar actualizaciones");
                return true;
            } else {
                Toast toast = Toast.makeText(this, "No se detectó ninguna conexión a internet", Toast.LENGTH_SHORT);
                toast.show();
                Log.d("myTag", "Sin conexion a internet");
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }





    public Boolean EstaConectadoInternet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");
            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {//No se detecto internet
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }



    private Autoupdater autoupdater;
    private Context context;


    private void comenzarActualizar(){
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
        public void run() {
            //Volvemos el ProgressBar a invisible.
            tvdesc.setVisibility(View.INVISIBLE);
            pb1.setVisibility(View.INVISIBLE);

            //Comprueba que halla nueva versión.
            if(autoupdater.isNewVersionAvailable()){
                //Crea mensaje con datos de versión.
                String msj = "Se encontró una nueva actualizacion\n\n";
                msj += "\nVersion actual: " + autoupdater.getCurrentVersionName() + "\nVersion actual del codigo: (" + autoupdater.getCurrentVersionCode() + ")\n\n";
                msj += "\nVersion nueva: "  + autoupdater.getLatestVersionName()  + "\nVersion nueva del codigo: (" + autoupdater.getLatestVersionCode() +")";
                msj += "\n\n\nDesea Actualizar?";
                //Crea ventana de alerta.
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                dialog1.setMessage(msj);
                dialog1.setCancelable(false);
                dialog1.setNegativeButton("Más tarde", null);
                //Establece el boton de Aceptar y que hacer si se selecciona.
                dialog1.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Vuelve a poner el ProgressBar mientras se baja e instala.
                        tvdesc.setVisibility(View.VISIBLE);
                        pb1.setVisibility(View.VISIBLE);
                        //Se ejecuta el Autoupdater con la orden de instalar. Se puede poner un listener o no
                        autoupdater.InstallNewVersion(null);
                    }
                });

                //Muestra la ventana esperando respuesta.
                dialog1.show();
            }else{
                //No existen Actualizaciones.ees
                Log.d("", "No Hay actualizaciones");
            }
        }
    };
}

