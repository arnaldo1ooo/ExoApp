package com.exofpune;

import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.exofpune.R;

public class activity_acercade extends AppCompatActivity {

    TextView version;
    String strVersion;
    PackageInfo packageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        version = (TextView) findViewById(R.id.tvVersion);



        version.setText("");
    }
}
