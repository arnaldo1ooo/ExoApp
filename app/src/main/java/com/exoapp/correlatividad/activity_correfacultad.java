package com.exoapp.correlatividad;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.exoapp.R;

public class activity_correfacultad extends AppCompatActivity {

    Button btnFacu1;
    Button btnFacu2;
    Button btnFacu3;
    Button btnFacu4;
    Button btnFacu5;
    Button btnFacu6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correfacultad);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Facultades");

        btnFacu1 = (Button) findViewById(R.id.btnFacu1);
        btnFacu2 = (Button) findViewById(R.id.btnFacu2);
        btnFacu3 = (Button) findViewById(R.id.btnFacu3);
        btnFacu4 = (Button) findViewById(R.id.btnFacu4);
        btnFacu5 = (Button) findViewById(R.id.btnFacu5);
        btnFacu6 = (Button) findViewById(R.id.btnFacu6);

        VisibilidadObjetos();


        btnFacu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_correcarrera.class);
                intent.putExtra("FacultadSelect", btnFacu1.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

        btnFacu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_correcarrera.class);
                intent.putExtra("FacultadSelect", btnFacu2.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

        btnFacu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_correcarrera.class);
                intent.putExtra("FacultadSelect", btnFacu3.getText().toString());
                startActivityForResult(intent, 0);
            }
        });



        String UniversidadSelect = getIntent().getExtras().getString("UniversidadSelect");
        Log.d("Universidad Select: ",UniversidadSelect);

        if (UniversidadSelect.equals("Universidad Nacional del Este") == true){
            btnFacu1.setText("Facultad Politécnica");
            btnFacu1.setVisibility(View.VISIBLE);
        }
    }


    private void VisibilidadObjetos (){
        btnFacu1.setVisibility(View.INVISIBLE);
        btnFacu2.setVisibility(View.INVISIBLE);
        btnFacu3.setVisibility(View.INVISIBLE);
        btnFacu4.setVisibility(View.INVISIBLE);
        btnFacu5.setVisibility(View.INVISIBLE);
        btnFacu6.setVisibility(View.INVISIBLE);
    }

}
