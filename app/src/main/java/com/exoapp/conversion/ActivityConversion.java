package com.exoapp.conversion;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.exoapp.R;
import com.exoapp.utilidades.Metodos;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;

/**
 * Conversion en una sola pantalla: el usuario elige "Puntos -> %" o "% -> Puntos",
 * escribe los dos datos y el resultado se actualiza solo mientras escribe.
 */
public class ActivityConversion extends AppCompatActivity {
    private RadioGroup rgModo;
    private EditText etTotalValor, etValorObte;
    private TextView tvTituloValorObte, tvValorObteUnidad, tvEjemploConver,
            tvAvisoConver, tvResultadoConver, tvDetalleConver;
    private final DecimalFormat df = Metodos.FormatoDecimal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Conversión");

        rgModo = findViewById(R.id.rgModo);
        etTotalValor = findViewById(R.id.etTotalValor);
        etValorObte = findViewById(R.id.etValorObte);
        tvTituloValorObte = findViewById(R.id.tvTituloValorObte);
        tvValorObteUnidad = findViewById(R.id.tvValorObteUnidad);
        tvEjemploConver = findViewById(R.id.tvEjemploConver);
        tvAvisoConver = findViewById(R.id.tvAvisoConver);
        tvResultadoConver = findViewById(R.id.tvResultadoConver);
        tvDetalleConver = findViewById(R.id.tvDetalleConver);

        Metodos.ConfigurarCampoDecimal(etTotalValor);
        Metodos.ConfigurarCampoDecimal(etValorObte);

        TextWatcher alEscribir = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Actualizar();
            }
        };
        etTotalValor.addTextChangedListener(alEscribir);
        etValorObte.addTextChangedListener(alEscribir);

        rgModo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                etValorObte.setText(""); // el dato cambia de unidad (puntos o %), se borra para no confundir
                Actualizar();
            }
        });

        AdView adView = findViewById(R.id.adViewConversion);
        adView.loadAd(new AdRequest.Builder().build());

        Actualizar();
    }

    private boolean EsPuntosAPorc() {
        return rgModo.getCheckedRadioButtonId() == R.id.rbPuntoAPorc;
    }

    private void Actualizar() {
        boolean puntosAPorc = EsPuntosAPorc();

        // Textos que dependen del modo
        tvTituloValorObte.setText(puntosAPorc ? R.string.conv_obtenido_puntos_titulo : R.string.conv_obtenido_porc_titulo);
        tvValorObteUnidad.setText(puntosAPorc ? R.string.conv_unidad_puntos : R.string.conv_unidad_porc);
        etValorObte.setHint(puntosAPorc ? R.string.conv_obtenido_puntos_hint : R.string.conv_obtenido_porc_hint);
        tvEjemploConver.setText(puntosAPorc ? R.string.conv_ejemplo_puntos_a_porc : R.string.conv_ejemplo_porc_a_puntos);

        String textoTotal = etTotalValor.getText().toString().trim();
        String textoObte = etValorObte.getText().toString().trim();
        double total = Metodos.ParseDoble(textoTotal);
        double obte = Metodos.ParseDoble(textoObte);

        if (textoTotal.isEmpty() || textoObte.isEmpty()) {
            Mostrar("—", getString(R.string.conv_completa), null);
            return;
        }
        if (total <= 0) {
            Mostrar("—", getString(R.string.conv_completa), getString(R.string.conv_aviso_total_cero));
            return;
        }

        if (puntosAPorc) {
            if (obte > total) {
                Mostrar("—", getString(R.string.conv_completa),
                        getString(R.string.conv_aviso_puntos_mayor, df.format(total)));
                return;
            }
            double porc = (obte * 100) / total;
            Mostrar(df.format(porc) + "%",
                    "Hiciste " + df.format(obte) + " de " + df.format(total) + " puntos, que es el "
                            + df.format(porc) + "% del examen.", null);
        } else {
            if (obte > 100) {
                Mostrar("—", getString(R.string.conv_completa), getString(R.string.conv_aviso_porc_mayor));
                return;
            }
            double puntos = (obte * total) / 100;
            Mostrar(df.format(puntos) + " puntos",
                    "El " + df.format(obte) + "% de un examen de " + df.format(total)
                            + " puntos equivale a " + df.format(puntos) + " puntos.", null);
        }
    }

    private void Mostrar(String resultado, String detalle, String aviso) {
        tvResultadoConver.setText(resultado);
        tvDetalleConver.setText(detalle);
        if (aviso == null) {
            tvAvisoConver.setVisibility(View.GONE);
        } else {
            tvAvisoConver.setText(aviso);
            tvAvisoConver.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() { // Boton atras de la barra
        finish();
        return true;
    }
}
