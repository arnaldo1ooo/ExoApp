package com.exofpune;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class activity_bonificacion extends AppCompatActivity {

    TextView etBonificacion;
    TextView tvTitulo2;
    TextView tvNota2;
    TextView tvNota3;
    TextView tvNota4;
    TextView tvNota5;
    Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonificacion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bonificación total");

        etBonificacion = (TextView) findViewById(R.id.etBonificacion);
        tvTitulo2 = (TextView) findViewById(R.id.tvTitulo2);
        tvNota2 = (TextView) findViewById(R.id.tvNota2);
        tvNota3 = (TextView) findViewById(R.id.tvNota3);
        tvNota4 = (TextView) findViewById(R.id.tvNota4);
        tvNota5 = (TextView) findViewById(R.id.tvNota5);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);

        PonerObjetosInvisibles("INVISIBLE");

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("etBonificacion ",etBonificacion.getText().toString());
                if (etBonificacion.getText().equals(null) == false){
                    Calcular();
                }else{
                    Toast.makeText(null, "La bonificación no es válida",Toast.LENGTH_LONG);
                }
            }
        });

    }


    private void PonerObjetosInvisibles(String valor) {
        if (valor == "VISIBLE"){
            tvTitulo2.setVisibility(View.VISIBLE);
            tvNota2.setVisibility(View.VISIBLE);
            tvNota3.setVisibility(View.VISIBLE);
            tvNota4.setVisibility(TextView.VISIBLE);
            tvNota5.setVisibility(TextView.VISIBLE);
        }
        if (valor == "INVISIBLE"){
            tvTitulo2.setVisibility(View.INVISIBLE);
            tvNota2.setVisibility(View.INVISIBLE);
            tvNota3.setVisibility(View.INVISIBLE);
            tvNota4.setVisibility(TextView.INVISIBLE);
            tvNota5.setVisibility(TextView.INVISIBLE);
        }
    }


    public void Calcular() {
        DecimalFormat df = new DecimalFormat("#.###");
        double TotalFinal = 60;
        double Minpara2 = 60;
        double Minpara3 = 60;
        double Minpara4 = 60;
        double Minpara5 = 60;

        PonerObjetosInvisibles("VISIBLE");
        double Bonif = Double.parseDouble(etBonificacion.getText().toString());

        double Nota2 = Minpara2 - Bonif;
        double Nota2Porc = (Nota2*100)/Minpara2;
        tvNota2.setText(df.format(Nota2) +" puntos de " + TotalFinal + " (" + df.format(Nota2Porc) + "% de 100%)");
    }
}
