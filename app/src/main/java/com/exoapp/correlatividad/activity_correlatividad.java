package com.exoapp.correlatividad;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exoapp.R;
import com.exoapp.recyclerview.correlatividad.itemCorrelatividad;
import com.exoapp.recyclerview.correlatividad.rvCorrelatividad;

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
        adaptador = new rvCorrelatividad(this, MetodoListItem());

        //OnClick
        /*((rvLista) adaptador).setOnClickListener(new View.OnClickListener() {
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

    private ArrayList<itemCorrelatividad> MetodoListItem() {
        ArrayList<itemCorrelatividad> listItems = new ArrayList<>();

        String carreraseleccionada = getIntent().getExtras().getString("carrera");
        String mallaseleccionada = getIntent().getExtras().getString("malla");

        Log.d("CarreraSeleccionada", carreraseleccionada);
        Log.d("MallaSeleccionada", mallaseleccionada);
        switch (carreraseleccionada) {
            case "ANÁLISIS DE SISTEMAS":
                switch (mallaseleccionada) {
                    case "PLAN 2017":
                        listItems.add(new itemCorrelatividad("MATERIA 1", "Descripcion 1"));
                        listItems.add(new itemCorrelatividad("MATERIA 2", "Descripcion 2"));
                        listItems.add(new itemCorrelatividad("MATERIA 3", "Descripcion 3"));
                        listItems.add(new itemCorrelatividad("MATERIA 4", "Descripcion 4"));
                        break;

                    case "PLAN VIGENTE":
                        listItems.add(new itemCorrelatividad("MATERIA 1", "Descripcion 1"));
                        listItems.add(new itemCorrelatividad("MATERIA 2", "Descripcion 2"));
                        listItems.add(new itemCorrelatividad("MATERIA 3", "Descripcion 3"));
                        listItems.add(new itemCorrelatividad("MATERIA 4", "Descripcion 4"));
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
                        listItems.add(new itemCorrelatividad("INGLÉS TÉCNICO I", "Descripcion 1"));
                        listItems.add(new itemCorrelatividad("COMUNICACIÓN ORAL Y ESCRITA EN CASTELLANO I", "Descripcion 2"));
                        listItems.add(new itemCorrelatividad("LENGUA Y CULTURA GUARANÍ I", "Descripcion 3"));
                        listItems.add(new itemCorrelatividad("GEOGRAFÍA TURÍSTICA DEL PARAGUAY Y LATINOAMERICANA", "Descripcion 4"));
                        listItems.add(new itemCorrelatividad("INTRODUCCIÓN AL TURISMO Y A LA ESTRUCTURA DE MERCADO", "Descripcion 4"));
                        listItems.add(new itemCorrelatividad("TEORÍA SOCIOLÓGICA DEL TURISMO", "Descripcion 6"));
                        break;

                    case "PLAN VIGENTE":
                        listItems.add(new itemCorrelatividad("IDIOMAS I", "Descripcion 1"));
                        listItems.add(new itemCorrelatividad("LENGUA GUARANÍ I", "Descripcion 2"));
                        listItems.add(new itemCorrelatividad("LENGUA CASTELLANA I", "Descripcion 3"));
                        listItems.add(new itemCorrelatividad("SOCIOLOGÍA TURÍSTICA", "Descripcion 4"));
                        listItems.add(new itemCorrelatividad("HISTORIA DE LA CULTURA PARAGUAYA", "Descripcion 5"));
                        listItems.add(new itemCorrelatividad("GEOGRAFÍA TURÍSTICA DEL PARAGUAY", "Descripcion 6"));
                        break;

                    default:
                        Toast.makeText(activity_correlatividad.this, "La malla recibida de la carrera " + carreraseleccionada + ", no coincide con ninguno", Toast.LENGTH_SHORT).show();
                        break;
                }
        }

        return listItems;
    }
}
