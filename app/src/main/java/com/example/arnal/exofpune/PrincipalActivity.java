package com.example.arnal.exofpune;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class PrincipalActivity extends AppCompatActivity {

    Button btnCalculo;
    EditText et1parcial;
    EditText et2parcial;
    EditText etTp;
    TextView tvResultado;
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
    TextView totaltext1;
    TextView totaltext2;
    TextView totaltext3;
    TextView tvFeli;
    double puntos = 0;
    double puntos2 = 0;
    double puntos3 = 0;
    DecimalFormat df = new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnCalculo = (Button) findViewById(R.id.btnCalculo);
        et1parcial = (EditText) findViewById(R.id.et1parcial);
        et2parcial = (EditText) findViewById(R.id.et2parcial);
        etTp = (EditText) findViewById(R.id.etTp);
        tvResultado = (TextView) findViewById(R.id.tvResultado);
        sp1 = (Spinner) findViewById(R.id.sp1);
        sp2 = (Spinner) findViewById(R.id.sp2);
        sp3 = (Spinner) findViewById(R.id.sp3);
        totaltext1 = (TextView) findViewById(R.id.tvTotalText1);
        totaltext2 = (TextView) findViewById(R.id.tvTotalText2);
        totaltext3 = (TextView) findViewById(R.id.tvTotalText3);
        tvFeli = (TextView) findViewById(R.id.tvFeli);


        //Spinner
        ArrayAdapter<String> adaptadorsp = new ArrayAdapter<String>(this, R.layout.spinner_configuracion, array);
        sp1.setAdapter(adaptadorsp);
        sp2.setAdapter(adaptadorsp);
        sp3.setAdapter(adaptadorsp);


        //TotalText
        totaltext1.setText(df.format(puntos) + " de " +total1p+ " Puntos");
        totaltext2.setText(df.format(puntos2) + " de " +total2p+ " Puntos");
        totaltext3.setText(df.format(puntos3) + " de " +totaltp+ " Puntos");


//Al cambiar texto
        et1parcial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  //Antes de escritir

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  //cuando se modifica un texto

            }

            @Override
            public void afterTextChanged(Editable s) { //Despeus de escribir
                if(s.length() >= 1) {//Si es vacio
                    puntos = (Double.parseDouble(et1parcial.getText().toString()) * total1p) / 100;
                    totaltext1.setText(df.format(puntos) + " de " +total1p+ " Puntos");
                }
            }
        });



        btnCalculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTeclado(v);
                SiEsVacio(v);

                if (sp1.getSelectedItemId() == 0){
                    if (Double.parseDouble(et1parcial.getText().toString())>100){
                        MensajeErrorPorcentaje(v);
                    }
                    else{
                        if(MensajeErrorPorcentaje(v)==false){
                            p1 = (Double.parseDouble(et1parcial.getText().toString()) * total1p) / 100;
                        }
                    }
                }
                else{
                    if (Double.parseDouble(et1parcial.getText().toString())>total1p){
                        MensajeErrorPuntos(v, (int)total1p);
                    }
                    else{
                            p1 = Double.parseDouble(et1parcial.getText().toString());
                    }
                }


                if (sp2.getSelectedItemId() == 0){
                    if (Double.parseDouble(et2parcial.getText().toString())>100) {
                        MensajeErrorPorcentaje(v);
                    }
                    else{
                        if(MensajeErrorPorcentaje(v)==false){
                            p2 = (Double.parseDouble(et2parcial.getText().toString()) * total2p) / 100;
                        }
                    }
                }
                else{
                    if (Double.parseDouble(et2parcial.getText().toString())>total2p){
                        MensajeErrorPuntos(v, (int)total2p);
                    }
                    else{
                        p2 = Double.parseDouble(et2parcial.getText().toString());
                    }
                }


                if (sp3.getSelectedItemId() == 0){
                    if (Double.parseDouble(etTp.getText().toString())>100) {
                        MensajeErrorPorcentaje(v);
                    }
                    else{
                        tp = (Double.parseDouble(etTp.getText().toString()) * totaltp) / 100;
                    }
                }
                else{
                    if (Double.parseDouble(etTp.getText().toString())>totaltp){
                        MensajeErrorPuntos(v, (int)totaltp);
                    }
                    else{
                        tp = Double.parseDouble(etTp.getText().toString());
                    }
                }

                resultado = p1+p2+tp;
                tvResultado.setText(df.format(resultado)+" Puntos de 40 Puntos");
                Felicitar(v);

            }
        });

    }






//Metodo Mensaje de error
    public boolean MensajeErrorPorcentaje(View v) {
        Toast toast = Toast.makeText(this,"El porcentaje obtenido no puede ser mayor al 100%",Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    public boolean MensajeErrorPuntos(View v, int TotalPuntos) {
        Toast toast = Toast.makeText(this,"El punto obtenido no puede ser mayor a "+TotalPuntos+" Puntos",Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    public void SiEsVacio(View v){
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

    public void Felicitar (View v){
        if(resultado >= 32.4){
            tvFeli.setText("EXONERASTE!! :D");
        }
        else{
            tvFeli.setText("No exoneraste :(");
        }
    }


    public void OcultarTeclado (View v){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
