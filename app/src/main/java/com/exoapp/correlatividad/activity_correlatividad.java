package com.exoapp.correlatividad;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.exoapp.R;

public class activity_correlatividad extends AppCompatActivity {

    TextView version;
    TextView versioncodigo;
    String strVersion;
    PackageInfo packageInfo;
    ImageButton ibFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correlatividad);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Acerca de");

        version = (TextView) findViewById(R.id.tvVersion);
        versioncodigo = (TextView) findViewById(R.id.tvVersionCodigo);
        ibFacebook = (ImageButton) findViewById(R.id.ibFacebook);

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version.setText(String.valueOf(packageInfo.versionName));
            versioncodigo.setText(String.valueOf(packageInfo.versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            strVersion = "No se puede cargar la version!";
        }


        ibFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookId = "fb://page/2152438201747665";
                String urlPage = "http://www.facebook.com/exoapp1";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId )));
                } catch (Exception e) {
                    Log.e("Click en fb", "Aplicación no instalada.");
                    //Abre url de pagina.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }
            }
        });
    }
}
