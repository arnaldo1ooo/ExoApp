package com.exofpune;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class activity_correuniversidad extends AppCompatActivity {

    Button btnUni1;
    Button btnUni2;
    Button btnUni3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correuniversidad);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Universidades");

        btnUni1 = (Button) findViewById(R.id.btnUni1);
        btnUni2 = (Button) findViewById(R.id.btnUni2);
        btnUni3 = (Button) findViewById(R.id.btnUni3);

        btnUni1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_correfacultad.class);
                intent.putExtra("UniversidadSelect", btnUni1.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

        btnUni2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_correfacultad.class);
                intent.putExtra("UniversidadSelect", btnUni2.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

        btnUni3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_correfacultad.class);
                intent.putExtra("UniversidadSelect", btnUni3.getText().toString());
                startActivityForResult(intent, 0);
            }
        });
    }


}
