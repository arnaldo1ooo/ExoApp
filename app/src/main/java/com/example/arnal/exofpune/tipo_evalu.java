package com.example.arnal.exofpune;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tipo_evalu extends AppCompatActivity {
    Button btnopc1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_evalu);

        btnopc1 = (Button) findViewById(R.id.btnopc1);


        btnopc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), PrincipalActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
