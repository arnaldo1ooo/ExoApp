package com.exoapp.correlatividad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exoapp.R;
import com.exoapp.recyclerview.correlatividad.item_correlatividad;
import com.exoapp.recyclerview.correlatividad.rv_correlatividad;
import com.exoapp.recyclerview.lista.item_lista;
import com.exoapp.recyclerview.lista.rv_lista;

import java.util.ArrayList;

public class activity_correlatividad extends AppCompatActivity {

    private RecyclerView recyclerview;
    private RecyclerView.Adapter adaptador;
    private LinearLayoutManager layoutManager;
    private CheckBox cbMateria;
    private String tvDescripcion;
    private TextView tvTitulo;
    private TextView tvSemestre;
    private String carreraseleccionada;
    private String mallaseleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correlatividad);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Correlatividad");


        //Ponemos el titulo
        carreraseleccionada = getIntent().getExtras().getString("carrera");
        mallaseleccionada = getIntent().getExtras().getString("malla");

        tvSemestre = (TextView) findViewById(R.id.tvSemestre);

        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        tvTitulo.setText(mallaseleccionada + " - " + carreraseleccionada);

        //Se crea el recyclerview y adaptador
        recyclerview = (RecyclerView) findViewById(R.id.rvPrincipal);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        //Adaptador
        adaptador = new rv_correlatividad(this, MetodoListItem());

        //OnClick
        /*((rv_lista) adaptador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo el titulo del item seleccionado
                String tituloitem = MetodoListItem().get(
                        recyclerview.getChildAdapterPosition(view)).getTitulo();
                String malla = "";
                Intent intent;

                switch (tituloitem) {
                    //POLITECNICA FPUNE
                    case "PLAN 2017":
                        malla = tituloitem;
                        break;

                    case "PLAN VIGENTE":
                        malla = tituloitem;
                        break;


                    default:
                        Toast.makeText(activity_correlatividad.this, "No se seleccionó ningún item", Toast.LENGTH_SHORT).show();
                        break;
                }

                intent = new Intent(activity_corremalla.this, com.exoapp.correlatividad.activity_corremalla.class);
                intent.putExtra("malla", malla);
                startActivity(intent);

                //Imprime el titulo del item seleccionado
                Toast.makeText(getApplicationContext(), "Selección: " + MetodoListItem().get(
                        recyclerview.getChildAdapterPosition(view)).getTitulo(), Toast.LENGTH_SHORT).show();
            }
        });*/

        recyclerview.setAdapter(adaptador);

        //Linea divisor de RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerview.addItemDecoration(dividerItemDecoration);

    }

    private ArrayList<item_correlatividad> MetodoListItem() {
        ArrayList<item_correlatividad> listItems = new ArrayList<>();

        String carreraseleccionada = getIntent().getExtras().getString("carrera");
        String mallaseleccionada = getIntent().getExtras().getString("malla");

        Log.d("CarreraSeleccionada", carreraseleccionada);
        Log.d("MallaSeleccionada", mallaseleccionada);
        switch (carreraseleccionada) {
            case "ANÁLISIS DE SISTEMAS":
                switch (mallaseleccionada) {
                    case "PLAN 2017":
                        listItems.add(new item_correlatividad("MATERIA 1", "Descripcion 1"));
                        listItems.add(new item_correlatividad("MATERIA 2", "Descripcion 2"));
                        listItems.add(new item_correlatividad("MATERIA 3", "Descripcion 3"));
                        listItems.add(new item_correlatividad("MATERIA 4", "Descripcion 4"));
                        break;

                    case "PLAN VIGENTE":
                        listItems.add(new item_correlatividad("MATERIA 1", "Descripcion 1"));
                        listItems.add(new item_correlatividad("MATERIA 2", "Descripcion 2"));
                        listItems.add(new item_correlatividad("MATERIA 3", "Descripcion 3"));
                        listItems.add(new item_correlatividad("MATERIA 4", "Descripcion 4"));
                        break;

                    default:
                        Toast.makeText(activity_correlatividad.this, "La malla recibida de la carrera " + carreraseleccionada + ", no coincide con ninguno", Toast.LENGTH_SHORT).show();
                        break;
                }

            default:
                Toast.makeText(activity_correlatividad.this, "La carrera recibida no coincide con ninguna de la bd", Toast.LENGTH_SHORT).show();
                break;


            case "TURISMO":
                switch (mallaseleccionada) {
                    case "PLAN 2017":
                        tvSemestre.setText("1º Semestre");
                        listItems.add(new item_correlatividad("INGLÉS TÉCNICO I", "Descripcion 1"));
                        listItems.add(new item_correlatividad("COMUNICACIÓN ORAL Y ESCRITA EN CASTELLANO I", "Descripcion 2"));
                        listItems.add(new item_correlatividad("LENGUA Y CULTURA GUARANÍ I", "Descripcion 3"));
                        listItems.add(new item_correlatividad("GEOGRAFÍA TURÍSTICA DEL PARAGUAY Y LATINOAMERICANA", "Descripcion 4"));
                        listItems.add(new item_correlatividad("INTRODUCCIÓN AL TURISMO Y A LA ESTRUCTURA DE MERCADO", "Descripcion 4"));
                        listItems.add(new item_correlatividad("TEORÍA SOCIOLÓGICA DEL TURISMO", "Descripcion 6"));
                        break;

                    case "PLAN VIGENTE":
                        listItems.add(new item_correlatividad("IDIOMAS I", "Descripcion 1"));
                        listItems.add(new item_correlatividad("LENGUA GUARANÍ I", "Descripcion 2"));
                        listItems.add(new item_correlatividad("LENGUA CASTELLANA I", "Descripcion 3"));
                        listItems.add(new item_correlatividad("SOCIOLOGÍA TURÍSTICA", "Descripcion 4"));
                        listItems.add(new item_correlatividad("HISTORIA DE LA CULTURA PARAGUAYA", "Descripcion 5"));
                        listItems.add(new item_correlatividad("GEOGRAFÍA TURÍSTICA DEL PARAGUAY", "Descripcion 6"));
                        break;

                    default:
                        Toast.makeText(activity_correlatividad.this, "La malla recibida de la carrera " + carreraseleccionada + ", no coincide con ninguno", Toast.LENGTH_SHORT).show();
                        break;
                }
        }

        return listItems;
    }
}
