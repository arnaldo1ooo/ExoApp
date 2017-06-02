package com.example.arnal.exofpune;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class activity_tipo_evalu extends AppCompatActivity {
    Button btnopc1;
    Button btnopc2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_evalu);

        btnopc1 = (Button) findViewById(R.id.btnopc1);
        btnopc2 = (Button) findViewById(R.id.btnopc2);


        btnopc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date FechaActual = new Date();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Date FechaDate = null;
                try {
                    FechaDate = formato.parse("15/06/2017");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (FechaActual.before(FechaDate)){
                    Intent intent = new Intent (v.getContext(), activity_evalu1.class);
                    startActivityForResult(intent, 0);
                }
                else{
                    Log.d("myTag", "Ya caducó");
                }

            }
        });

        btnopc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
