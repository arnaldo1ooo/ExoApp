package com.example.arnal.exofpune;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class activity_evalu1 extends AppCompatActivity {

    Button btnCalculo;
    EditText et1parcial;
    EditText et2parcial;
    EditText etTp;
    TextView tvResultado;
    TextView tvFaltante;
    int total1p = 15;
    int total2p = 15;
    int totaltp = 10;
    double resultado;
    double p1;
    double p2;
    double tp;
    Spinner sp1;
    Spinner sp2;
    Spinner sp3;
    String[] array = {"%","Puntos"};
    TextView tvTotalText1;
    TextView tvTotalText2;
    TextView tvTotalText3;
    TextView tvFeli;
    double PuntosObtenidos = 0;
    double PuntosObtenidos2 = 0;
    double PuntosObtenidos3 = 0;
    DecimalFormat df = new DecimalFormat("#.###");
    double PuntosParaExonerar = 32.4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalu1);

        btnCalculo = (Button) findViewById(R.id.btnCalculo);
        et1parcial = (EditText) findViewById(R.id.et1parcial);
        et2parcial = (EditText) findViewById(R.id.et2parcial);
        etTp = (EditText) findViewById(R.id.etTp);
        tvResultado = (TextView) findViewById(R.id.tvResultado);
        sp1 = (Spinner) findViewById(R.id.sp1);
        sp2 = (Spinner) findViewById(R.id.sp2);
        sp3 = (Spinner) findViewById(R.id.sp3);
        tvTotalText1 = (TextView) findViewById(R.id.tvTotalText1);
        tvTotalText2 = (TextView) findViewById(R.id.tvTotalText2);
        tvTotalText3 = (TextView) findViewById(R.id.tvTotalText3);
        tvFeli = (TextView) findViewById(R.id.tvFeli);
        tvFaltante = (TextView) findViewById(R.id.tvFaltante);


        //Spinner
        ArrayAdapter<String> adaptadorsp = new ArrayAdapter<String>(this, R.layout.spinner_configuracion, array);
        sp1.setAdapter(adaptadorsp);
        sp2.setAdapter(adaptadorsp);
        sp3.setAdapter(adaptadorsp);



        //Al cambiar valor de spinner1
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(getApplicationContext(), "Ninguno seleccionado", Toast.LENGTH_SHORT).show();
            }
        });



        PorcAPuntos1();
        PorcAPuntos2();
        PorcAPuntos3();


        btnCalculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTeclado(v);
                SiEsVacio();

                    if(MensajeErrorPorcentajeOPuntos(Double.parseDouble(et1parcial.getText().toString()), total1p) == false &&
                            MensajeErrorPorcentajeOPuntos(Double.parseDouble(et2parcial.getText().toString()), total2p) == false &&
                            MensajeErrorPorcentajeOPuntos(Double.parseDouble(etTp.getText().toString()), totaltp) == false){
                        resultado = PuntosObtenidos + PuntosObtenidos2 + PuntosObtenidos3;
                        tvResultado.setText(df.format(resultado) + " Puntos de 40 Puntos");
                        Felicitar();
                    }
            }
        });

    }


    private void PorcAPuntos3() {
        //Al cambiar texto3
        etTp.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  //Antes de escritir

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  //Cuando se esta modificando un texto

            }


            @Override
            public void afterTextChanged(Editable s) { //Despues de escribir
                if(s.length() >= 1 && MensajeErrorPorcentajeOPuntos(Double.parseDouble(etTp.getText().toString()), totaltp)== false) {//Si es vacio
                    PuntosObtenidos3 = (Double.parseDouble(etTp.getText().toString()) * totaltp) / 100;
                    tvTotalText3.setText(df.format(PuntosObtenidos3) + " de " +totaltp+ " Puntos");
                }
                else{
                    PuntosObtenidos3 = 0;
                    tvTotalText3.setText("0 de " +totaltp+ " Puntos");
                }
            }
        });
    }

    private void PorcAPuntos2() {
        //Al cambiar texto2
        et2parcial.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  //Antes de escritir

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  //Cuando se esta modificando un texto

            }


            @Override
            public void afterTextChanged(Editable s) { //Despues de escribir
                if(s.length() >= 1 && MensajeErrorPorcentajeOPuntos(Double.parseDouble(et2parcial.getText().toString()), total2p)== false) {//Si es vacio
                    PuntosObtenidos2 = (Double.parseDouble(et2parcial.getText().toString()) * total2p) / 100;
                    tvTotalText2.setText(df.format(PuntosObtenidos2) + " de " +total2p+ " Puntos");
                }
                else{
                    PuntosObtenidos2 = 0;
                    tvTotalText2.setText("0 de " +total2p+ " Puntos");
                }
            }
        });
    }

    private void PorcAPuntos1() {
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
                if (sp1.getSelectedItemId() == 0) {
                    if (s.length() >= 1 && MensajeErrorPorcentajeOPuntos(Double.parseDouble(et1parcial.getText().toString()), total1p) == false) {//Si es vacio
                        PuntosObtenidos = (Double.parseDouble(et1parcial.getText().toString()) * total1p) / 100;
                        tvTotalText1.setText(df.format(PuntosObtenidos) + " de " + total1p + " Puntos");
                    } else {
                        PuntosObtenidos = 0;
                        tvTotalText1.setText("0 de " + total1p + " Puntos");
                    }
                }
                else{

                    if (s.length() >= 1 && MensajeErrorPorcentajeOPuntos(Double.parseDouble(et1parcial.getText().toString()), total1p) == false) {
                        PuntosObtenidos = Double.parseDouble(et1parcial.getText().toString());
                        tvTotalText1.setText(df.format(PuntosObtenidos) + " de " + total1p + " Puntoss");
                    }
                    else {
                        PuntosObtenidos = 0;
                        tvTotalText1.setText("0 de " + total1p + " Puntos");
                    }
                }
            }
        });
    }


    //Metodo Mensaje de error
    public boolean MensajeErrorPorcentajeOPuntos(double PorcOPuntos, int TotalPuntos) {
        if (sp1.getSelectedItemId() == 0){ //Si es en Porcentaje
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


    public void SiEsVacio(){
        if(et1parcial.getText().toString().equals("")){//Si es vacio
            et1parcial.setText("0");
        }
        if(et2parcial.getText().toString().equals("")){//Si es vacio
            et2parcial.setText("0");
        }
        if(etTp.getText().toString().equals("")){//Si es vacio
            etTp.setText("0");
        }
    }

    public void Felicitar (){
        if(resultado >= 32.4){
            tvFeli.setText("EXONERASTE!! :D");
        }
        else{
            tvFeli.setText("No exoneraste :(");
            tvFaltante.setVisibility(TextView.VISIBLE);
            tvFaltante.setText("Te faltaron " + df.format(PuntosParaExonerar - (PuntosObtenidos + PuntosObtenidos2 + PuntosObtenidos3)) + " Puntos para Exonerar");
        }
    }


    public void OcultarTeclado (View v){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
