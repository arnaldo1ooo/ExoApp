package com.exoapp;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.exoapp.utilidades.Tema;

/** Configuracion de la app: por ahora, apariencia (automatico / claro / oscuro). */
public class ActivityConfiguracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_configuracion);

        RadioGroup rgTema = findViewById(R.id.rgTema);

        // Marca la opcion guardada antes de escuchar cambios (asi no se dispara solo)
        int actual = Tema.Leer(this);
        if (actual == Tema.CLARO) {
            rgTema.check(R.id.rbTemaClaro);
        } else if (actual == Tema.OSCURO) {
            rgTema.check(R.id.rbTemaOscuro);
        } else {
            rgTema.check(R.id.rbTemaSistema);
        }

        rgTema.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int modo = Tema.SISTEMA;
                if (checkedId == R.id.rbTemaClaro) {
                    modo = Tema.CLARO;
                } else if (checkedId == R.id.rbTemaOscuro) {
                    modo = Tema.OSCURO;
                }
                if (modo != Tema.Leer(ActivityConfiguracion.this)) {
                    Tema.Guardar(ActivityConfiguracion.this, modo); // la pantalla se redibuja sola con el nuevo modo
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
