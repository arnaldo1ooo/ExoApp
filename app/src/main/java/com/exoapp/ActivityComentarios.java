package com.exoapp;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Comentarios anonimos: el usuario escribe y envia, sin dar su correo.
 * El mensaje se manda a un Google Form (ver res/values/config_comentarios.xml) y
 * el aviso llega al correo del desarrollador desde Google Forms.
 */
public class ActivityComentarios extends AppCompatActivity {
    private static final int MINIMO_LETRAS = 10;
    private static final long ESPERA_ENTRE_ENVIOS_MS = 30_000;
    private static long ultimoEnvio = -ESPERA_ENTRE_ENVIOS_MS; // SystemClock.elapsedRealtime del ultimo envio

    private Spinner spTipo;
    private EditText etComentario;
    private TextView tvContador, tvAviso;
    private Button btnEnviar;
    private final ExecutorService ejecutor = Executors.newSingleThreadExecutor();
    private final Handler hiloPrincipal = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_comentarios);

        spTipo = findViewById(R.id.spTipoComentario);
        etComentario = findViewById(R.id.etComentario);
        tvContador = findViewById(R.id.tvContadorComentario);
        tvAviso = findViewById(R.id.tvAvisoComentario);
        btnEnviar = findViewById(R.id.btnEnviarComentario);

        spTipo.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_configuracion,
                getResources().getStringArray(R.array.com_tipos)));

        etComentario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvContador.setText(s.length() + "/1000");
                tvAviso.setVisibility(View.GONE);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enviar();
            }
        });

        ((AdView) findViewById(R.id.adView1)).loadAd(new AdRequest.Builder().build());
    }

    private void Enviar() {
        final String mensaje = etComentario.getText().toString().trim();

        if (getString(R.string.comentarios_form_url).contains("REEMPLAZAR")
                || getString(R.string.comentarios_entry_tipo).contains("REEMPLAZAR")
                || getString(R.string.comentarios_entry_mensaje).contains("REEMPLAZAR")) {
            Aviso(getString(R.string.com_error_config)); // falta configurar el formulario
            return;
        }
        if (mensaje.length() < MINIMO_LETRAS) {
            Aviso(getString(R.string.com_error_corto));
            return;
        }
        if (SystemClock.elapsedRealtime() - ultimoEnvio < ESPERA_ENTRE_ENVIOS_MS) {
            Aviso(getString(R.string.com_error_espera)); // evita enviar varias veces seguidas
            return;
        }

        final String tipo = String.valueOf(spTipo.getSelectedItem());
        final String completo = mensaje + "\n\n[App " + VersionApp() + " | Android " + Build.VERSION.RELEASE + "]";

        btnEnviar.setEnabled(false);
        btnEnviar.setText(R.string.com_enviando);

        ejecutor.execute(new Runnable() {
            @Override
            public void run() {
                final boolean ok = Publicar(tipo, completo);
                hiloPrincipal.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isFinishing() || isDestroyed()) {
                            return;
                        }
                        btnEnviar.setEnabled(true);
                        btnEnviar.setText(R.string.com_enviar);
                        if (ok) {
                            ultimoEnvio = SystemClock.elapsedRealtime();
                            etComentario.setText("");
                            Toast.makeText(ActivityComentarios.this, R.string.com_gracias, Toast.LENGTH_LONG).show();
                        } else {
                            Aviso(getString(R.string.com_error_red)); // el texto escrito se conserva
                        }
                    }
                });
            }
        });
    }

    /* Envia los datos al formulario de Google como si se llenara el formulario. Devuelve true si Google lo recibio. */
    private boolean Publicar(String tipo, String mensaje) {
        HttpURLConnection conexion = null;
        try {
            String cuerpo = URLEncoder.encode(getString(R.string.comentarios_entry_tipo), "UTF-8") + "="
                    + URLEncoder.encode(tipo, "UTF-8") + "&"
                    + URLEncoder.encode(getString(R.string.comentarios_entry_mensaje), "UTF-8") + "="
                    + URLEncoder.encode(mensaje, "UTF-8");
            byte[] datos = cuerpo.getBytes(StandardCharsets.UTF_8);

            conexion = (HttpURLConnection) new URL(getString(R.string.comentarios_form_url)).openConnection();
            conexion.setRequestMethod("POST");
            conexion.setConnectTimeout(10_000);
            conexion.setReadTimeout(10_000);
            conexion.setDoOutput(true);
            conexion.setInstanceFollowRedirects(false);
            conexion.setFixedLengthStreamingMode(datos.length);
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            OutputStream salida = conexion.getOutputStream();
            salida.write(datos);
            salida.close();

            int codigo = conexion.getResponseCode();
            return codigo >= 200 && codigo < 400;
        } catch (Exception e) {
            return false;
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }
    }

    private String VersionApp() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
            return "?";
        }
    }

    private void Aviso(String texto) {
        tvAviso.setText(texto);
        tvAviso.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        ejecutor.shutdown();
        super.onDestroy();
    }
}
