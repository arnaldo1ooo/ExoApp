package com.exoapp.correlatividad.correcarrera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exoapp.R;
import com.exoapp.correlatividad.corremalla.activity_corremalla;
import com.exoapp.recyclerview.lista.item_lista;
import com.exoapp.recyclerview.lista.rv_lista;

import java.util.ArrayList;

public class activity_correcarrera extends AppCompatActivity {

    private RecyclerView recyclerview;
    private RecyclerView.Adapter adaptador;
    private LinearLayoutManager layoutManager;
    private TextView txtTitulo;
    private String facultadseleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correcarrera);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Seleccione la carrera");


        //Ponemos el titulo
        facultadseleccionada = getIntent().getExtras().getString("facultad");
        txtTitulo = (TextView) findViewById(R.id.tvTitulo);
        txtTitulo.setText("Carreras de la\n" + facultadseleccionada);

        //Se crea el recyclerview y adaptador
        recyclerview = (RecyclerView) findViewById(R.id.rvPrincipal);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        //Adaptador
        adaptador = new rv_lista(this, MetodoListItem());

        //OnClick
        ((rv_lista) adaptador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo el titulo del item seleccionado
                String tituloitem = MetodoListItem().get(
                        recyclerview.getChildAdapterPosition(view)).getTitulo();
                String carrera = "";
                Intent intent;

                switch (tituloitem) {
                    //POLITECNICA FPUNE
                    case "ANÁLISIS DE SISTEMAS":
                        carrera = tituloitem;
                        break;

                    case "TURISMO":
                        carrera = tituloitem;
                        break;

                    case "INGENIERÍA ELÉCTRICA":
                        carrera = tituloitem;
                        break;


                    default:
                        Toast.makeText(activity_correcarrera.this,"No se seleccionó ningún item", Toast.LENGTH_SHORT).show();
                        break;
                }

                intent = new Intent(activity_correcarrera.this, activity_corremalla.class);
                intent.putExtra("carrera", carrera);
                startActivity(intent);

                //Imprime el titulo del item seleccionado
                /*Toast.makeText(getApplicationContext(), "Selección: " + MetodoListItem().get(
                        recyclerview.getChildAdapterPosition(view)).getTitulo(), Toast.LENGTH_SHORT).show();*/
            }
        });

        recyclerview.setAdapter(adaptador);

        //Linea divisor de RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerview.addItemDecoration(dividerItemDecoration);

    }

    private ArrayList<item_lista> MetodoListItem(){
        ArrayList<item_lista> listItems = new ArrayList<>();

        String facultadseleccionada = getIntent().getExtras().getString("facultad");
        switch (facultadseleccionada) {
            case "FACULTAD POLITÉCNICA":
                listItems.add(new item_lista(R.drawable.carrerapolianalisis,"ANÁLISIS DE SISTEMAS","Ciudad: Ciudad del Este"));
                listItems.add(new item_lista(R.drawable.carrerapoliturismo,"TURISMO","Ciudad: Ciudad del Este"));
                listItems.add(new item_lista(R.drawable.carrerapolielectrica,"INGENIERÍA ELÉCTRICA","Ciudad: Ciudad del Este"));
                break;

            case "FACULTAD DE INGENIERÍA AGRONÓMICA":
                listItems.add(new item_lista(R.drawable.facuupecienciasagrarias,"INGENIERÍA AGRONÓMICA","Ciudad: Minga Guazú"));
                listItems.add(new item_lista(R.drawable.facuupecienciasagrarias,"INGENIERÍA AMBIENTAL","Ciudad: Minga Guazú"));
                break;

            default:
                Toast.makeText(activity_correcarrera.this,"La universidad recibida no coincide con ninguno", Toast.LENGTH_SHORT).show();
                break;
        }

        return listItems;
    }
}
