package com.exoapp.correlatividad.correfacultad;

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
import com.exoapp.correlatividad.correcarrera.activity_correcarrera;
import com.exoapp.recyclerview.lista.itemLista;
import com.exoapp.recyclerview.lista.rvLista;

import java.util.ArrayList;

public class activity_correfacultad extends AppCompatActivity {

    private RecyclerView recyclerview;
    private RecyclerView.Adapter adaptador;
    private LinearLayoutManager layoutManager;
    private TextView txtTitulo;
    private String universidadseleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correfacultad);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Seleccione la facultad");

        //Ponemos el titulo
        universidadseleccionada = getIntent().getExtras().getString("universidad");


        txtTitulo = (TextView) findViewById(R.id.tvTitulo);
        txtTitulo.setText("Facultades de la\n" + universidadseleccionada);


        //Se crea el recyclerview y adaptador
        recyclerview = (RecyclerView) findViewById(R.id.rvPrincipal);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        //Adaptador
        adaptador = new rvLista(this, MetodoListItem());

        //OnClick
        ((rvLista) adaptador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo el titulo del item seleccionado
                String tituloitem = MetodoListItem().get(
                        recyclerview.getChildAdapterPosition(view)).getTitulo();
                String facultad = "";
                Intent intent;

                switch (tituloitem) {
                    //UNIVERSIDAD UNE
                    case "FACULTAD POLITÉCNICA":
                        facultad = tituloitem;
                        break;

                    case "FACULTAD DE INGENIERÍA AGRONÓMICA":
                        facultad = tituloitem;
                        break;


                    default:
                        Toast.makeText(activity_correfacultad.this,"No se seleccionó ningún item", Toast.LENGTH_SHORT).show();
                        break;
                }

                intent = new Intent(activity_correfacultad.this, activity_correcarrera.class);
                intent.putExtra("universidad", universidadseleccionada);
                intent.putExtra("facultad", facultad);
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

    private ArrayList<itemLista> MetodoListItem(){
        ArrayList<itemLista> listItems = new ArrayList<>();

        switch (universidadseleccionada) {
            case "UNIVERSIDAD NACIONAL DEL ESTE":
                listItems.add(new itemLista(R.drawable.facuunepolitecnica,"FACULTAD POLITÉCNICA","Ciudad: Ciudad del Este"));
                listItems.add(new itemLista(R.drawable.facuuneagronomia,"FACULTAD DE INGENIERÍA AGRONÓMICA","Ciudad: Ciudad del Este"));
                listItems.add(new itemLista(R.drawable.facuunecienciassalud,"FACULTAD DE CIENCIAS DE LA SALUD","Ciudad: Ciudad del Este"));
                break;

            case "UNIVERSIDAD PRIVADA DEL ESTE":
                listItems.add(new itemLista(R.drawable.facuupecienciasagrarias,"FACULTAD DE CIENCIAS AGRARIAS","Ciudad: Ciudad del Este"));
                break;

            default:
                Toast.makeText(activity_correfacultad.this,"La universidad recibida no coincide con ninguno", Toast.LENGTH_SHORT).show();
                break;
        }

        return listItems;
    }
}
