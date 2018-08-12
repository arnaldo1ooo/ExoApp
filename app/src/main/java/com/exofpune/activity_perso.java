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
import android.widget.Toast;

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
    TextView etNota5;
    int SumaTotal;

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
        etNota5 = (TextView) findViewById(R.id.etNota5);


        //Llamada de metodos
        ComprobarBotonTp();


        tbTp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComprobarBotonTp();
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (tbTp.getText().toString().equals("NO")) {
                        SumaTotal = Integer.parseInt(et1parcial.getText().toString()) + Integer.parseInt(et2parcial.getText().toString());
                        et3.setText("0");
                    } else {
                        SumaTotal = Integer.parseInt(et1parcial.getText().toString()) + Integer.parseInt(et2parcial.getText().toString()) + Integer.parseInt(et3.getText().toString());
                    }
                    Log.d("Suma Total", "" + SumaTotal);
                    if(MensajesError() == true){
                        return;
                    }

                    Intent intent = new Intent(activity_perso.this, activity_evalu.class);
                    intent.putExtra("puntototal1parcial", Integer.parseInt(et1parcial.getText().toString()));
                    intent.putExtra("puntototal2parcial", Integer.parseInt(et2parcial.getText().toString()));
                    intent.putExtra("puntototaltp", Integer.parseInt(et3.getText().toString()));
                    intent.putExtra("puntotonota5", Double.parseDouble(etNota5.getText().toString()));
                    intent.putExtra("Minimo Exoneracion", Double.parseDouble(etExoMin.getText().toString()));
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(activity_perso.this, "Ningun casillero puede quedar vacio", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });
    }


    private boolean MensajesError() {
        if (Double.parseDouble(etNota5.getText().toString()) > SumaTotal) {
            Toast.makeText(activity_perso.this, "El puntaje para obtener 5 no puede ser mayor al total de los puntajes", Toast.LENGTH_LONG).show();
            return true;
        }

        if (Double.parseDouble(etNota5.getText().toString()) < Double.parseDouble(etExoMin.getText().toString())) {
            Toast.makeText(activity_perso.this, "El puntaje para obtener 5 no puede ser menor al puntaje minimo para exonerar", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }


    private void ComprobarBotonTp() {
        if (tbTp.getText().toString().equals("NO")) {
            Log.d("tbTp Click", "TP Desactivado");
            et3.setEnabled(false);
            tp3.setTextColor(Color.DKGRAY);
            puntos3.setTextColor(Color.DKGRAY);
            et3.setTextColor(Color.DKGRAY);

        } else {
            Log.d("tbTp Click", "TP Activado");
            et3.setEnabled(true);
            tp3.setTextColor(Color.WHITE);
            puntos3.setTextColor(Color.WHITE);
            et3.setTextColor(Color.WHITE);
        }
    }
}
