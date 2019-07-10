package com.exoapp.correlatividad.correuniversidad;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.exoapp.R;
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

        adaptador = new RecyclerViewAdaptador(this, MetodoListItem());
        recyclerview.setAdapter(adaptador);

        //Linea divisor de RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerview.addItemDecoration(dividerItemDecoration);

    }

    private ArrayList<Item> MetodoListItem(){
        ArrayList<Item> listItems = new ArrayList<>();
        listItems.add(new Item(R.drawable.logouniune,"UNIVERSIDAD NACIONAL DEL ESTE","Ciudad: Ciudad del Este"));
        listItems.add(new Item(R.drawable.logouniupe,"UNIVERSIDAD PRIVADA DEL ESTE","Ciudad: Ciudad del Este"));

        return listItems;
    }
}
