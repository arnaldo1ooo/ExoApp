package com.exoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exoapp.basededatos.DatabaseAccess;
import com.exoapp.recyclerview.rv_listafacu.itemListaFacu;
import com.exoapp.recyclerview.rv_listafacu.rvListaFacu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class ActivitySelecfacultad extends AppCompatActivity {
    private RecyclerView recyclerview;
    private RecyclerView.Adapter adaptador;
    private LinearLayoutManager layoutManager;
    private AdView adView;
    private TextView tvTitulo;
    private int iduniselect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecfacu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //Asignar icono

        tvTitulo = findViewById(R.id.tvTitulo);


        iduniselect = 1;

        MetodoUnicoUni(iduniselect);

        CargarConsultaaRV();
        MetodoBanner();

    }


    private void CargarConsultaaRV() {
        //Se crea el recyclerview y adaptador
        recyclerview = (RecyclerView) findViewById(R.id.rvPrincipal);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        //Adaptador
        adaptador = new rvListaFacu(this, MetodoAllFacultad());


        //OnClick
        ((rvListaFacu) adaptador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo el identificador seleccionado

                int idfacuselect = MetodoAllFacultad().get(recyclerview.getChildAdapterPosition(view)).getCodigo();
                int exoneracion = MetodoAllFacultad().get(recyclerview.getChildAdapterPosition(view)).getExoneracion();
                double nota2 = MetodoAllFacultad().get(recyclerview.getChildAdapterPosition(view)).getNota2();
                double nota3 = MetodoAllFacultad().get(recyclerview.getChildAdapterPosition(view)).getNota3();
                double nota4 = MetodoAllFacultad().get(recyclerview.getChildAdapterPosition(view)).getNota4();
                double nota5 = MetodoAllFacultad().get(recyclerview.getChildAdapterPosition(view)).getNota5();

                System.out.println("Codigo; " + idfacuselect + "    Exo: " + exoneracion);
                System.out.println("Nota2: " + nota2 + ", Nota3: " + nota3 + ", Nota4: " + nota4 + ", Nota5: " + nota5);



                PreguntaSiElegirFacu();
            }
        });


        recyclerview.setAdapter(adaptador);

        //Linea divisor de RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerview.addItemDecoration(dividerItemDecoration);
    }


    private void MetodoUnicoUni(int identificador) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.abrir();

        String[] Array = databaseAccess.ConsultaUnicaUniver(identificador);

        if (Array[0] == null) { //Si array está vacio
            Toast.makeText(this, "No se encontró ningún registro", Toast.LENGTH_SHORT).show();
        } else {
            String universidad = Array[1];
            String sigla = Array[2];
            tvTitulo.setText("Facultades de la " + universidad + " (" + sigla + ")");

        }
        databaseAccess.cerrar();
    }


    private ArrayList<itemListaFacu> MetodoAllFacultad() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.abrir();
        Cursor cursor = databaseAccess.ConsultaAllFacultad(iduniselect);
        ArrayList<itemListaFacu> listItems = new ArrayList<>();

        int codigo;
        String facultad;
        String sigla;
        int exoneracion;
        double nota2;
        double nota3;
        double nota4;
        double nota5;

        while (cursor.moveToNext() == true) {
            codigo = cursor.getInt(0); //Columna 0
            facultad = cursor.getString(1); //Columna 1
            sigla = cursor.getString(2);
            exoneracion = cursor.getInt(4);
            nota2 = cursor.getDouble(5);
            nota3 = cursor.getDouble(6);
            nota4 = cursor.getDouble(7);
            nota5 = cursor.getDouble(8);


            int idimagen = getResources().getIdentifier("facu_" + codigo, "drawable", getPackageName());
            if (idimagen == 0) { //Si imagen no existe
                listItems.add(new itemListaFacu(codigo, R.drawable.imagen_0, facultad, sigla, exoneracion, nota2, nota3, nota4, nota5));
            } else {
                listItems.add(new itemListaFacu(codigo, idimagen, facultad, sigla, exoneracion, nota2, nota3, nota4, nota5));
            }
        }
        Log.d("CargarConsultaaRV", "Se cargó todos los registros de la tabla facultades al Recycler View");
        databaseAccess.cerrar();
        return listItems;
    }

    private void MetodoBanner() {
        adView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
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



    private void PreguntaSiElegirFacu() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .setTitle("¿Asignar por defecto la facultad seleccionada?")
                .setCancelable(false)
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Apreto en no");



                        Intent i = new Intent(ActivitySelecfacultad.this, ActivityPrincipal.class );
                        startActivity(i);
                    }
                })
                .setNegativeButton("Si", new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Apreto en si");
                    }
                }).show();
    }
}
