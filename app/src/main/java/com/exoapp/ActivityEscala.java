package com.exoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivityEscala extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Escala");
        setContentView(R.layout.activity_escala);
    }
}
