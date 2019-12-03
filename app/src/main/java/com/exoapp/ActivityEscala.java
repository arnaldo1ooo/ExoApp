package com.exoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivityEscala extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Escala de calificaciones");
        setContentView(R.layout.activity_escala);
    }
}
