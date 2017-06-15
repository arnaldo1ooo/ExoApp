package com.exofpune;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
        getSupportActionBar().setTitle("Acerca de");

        version = (TextView) findViewById(R.id.tvVersion);

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version.setText(String.valueOf(packageInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            strVersion = "No se puede cargar la version!";
        }
    }
}
