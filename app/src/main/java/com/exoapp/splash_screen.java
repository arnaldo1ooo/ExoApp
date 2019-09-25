package com.exoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class splash_screen extends Activity {
    private TextView versionApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        versionApp = (TextView) findViewById(R.id.tvVersionSplash);

        try {
            PackageInfo packageInfo;
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionApp.setText("v" + String.valueOf(packageInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede cargar la version actual!", Toast.LENGTH_LONG).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_screen.this, activity_principal.class);
                startActivity(intent);
            }
        }, 1500);
    }
}
