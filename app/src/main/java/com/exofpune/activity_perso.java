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

import com.exofpune.R;

public class activity_perso extends AppCompatActivity {


    Button tbTp;
    Button btnSiguiente;
    TextView et1;
    TextView et2;
    EditText et3;
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
        et1 = (TextView) findViewById(R.id.et1);
        et2 = (TextView) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        tp3 = (TextView) findViewById(R.id.tp3);
        puntos3 = (TextView) findViewById(R.id. puntos3);
        total3 = (TextView) findViewById(R.id. total3);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), activity_perso2.class);
                startActivityForResult(intent, 0);
            }
        });


        tbTp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbTp.getText().toString().equals("NO")){
                    et3.setEnabled(false);
                    tp3.setTextColor(Color.DKGRAY);
                    puntos3.setTextColor(Color.DKGRAY);
                    et3.setTextColor(Color.DKGRAY);
                    Log.d("myTag", "Desactivado");
                }
                else{
                    et3.setEnabled(true);
                    tp3.setTextColor(Color.WHITE);
                    puntos3.setTextColor(Color.WHITE);
                    et3.setTextColor(Color.WHITE);
                    Log.d("myTag", "Activado");
                }
            }
        });
    }
}
