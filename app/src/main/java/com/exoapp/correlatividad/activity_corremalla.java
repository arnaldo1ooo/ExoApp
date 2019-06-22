package com.exofpune;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class activity_corremalla extends AppCompatActivity {

    Button btnMalla1;
    Button btnMalla2;
    Button btnMalla3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corremalla);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mallas");

        btnMalla1 = (Button) findViewById(R.id.btnCarrera1);
        btnMalla2 = (Button) findViewById(R.id.btnCarrera2);
        btnMalla3 = (Button) findViewById(R.id.btnCarrera4);

        VisibilidadObjetos();

        btnMalla1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_corremalla.class);
                intent.putExtra("MallaSelect", btnMalla1.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

        btnMalla2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_corremalla.class);
                intent.putExtra("MallaSelect", btnMalla2.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

        btnMalla3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_corremalla.class);
                intent.putExtra("MallaSelect", btnMalla3.getText().toString());
                startActivityForResult(intent, 0);
            }
        });


        String CarreraSelect = getIntent().getExtras().getString("CarreraSelect");
        Log.d("Carrera Select: ",CarreraSelect);

        if (CarreraSelect.equals("Turismo") == true) {
            btnMalla1.setText("Plan 2017");
            btnMalla1.setVisibility(View.VISIBLE);
        }
    }


    private void VisibilidadObjetos() {
        btnMalla1.setVisibility(View.INVISIBLE);
        btnMalla2.setVisibility(View.INVISIBLE);
        btnMalla3.setVisibility(View.INVISIBLE);
    }

}
