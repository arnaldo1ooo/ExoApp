package com.exofpune;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class activity_perso extends AppCompatActivity {
    Button tbTp;
    Button btnSiguiente;
    TextView et1parcial;
    TextView et2parcial;
    EditText et3;
    EditText etExoMin;
    TextView tp3;
    TextView puntos3;
    TextView total3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perso);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tbTp = (Button) findViewById(R.id.tbTp);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        et1parcial = (TextView) findViewById(R.id.et1Parcial);
        et2parcial = (TextView) findViewById(R.id.et2parcial);
        et3 = (EditText) findViewById(R.id.et3);
        etExoMin = (EditText) findViewById(R.id.etExoMin);
        tp3 = (TextView) findViewById(R.id.tp3);
        puntos3 = (TextView) findViewById(R.id.puntos3);
        total3 = (TextView) findViewById(R.id.total3);


        tbTp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbTp.getText().toString().equals("NO")) {
                    et3.setEnabled(false);
                    tp3.setTextColor(Color.DKGRAY);
                    puntos3.setTextColor(Color.DKGRAY);
                    et3.setTextColor(Color.DKGRAY);
                    Log.d("myTag", "Desactivado");
                } else {
                    et3.setEnabled(true);
                    tp3.setTextColor(Color.WHITE);
                    puntos3.setTextColor(Color.WHITE);
                    et3.setTextColor(Color.WHITE);
                    Log.d("myTag", "Activado");
                }
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbTp.getText().toString().equals("NO")) {
                    Intent intent = new Intent (activity_perso.this, activity_evalu2.class);
                    intent.putExtra("puntototal1parcial", Integer.parseInt(et1parcial.getText().toString()));
                    intent.putExtra("puntototal2parcial", Integer.parseInt(et2parcial.getText().toString()));
                    intent.putExtra("Minimo Exoneracion", Integer.parseInt(etExoMin.getText().toString()));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(v.getContext(), activity_evalu1.class);
                    startActivityForResult(intent, 0);
                }
            }
        });
    }
}
