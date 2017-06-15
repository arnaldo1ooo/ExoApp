package com.exofpune;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exofpune.R;

public class activity_perso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perso);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
