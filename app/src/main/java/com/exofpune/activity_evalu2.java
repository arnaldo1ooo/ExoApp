package com.exofpune;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class activity_evalu2 extends AppCompatActivity {

    Button btnCalculo;
    EditText et1parcial;
    EditText et2parcial;
    TextView tvResultado;
    TextView tvFaltante;
    TextView tvPuntosFinal;
    int total1p = 20;
    int total2p = 20;
    double resultado;
    Spinner sp1;
    Spinner sp2;
    String[] array = {"%","Puntos"};
    TextView tvTotalText1;
    TextView tvTotalText2;
    TextView tvFeli;
    double PuntosObtenidos1 = 0;
    double PuntosObtenidos2 = 0;
    double PuntosParaExonerar = 32.4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalu2);

        try {
            Bundle extras = getIntent().getExtras();
            total1p = extras.getInt("puntototal1parcial");
            total2p = extras.getInt("puntototal2parcial");
            PuntosParaExonerar = extras.getInt("Minimo Exoneracion");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        btnCalculo = (Button) findViewById(R.id.btnCalculo);
        et1parcial = (EditText) findViewById(R.id.et1Parcial);
        et2parcial = (EditText) findViewById(R.id.et2parcial);
        tvResultado = (TextView) findViewById(R.id.tvResultado);
        sp1 = (Spinner) findViewById(R.id.sp1);
        sp2 = (Spinner) findViewById(R.id.sp2);
        tvTotalText1 = (TextView) findViewById(R.id.tvTotalText1);
        tvTotalText2 = (TextView) findViewById(R.id.tvTotalText2);
        tvFeli = (TextView) findViewById(R.id.tvFeli);
        tvFaltante = (TextView) findViewById(R.id.tvFaltante);
        tvPuntosFinal = (TextView) findViewById(R.id.tvPuntosFinal);

        //Spinner
        ArrayAdapter<String> adaptadorsp = new ArrayAdapter<String>(this, R.layout.spinner_configuracion, array);
        sp1.setAdapter(adaptadorsp);
        sp2.setAdapter(adaptadorsp);


        tvFaltante.setVisibility(View.INVISIBLE);
        tvResultado.setVisibility(View.INVISIBLE);
        tvFeli.setVisibility(View.INVISIBLE);
        tvPuntosFinal.setVisibility(TextView.INVISIBLE);




        AlCambiarValorSpinner(sp1, et1parcial, tvTotalText1,total1p, PuntosObtenidos1);
        AlCambiarValorSpinner(sp2, et2parcial, tvTotalText2,total2p, PuntosObtenidos2);
        AlModificarEditText1();
        AlModificarEditText2();

        btnCalculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat df = new DecimalFormat("#.###");
                OcultarTeclado(v);
                SiEsVacio();

                if(MensajeErrorPorcentajeOPuntos(sp1, Double.parseDouble(et1parcial.getText().toString()), total1p) == false &&
                        MensajeErrorPorcentajeOPuntos(sp2, Double.parseDouble(et2parcial.getText().toString()), total2p) == false){
                    resultado = PuntosObtenidos1 + PuntosObtenidos2;
                    tvResultado.setText(df.format(resultado) + " Puntos de 40 Puntos");
                    Felicitar();
                }
            }
        });

    }


    private void AlModificarEditText1() {
        //Al cambiar texto1
        et1parcial.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  //Antes de escritir

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  //Cuando se esta modificando un texto

            }

            @Override
            public void afterTextChanged(Editable s) { //Despues de escribir
                PuntosObtenidos1= CalculoPorcentajeOPunto (sp1, et1parcial, tvTotalText1,total1p, PuntosObtenidos1);
            }
        });
    }

    private void AlModificarEditText2() {
        //Al cambiar texto1
        et2parcial.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  //Antes de escritir

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  //Cuando se esta modificando un texto

            }

            @Override
            public void afterTextChanged(Editable s) { //Despues de escribir
                PuntosObtenidos2= CalculoPorcentajeOPunto (sp2, et2parcial, tvTotalText2,total2p, PuntosObtenidos2);
            }
        });
    }

    public double CalculoPorcentajeOPunto (Spinner ELSpinner, EditText ElEditText, TextView tvTotal, int Totalpuntos, double PuntosObtenidos){
        DecimalFormat df = new DecimalFormat("#.###");
        if (ELSpinner.getSelectedItemId() == 0) {
            Log.d("Tipo de Spinner", "Eligió %");
            if (ElEditText.getText().toString().equals("") == false && MensajeErrorPorcentajeOPuntos(ELSpinner, Double.parseDouble(ElEditText.getText().toString()), Totalpuntos) == false) {//Si es vacio
                PuntosObtenidos= (Double.parseDouble(ElEditText.getText().toString()) * Totalpuntos) / 100;
                tvTotal.setText(df.format(PuntosObtenidos) + " de " + Totalpuntos + " Puntos");
            } else {
                PuntosObtenidos = 0.0;
                tvTotal.setText("0 de " + Totalpuntos + " Puntos");
            }
        }
        else{
            Log.d("Tipo de Spinner", "Eligió Puntos");
            if (ElEditText.getText().toString().equals("") == false && MensajeErrorPorcentajeOPuntos(ELSpinner, Double.parseDouble(ElEditText.getText().toString()), Totalpuntos) == false) {
                PuntosObtenidos = Double.parseDouble(ElEditText.getText().toString());
                tvTotal.setText(df.format(PuntosObtenidos) + " de " + Totalpuntos + " Puntoss");
            }
            else {
                PuntosObtenidos = 0.0;
                tvTotal.setText("0 de " + Totalpuntos + " Puntos");
            }
        }
        return PuntosObtenidos;
    }

    //Metodo Mensaje de error
    public boolean MensajeErrorPorcentajeOPuntos(Spinner ElSpinner, double PorcOPuntos, int TotalPuntos) {
        if (ElSpinner.getSelectedItemId() == 0){ //Si es en Porcentaje
            if (PorcOPuntos > 100){
                Toast toast = Toast.makeText(this,"El porcentaje obtenido no puede ser mayor al 100%",Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
            else{
                return false;
            }
        }
        else { //Si es en Puntos
            if (PorcOPuntos > TotalPuntos){
                Toast toast = Toast.makeText(this,"El punto obtenido no puede ser mayor a "+TotalPuntos+" Puntos",Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
            else {
                return false;
            }
        }
    }

    private void AlCambiarValorSpinner(final Spinner ElSpinner, final EditText ElEditText, final TextView tvTotal, final int Totalpuntos, final Double PuntosObtenidos) {
        //Al cambiar valor de spinner1
        ElSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
                Log.d("sd", "Cambio valor de sp");
                CalculoPorcentajeOPunto (ElSpinner, ElEditText, tvTotal, Totalpuntos, PuntosObtenidos);
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(getApplicationContext(), "Ninguno seleccionado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SiEsVacio(){
        if(et1parcial.getText().toString().equals("")){//Si es vacio
            et1parcial.setText("0");
        }
        if(et2parcial.getText().toString().equals("")){//Si es vacio
            et2parcial.setText("0");
        }
    }

    public void Felicitar (){
        DecimalFormat df = new DecimalFormat("#.###");
        if(resultado >= PuntosParaExonerar){
            tvFeli.setText("EXONERASTE!! :D");
            tvFeli.setVisibility(View.VISIBLE);
            tvFaltante.setVisibility(TextView.VISIBLE);
            tvResultado.setVisibility(TextView.VISIBLE);
            tvPuntosFinal.setVisibility(TextView.INVISIBLE);
            if(resultado >= 38){
                tvFaltante.setText("Obtuviste nota 5");
            }
            else{
                tvFaltante.setText("Obtuviste nota 4");
            }
        }
        else{
            tvFeli.setText("No exoneraste :(");
            tvFaltante.setText("Te faltaron " + df.format(PuntosParaExonerar - (PuntosObtenidos1 + PuntosObtenidos2)) + " Puntos para Exonerar");
            tvFeli.setVisibility(View.VISIBLE);
            tvFaltante.setVisibility(TextView.VISIBLE);
            tvResultado.setVisibility(TextView.VISIBLE);
            tvPuntosFinal.setVisibility(TextView.VISIBLE);
            tvPuntosFinal.setText("Debes hacer " + df.format(((60 - (PuntosObtenidos1 + PuntosObtenidos2))*100)/60) + "% en la final (" + df.format(60 - (PuntosObtenidos1 + PuntosObtenidos2)) + " de 60)");
        }
    }

    public void OcultarTeclado (View v){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }







    public void setTotal1p(int total1p) {
        this.total1p = total1p;
    }
    public int getTotal1p() {
        return total1p;
    }
}
