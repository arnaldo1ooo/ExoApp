package com.exoapp.correlatividad.correfacultad;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exoapp.R;
import com.exoapp.recyclerview.Item;
import com.exoapp.recyclerview.RecyclerViewAdaptador;

import java.util.ArrayList;

public class activity_correfacultad extends AppCompatActivity {

    private RecyclerView recyclerview;
    private RecyclerView.Adapter adaptador;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correfacultad);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Facultades");

        //Se crea el recyclerview y adaptador
        recyclerview = (RecyclerView) findViewById(R.id.rvPrincipal);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        //Adaptador
        adaptador = new RecyclerViewAdaptador(this, MetodoListItem());

        //OnClick
        ((RecyclerViewAdaptador) adaptador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo el titulo del item seleccionado
                String tituloitem = MetodoListItem().get(
                        recyclerview.getChildAdapterPosition(view)).getTitulo();

                switch (tituloitem) {
                    case "FACULTAD POLITÉCNICA":
                        //Intent i = new Intent(this, ac.class);
                        //startActivity(i);
                        break;

                    case "FACULTAD DE INGENIERÍA AGRONÓMICA":
                        //Intent i = new Intent(this, ac.class);
                        //startActivity(i);
                        break;

                    default:
                        Toast.makeText(activity_correfacultad.this,"No se seleccionó ningún item", Toast.LENGTH_SHORT).show();
                        break;
                }


                //Imprime el titulo del item seleccionado
                Toast.makeText(getApplicationContext(), "Selección: " + MetodoListItem().get(
                        recyclerview.getChildAdapterPosition(view)).getTitulo(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerview.setAdapter(adaptador);

        //Linea divisor de RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerview.addItemDecoration(dividerItemDecoration);

    }

    private ArrayList<Item> MetodoListItem(){
        ArrayList<Item> listItems = new ArrayList<>();

        String univesidadseleccionada = getIntent().getExtras().getString("universidad");
        switch (univesidadseleccionada) {
            case "UNIVERSIDAD NACIONAL DEL ESTE":
                listItems.add(new Item(R.drawable.logofacuunepolitecnica,"FACULTAD POLITÉCNICA","Ciudad: Ciudad del Este"));
                listItems.add(new Item(R.drawable.logofacuuneagronomia,"FACULTAD DE INGENIERÍA AGRONÓMICA","Ciudad: Ciudad del Este"));
                listItems.add(new Item(R.drawable.logofacuunecienciassalud,"FACULTAD DE CIENCIAS DE LA SALUD","Ciudad: Ciudad del Este"));
                break;

            case "UNIVERSIDAD PRIVADA DEL ESTE":
                listItems.add(new Item(R.drawable.logofacuupecienciasagrarias,"FACULTAD DE CIENCIAS AGRARIAS","Ciudad: Ciudad del Este"));
                break;

            default:
                Toast.makeText(activity_correfacultad.this,"La universidad recibida no coincide con ninguno", Toast.LENGTH_SHORT).show();
                break;
        }

        return listItems;
    }
}
