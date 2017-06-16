package com.exofpune;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.exofpune.R;
import com.exofpune.activity_tipo_evalu;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView version;
        String strVersion;
        PackageInfo packageInfo;

        version = (TextView) findViewById(R.id.tvVersionSplash);

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version.setText("v" + String.valueOf(packageInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            strVersion = "No se puede cargar la version!";
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent (SplashScreen.this, activity_tipo_evalu.class);
                startActivity(intent);
            }
        },3000);

    }

}
