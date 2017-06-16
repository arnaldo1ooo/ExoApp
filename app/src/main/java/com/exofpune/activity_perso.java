package com.exofpune;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exofpune.R;

public class activity_perso extends AppCompatActivity {


    Button tbTp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perso);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        tbTp = (Button) findViewById(R.id.tbTp);






        tbTp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
