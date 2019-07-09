package com.exoapp.correlatividad.correuniversidad;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
    private RecyclerView.LayoutManager layoutmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correuniversidad);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Universidades");


        //Se crea el recyclerview y adaptador
        recyclerview = (RecyclerView) findViewById(R.id.rvPrincipal);
        layoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutmanager);

        adaptador = new RecyclerViewAdaptador(this, MetodoListItem());
        recyclerview.setAdapter(adaptador);
    }

    private ArrayList<Item> MetodoListItem(){
        ArrayList<Item> listItems = new ArrayList<>();
        listItems.add(new Item(R.drawable.boton_borde_redondo,"UNIVERSIDAD NACIONAL DEL ESTE","Hola mundo como estas"));
        listItems.add(new Item(R.drawable.boton_borde_redondo,"UNIVERSIDAD PRIVADA DEL ESTE","Hola mundo como estas????"));
        listItems.add(new Item(R.drawable.boton_borde_redondo,"UNIVERSIDAD CATOLICA","Hola mundo como estasasdasdas"));
        listItems.add(new Item(R.drawable.boton_borde_redondo,"UNIVERSIDAD CATOLICA","Hola mundo como estasasdasdas"));
        listItems.add(new Item(R.drawable.boton_borde_redondo,"UNIVERSIDAD CATOLICA","Hola mundo como estasasdasdas"));
        listItems.add(new Item(R.drawable.boton_borde_redondo,"UNIVERSIDAD CATOLICA","Hola mundo como estasasdasdas"));
        listItems.add(new Item(R.drawable.boton_borde_redondo,"UNIVERSIDAD CATOLICA","Hola mundo como estasasdasdas"));
        listItems.add(new Item(R.drawable.boton_borde_redondo,"UNIVERSIDAD CATOLICA","Hola mundo como estasasdasdas"));
        listItems.add(new Item(R.drawable.boton_borde_redondo,"UNIVERSIDAD CATOLICA","Hola mundo como estasasdasdas"));
        listItems.add(new Item(R.drawable.boton_borde_redondo,"UNIVERSIDAD CATOLICA","Hola mundo como estasasdasdas"));

        return listItems;
    }
}
