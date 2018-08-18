package com.exofpune;

import android.graphics.Color;
import android.support.v4.content.ContentResolverCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class activity_intro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        addSlide(AppIntroFragment.newInstance("Primer slide", "Descripcion", R.drawable.img1, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
}
}

