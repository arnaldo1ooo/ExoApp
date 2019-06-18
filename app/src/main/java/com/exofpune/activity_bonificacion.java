package com.exofpune;

import android.graphics.Color;
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
    TextView tvTituloNota2;
    TextView tvNota2;
    TextView tvTituloNota3;
    TextView tvNota3;
    TextView tvTituloNota4;
    TextView tvNota4;
    TextView tvTituloNota5;
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
        tvTituloNota2 = (TextView) findViewById(R.id.tvTituloNota2);
        tvNota2 = (TextView) findViewById(R.id.tvNota2);
        tvTituloNota3 = (TextView) findViewById(R.id.tvTituloNota3);
        tvNota3 = (TextView) findViewById(R.id.tvNota3);
        tvTituloNota4 = (TextView) findViewById(R.id.tvTituloNota4);
        tvNota4 = (TextView) findViewById(R.id.tvNota4);
        tvTituloNota5 = (TextView) findViewById(R.id.tvTituloNota5);
        tvNota5 = (TextView) findViewById(R.id.tvNota5);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);

        VisibilidadObjetos("INVISIBLE");

        try {
            String BonificacionRecibido = getIntent().getExtras().getString("bonificacion");
            Log.d("Boni", "La bonificacion recibida es: " + BonificacionRecibido + "");
            etBonificacion.setText(BonificacionRecibido);
        } catch (Exception e) {
            Log.d("Error trycatch ", "No se recibio ninguna bonificacion " + e);
        }


        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calcular();
            }
        });

    }


    private void VisibilidadObjetos(String valor) {
        if (valor == "VISIBLE") {
            tvTitulo2.setVisibility(View.VISIBLE);
            tvTituloNota2.setVisibility(View.VISIBLE);
            tvNota2.setVisibility(View.VISIBLE);
            tvTituloNota3.setVisibility(View.VISIBLE);
            tvNota3.setVisibility(View.VISIBLE);
            tvTituloNota4.setVisibility(View.VISIBLE);
            tvNota4.setVisibility(TextView.VISIBLE);
            tvTituloNota5.setVisibility(View.VISIBLE);
            tvNota5.setVisibility(TextView.VISIBLE);
        }
        if (valor == "INVISIBLE") {
            tvTitulo2.setVisibility(View.INVISIBLE);
            tvTituloNota2.setVisibility(View.INVISIBLE);
            tvNota2.setVisibility(View.INVISIBLE);
            tvTituloNota3.setVisibility(View.INVISIBLE);
            tvNota3.setVisibility(View.INVISIBLE);
            tvTituloNota4.setVisibility(View.INVISIBLE);
            tvNota4.setVisibility(TextView.INVISIBLE);
            tvTituloNota5.setVisibility(View.INVISIBLE);
            tvNota5.setVisibility(TextView.INVISIBLE);
        }
    }


    public void Calcular() {
        if (etBonificacion.getText().toString().isEmpty() == true) { //Si bonificacion es vacia
            Toast.makeText(this, "La bonificación es inválida, no puede ser vacío", Toast.LENGTH_LONG).show();
        } else {
            DecimalFormat df = new DecimalFormat("#.###");
            DecimalFormat soloentero = new DecimalFormat("#");
            double bonmax = 40;
            double TotalFinal = 60;
            double bonificacion = Double.parseDouble(etBonificacion.getText().toString());

            if (bonificacion > bonmax || bonificacion < 0) { //Si bonificacion es mayor a bonificacion maxima
                Toast.makeText(this, "La bonificación no puede ser mayor a " + soloentero.format(bonmax) + " Puntos", Toast.LENGTH_LONG).show();
            } else {
                VisibilidadObjetos("VISIBLE");

                double Minpara2 = 60;
                double Minpara3 = 70;
                double Minpara4 = 81;
                double Minpara5 = 94;
                double Bonif = Double.parseDouble(etBonificacion.getText().toString());


                double Nota2 = Minpara2 - Bonif;
                if (Nota2 > TotalFinal) {
                    tvNota2.setTextColor(Color.RED);
                }
                double Nota2Porc = (Nota2 * 100) / TotalFinal;
                tvNota2.setText(df.format(Nota2) + " puntos de " + soloentero.format(TotalFinal) + " (" + df.format(Nota2Porc) + "% de 100%)");

                double Nota3 = Minpara3 - Bonif;
                if (Nota3 > TotalFinal) {
                    tvNota3.setTextColor(Color.RED);
                }
                double Nota3Porc = (Nota3 * 100) / TotalFinal;
                tvNota3.setText(df.format(Nota3) + " puntos de " + soloentero.format(TotalFinal) + " (" + df.format(Nota3Porc) + "% de 100%)");

                double Nota4 = Minpara4 - Bonif;
                if (Nota4 > TotalFinal) {
                    tvNota4.setTextColor(Color.RED);
                }
                double Nota4Porc = (Nota4 * 100) / TotalFinal;
                tvNota4.setText(df.format(Nota4) + " puntos de " + soloentero.format(TotalFinal) + " (" + df.format(Nota4Porc) + "% de 100%)");

                double Nota5 = Minpara5 - Bonif;
                if (Nota5 > TotalFinal) {
                    tvNota5.setTextColor(Color.RED);
                }
                double Nota5Porc = (Nota5 * 100) / TotalFinal;
                tvNota5.setText(df.format(Nota5) + " puntos de " + soloentero.format(TotalFinal) + " (" + df.format(Nota5Porc) + "% de 100%)");
            }
        }
    }
}
