package com.exofpune;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class activity_correcarrera extends AppCompatActivity {

    Button btnCarrera1;
    Button btnCarrera2;
    Button btnCarrera3;
    Button btnCarrera4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correcarrera);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Carreras");

        btnCarrera1 = (Button) findViewById(R.id.btnCarrera1);
        btnCarrera2 = (Button) findViewById(R.id.btnCarrera2);
        btnCarrera3 = (Button) findViewById(R.id.btnCarrera3);
        btnCarrera4 = (Button) findViewById(R.id.btnCarrera4);

        VisibilidadObjetos();


        btnCarrera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_corremalla.class);
                intent.putExtra("CarreraSelect", btnCarrera1.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

        btnCarrera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_corremalla.class);
                intent.putExtra("CarreraSelect", btnCarrera2.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

        btnCarrera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_corremalla.class);
                intent.putExtra("CarreraSelect", btnCarrera3.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

        btnCarrera4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_corremalla.class);
                intent.putExtra("CarreraSelect", btnCarrera4.getText().toString());
                startActivityForResult(intent, 0);
            }
        });


        String FacultadSelect = getIntent().getExtras().getString("FacultadSelect");
        Log.d("Facultad Select: ",FacultadSelect);

        if (FacultadSelect.equals("Facultad Politécnica") == true){
            btnCarrera1.setText("Turismo");
            btnCarrera1.setVisibility(View.VISIBLE);
        }
    }

    private void VisibilidadObjetos (){
        btnCarrera1.setVisibility(View.INVISIBLE);
        btnCarrera2.setVisibility(View.INVISIBLE);
        btnCarrera3.setVisibility(View.INVISIBLE);
        btnCarrera4.setVisibility(View.INVISIBLE);
    }

}
