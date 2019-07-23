package com.exoapp.correlatividad.correuniversidad;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.exoapp.R;
import com.exoapp.correlatividad.correfacultad.activity_correfacultad;
import com.exoapp.recyclerview.Item;
import com.exoapp.recyclerview.RecyclerViewAdaptador;

import java.util.ArrayList;

public class activity_correuniversidad extends AppCompatActivity {

    private RecyclerView recyclerview;
    private RecyclerView.Adapter adaptador;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correuniversidad);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Universidades");


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

                Intent intent;
                String universidad;
                switch (tituloitem) {
                    case "UNIVERSIDAD NACIONAL DEL ESTE":
                        universidad = "UNIVERSIDAD NACIONAL DEL ESTE";
                        intent = new Intent(activity_correuniversidad.this, activity_correfacultad.class);
                        intent.putExtra("universidad", universidad);
                        startActivity(intent);
                        break;

                    case "UNIVERSIDAD PRIVADA DEL ESTE":
                        universidad = "UNIVERSIDAD PRIVADA DEL ESTE";
                        intent = new Intent(activity_correuniversidad.this, activity_correfacultad.class);
                        intent.putExtra("universidad", universidad);
                        startActivity(intent);
                        break;


                    default:
                        Toast.makeText(activity_correuniversidad.this,"No se seleccionó ningún item", Toast.LENGTH_SHORT).show();
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
        listItems.add(new Item(R.drawable.logouniune,"UNIVERSIDAD NACIONAL DEL ESTE","Tipo de institución: Nacional" +
                "\n Dirección: Ciudad del Este - km 7 Acaray" +
                "\n Tel.: (+595) 61575478" +
                "\n Correo: rectorado@une.edu.py"));
        listItems.add(new Item(R.drawable.logouniupe,"UNIVERSIDAD PRIVADA DEL ESTE","Tipo de institución: Privada" +
                "\n Dirección: Ciudad del Este - km 6 Monday" +
                "\n Tel.: (+595) 61579441" +
                "\n Correo: info@upecde.edu.py"));

        return listItems;
    }
}
