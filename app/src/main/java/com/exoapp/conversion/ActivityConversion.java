package com.exoapp.conversion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.exoapp.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ActivityConversion extends AppCompatActivity {
    private Button btnAnterior, btnSiguiente;
    private AdView AdView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Conversión");


        btnAnterior = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        CargarYMostrarBanner(R.id.adViewConversion);

        //Añadir el fragment 1
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContenedorFragment, new FragmentConversionSelectConve())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void Anterior(View view) {
        onBackPressed(); //Retroceder
        btnAnterior.setVisibility(View.INVISIBLE);
        btnSiguiente.setVisibility(View.VISIBLE);
    }

    public void Siguiente(View view) {
        FragmentConversionSelectConve fragmentConversionSelectConve = (FragmentConversionSelectConve) this.getSupportFragmentManager().findFragmentById(R.id.flContenedorFragment);

        String radioButtonSelect = "";
        if (fragmentConversionSelectConve.getRbPuntoAPorc().isChecked()) {
            radioButtonSelect = "puntoaporc";
        }
        if (fragmentConversionSelectConve.getRbPorcAPunto().isChecked()) {
            radioButtonSelect = "porcapunto";
        }

        //Enviar respuesta
        Bundle bundle = new Bundle();
        bundle.putSerializable("radiobuttonselect", radioButtonSelect); //Variable a enviar
        FragmentConversionConver fragmentConversionConver = new FragmentConversionConver();
        fragmentConversionConver.setArguments(bundle); //Asignar bundle como argumento al fragment

        MostrarFragmentSeleccionado(fragmentConversionConver, R.id.flContenedorFragment);

        btnAnterior.setVisibility(View.VISIBLE);
        btnSiguiente.setVisibility(View.INVISIBLE);
    }


    private void MostrarFragmentSeleccionado(Fragment fragment, int elContenedor) {
        this.getSupportFragmentManager().beginTransaction()
                .replace(elContenedor, fragment)
                .addToBackStack(null) //Agrega a la pila para que el fragment anterior no se pierda
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() { //Boton atras de la app
        onBackPressed();
        btnAnterior.setVisibility(View.INVISIBLE);
        btnSiguiente.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { //Al oprimir boton atras del celular
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackPressed();
            btnAnterior.setVisibility(View.INVISIBLE);
            btnSiguiente.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void CargarYMostrarBanner(int elAdBanner) {
        AdView1 = findViewById(elAdBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView1.loadAd(adRequest);
        AdView1.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Código a ejecutar cuando un anuncio termina de cargarse.
            }

            @Override
            public void onAdOpened() {
                // Código que se ejecutará cuando un anuncio abra una
                // superposición que cubre la pantalla.
            }

            @Override
            public void onAdClicked() {
                // Código que se ejecutará cuando el usuario
                // haga clic en un anuncio.
            }

            @Override
            public void onAdClosed() {
                // Código a ejecutar cuando el usuario está a punto de regresar
                // a la aplicación después de pulsar en un anuncio.
            }
        });
    }
}