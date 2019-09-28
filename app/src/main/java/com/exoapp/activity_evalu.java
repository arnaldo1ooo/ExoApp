package com.exoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.text.DecimalFormat;

import com.exoapp.capturapantalla.ScreenshotType;
import com.exoapp.capturapantalla.ScreenshotUtils;

public class activity_evalu extends AppCompatActivity {
    int TotalPuntos1 = 0;
    int TotalPuntos2 = 0;
    int TotalPuntos3 = 0;
    double MinNota5 = 37.6;
    double PuntosParaExonerar = 32.4;
    double PuntosObtenidos1 = 0;
    double PuntosObtenidos2 = 0;
    double PuntosObtenidos3 = 0;
    Button btnCalculo;
    EditText et1parcial;
    EditText et2parcial;
    EditText etTp;
    TextView tvResultado;
    TextView tvFaltante;
    double Resultado;
    Spinner sp1;
    Spinner sp2;
    Spinner sp3;
    String[] array = {"%", "Puntos"};
    TextView tvTotalText1;
    TextView tvTotalText2;
    TextView tvTotalText3;
    TextView tvFeli;
    private ImageView ivCompartir;
    private Button btnCompartir;
    private LinearLayout layoutCompartir;
    private ImageView ivEmoji;
    private AdView AdView1;
    private Button btnBonificacion;

    //Crear boton compartir en action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compartir, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "OnStart", Toast.LENGTH_SHORT).show();
        // La actividad está a punto de hacerse visible.
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();
        // La actividad se ha vuelto visible (ahora se "reanuda").
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(this, "OnPause", Toast.LENGTH_SHORT).show();
        // Enfocarse en otra actividad  (esta actividad está a punto de ser "detenida").
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this, "OnStop", Toast.LENGTH_SHORT).show();
        // La actividad ya no es visible (ahora está "detenida")
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show();
        // La actividad está a punto de ser destruida.
    }


    //Al hacer click en boton compartir
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnCompartir) {
            TomarCaptura(ScreenshotType.FULL);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalu);

        //Action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnCalculo = findViewById(R.id.btnCalculo);
        et1parcial = findViewById(R.id.et1Parcial);
        et2parcial = findViewById(R.id.et2Parcial);
        etTp = findViewById(R.id.etTp);
        tvResultado = findViewById(R.id.tvResultado);
        sp1 =findViewById(R.id.sp1);
        sp2 = findViewById(R.id.sp2);
        sp3 = findViewById(R.id.sp3);
        tvTotalText1 = findViewById(R.id.tvTotalText1);
        tvTotalText2 = findViewById(R.id.tvTotalText2);
        tvTotalText3 = findViewById(R.id.tvTotalText3);
        tvFeli = findViewById(R.id.tv_feli);
        tvFaltante = findViewById(R.id.tvFaltante);
        btnCompartir = findViewById(R.id.btnCompartir);
        ivCompartir = findViewById(R.id.ivCompartir);
        layoutCompartir = findViewById(R.id.layout_compartir);
        ivEmoji = findViewById(R.id.ivEmoji);
        btnBonificacion = findViewById(R.id.btnBonificacion);

        MetodoBanner();

        //Llamamos a los datos que el intent o activity anterior envió, son los puntos totales segun el boton presionado en el activity principal
        RecibeDatosPuntos();

        try {
            //Llamada de metodos
            VisibilidadObjetos();
            OcultarTrabajoPractico();

            AlEscribirEnEditText(et1parcial, sp1, tvTotalText1, TotalPuntos1, PuntosObtenidos1);
            AlEscribirEnEditText(et2parcial, sp2, tvTotalText2, TotalPuntos2, PuntosObtenidos2);
            AlEscribirEnEditText(etTp, sp3, tvTotalText3, TotalPuntos3, PuntosObtenidos3);
        } catch (NullPointerException e) {
            System.out.println(" Error en la llamada de metodos: " + e);
        }


        //Al dar enter
        etTp.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    btnCalculo.callOnClick();
                    return true;
                }
                return false;
            }
        });

        btnBonificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBonificacion = new Intent(v.getContext(), activity_bonificacion.class);
                String[] Stringcortado = tvResultado.getText().toString().split(" ");

                intentBonificacion.putExtra("bonificacion", Stringcortado[0].replace(",", "."));
                Log.d("Resultado", "Bonificacion a enviar a otro intent: " + Stringcortado[0]);
                startActivityForResult(intentBonificacion, 0);
            }
        });

        MetodosSpinner();
    }

    private void MetodosSpinner() {
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Elemento seleccionado ->
                PuntosObtenidos1 = ConversionPorcyPunto(et1parcial, sp1, tvTotalText1, TotalPuntos1, PuntosObtenidos1);
                Log.d("MetodoSpinner sp1: ", PuntosObtenidos1+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //No se ha seleccionado nada.
                Toast.makeText(getApplicationContext(), "Ninguno seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Elemento seleccionado ->
                PuntosObtenidos2 = ConversionPorcyPunto(et2parcial, sp2, tvTotalText2, TotalPuntos2, PuntosObtenidos2);
                Log.d("MetodoSpinner sp2: ", PuntosObtenidos2+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //No se ha seleccionado nada.
                Toast.makeText(getApplicationContext(), "Ninguno seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Elemento seleccionado ->
                PuntosObtenidos3 = ConversionPorcyPunto(etTp, sp3, tvTotalText3, TotalPuntos3, PuntosObtenidos3);
                Log.d("MetodoSpinner sp3: ", PuntosObtenidos3+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //No se ha seleccionado nada.
                Toast.makeText(getApplicationContext(), "Ninguno seleccionado", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void Calcular(View v) {
        OcultarTeclado(v);
        SiEsVacio();

        DecimalFormat df = new DecimalFormat("#.###");
        if (MensajesError(et1parcial, sp1, PuntosObtenidos1, TotalPuntos1) == false &&
                MensajesError(et2parcial, sp2, PuntosObtenidos2, TotalPuntos2) == false &&
                MensajesError(etTp, sp3, PuntosObtenidos3, TotalPuntos3) == false) {
            Resultado = PuntosObtenidos1 + PuntosObtenidos2 + PuntosObtenidos3;
            Log.d("Click Boton Calculo", "Puntos Obtenidos1: " + PuntosObtenidos1);
            Log.d("Click Boton Calculo", "Puntos Obtenidos2: " + PuntosObtenidos2);
            Log.d("Click Boton Calculo", "Puntos Obtenidos3: " + PuntosObtenidos3);
            tvResultado.setText(df.format(Resultado) + " Puntos de 40 Puntos");
            Felicitar(v);
        }
    }


    private void MetodoBanner() {
        AdView1 = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView1.loadAd(adRequest);
        AdView1.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Código a ejecutar cuando un anuncio termina de cargarse.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Código a ejecutar cuando falla una solicitud de anuncio.
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
            public void onAdLeftApplication() {
                // Código a ejecutar cuando el usuario
                // ha abandonado la aplicación.
            }

            @Override
            public void onAdClosed() {
                // Código a ejecutar cuando el usuario está a punto de regresar
                // a la aplicación después de pulsar en un anuncio.
            }
        });
    }

    private void RecibeDatosPuntos() {
        try {
            TotalPuntos1 = Integer.parseInt(getIntent().getExtras().getString("TotalPuntos1"));
            Log.d("Total de puntos 1", "" + TotalPuntos1);
            TotalPuntos2 = Integer.parseInt(getIntent().getExtras().getString("TotalPuntos2"));
            TotalPuntos3 = Integer.parseInt(getIntent().getExtras().getString("TotalPuntos3"));
            String Personalizado = getIntent().getExtras().getString("EsPersonalizado");
            if (Personalizado.equals("Si")) {
                PuntosParaExonerar = Double.parseDouble(getIntent().getExtras().getString("Minimo Exoneracion"));
                MinNota5 = Double.parseDouble(getIntent().getExtras().getString("Minimo nota 5"));
            }
        } catch (NumberFormatException e) {
            Log.d("Error al obtener datos", "" + e);
            e.printStackTrace();
        }

        //Asignamos lo anterior
        DecimalFormat df = new DecimalFormat("#.###");
        tvTotalText1.setText(df.format(PuntosObtenidos1) + " de " + TotalPuntos1 + " Puntos");
        tvTotalText2.setText(df.format(PuntosObtenidos2) + " de " + TotalPuntos2 + " Puntos");
        tvTotalText3.setText(df.format(PuntosObtenidos3) + " de " + TotalPuntos3 + " Puntos");
        //Spinner
        ArrayAdapter<String> adaptadorsp = new ArrayAdapter<String>(this, R.layout.spinner_configuracion, array);
        sp1.setAdapter(adaptadorsp);
        sp2.setAdapter(adaptadorsp);
        sp3.setAdapter(adaptadorsp);
    }

    private void AlEscribirEnEditText(final EditText ElEditText, final Spinner ElSpinner, final TextView tvTotalText, final int TotalPuntos, final Double PuntosObtenidos) {
        ElEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  //Antes de escritir

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  //Cuando se esta modificando un texto

            }

            @Override
            public void afterTextChanged(Editable s) { //Despues de escribir
                try {
                    if (ElEditText == et1parcial) {
                        PuntosObtenidos1 = ConversionPorcyPunto(ElEditText, ElSpinner, tvTotalText, TotalPuntos, PuntosObtenidos);
                        Log.d("AlEscribirEnEditText", "" + PuntosObtenidos1);
                    } else {
                        if (ElEditText == et2parcial) {
                            PuntosObtenidos2 = ConversionPorcyPunto(ElEditText, ElSpinner, tvTotalText, TotalPuntos, PuntosObtenidos);
                            Log.d("AlEscribirEnEditText", "" + PuntosObtenidos2);
                        } else {
                            if (ElEditText == etTp) {
                                PuntosObtenidos3 = ConversionPorcyPunto(ElEditText, ElSpinner, tvTotalText, TotalPuntos, PuntosObtenidos);
                                Log.d("AlEscribirEnEditText", "" + PuntosObtenidos3);
                            }
                        }
                    }
                } catch (Exception n) {
                    Log.d("Despues de cambiar text", "Error al escribir en editext");
                }

            }
        });
    }

    Double PuntosObtenidosporciento;

    public double ConversionPorcyPunto(EditText ElEditText, Spinner ElSpinner, TextView tvTotal, int Totalpuntos, double PuntosObtenidos) {
        DecimalFormat df = new DecimalFormat("#.###");
        if (ElEditText.getText().toString().equals("") == false) {
            if (ElSpinner.getSelectedItemId() == 0) { //Si en el spinner esta %
                Log.d("Metodo Conversion", "Seleccion de Spinner %");
                if (MensajesError(ElEditText, ElSpinner, PuntosObtenidos, Totalpuntos) == false) { //Revisa si cumple ELEditText
                    PuntosObtenidos = (Double.parseDouble(ElEditText.getText().toString()) * Totalpuntos) / 100;
                    tvTotal.setText(df.format(PuntosObtenidos) + " de " + Totalpuntos + " Puntos");
                } else {
                    PuntosObtenidos = 0.0;
                    tvTotal.setText("0 de " + Totalpuntos + " Puntos");
                }
            }

            if (ElSpinner.getSelectedItemId() == 1) { //Si en el spinner esta Puntos
                Log.d("Metodo Conversion", "Seleccion de Spinner Puntos");
                if (MensajesError(ElEditText, ElSpinner, Double.parseDouble(ElEditText.getText().toString()), Totalpuntos) == false) { //Revisa si cumple ELEditText
                    PuntosObtenidosporciento = (Double.parseDouble(ElEditText.getText().toString()) * 100) / Totalpuntos;
                    PuntosObtenidos = Double.parseDouble(ElEditText.getText().toString());
                    tvTotal.setText(df.format(PuntosObtenidosporciento) + "% de 100%");
                } else {
                    PuntosObtenidos = 0.0;
                    tvTotal.setText("0% de 100%");
                }
            }

        } else {
            Log.d("Metodo Conversion", "Esta en blanco");

            if (ElSpinner.getSelectedItemId() == 0) {
                tvTotal.setText("0 de " + Totalpuntos + " Puntos");
            } else {
                if (ElSpinner.getSelectedItemId() == 1) {
                    tvTotal.setText("0 % de 100%");
                }
            }
        }
        Log.d("PuntosObtenidosConve", "" + PuntosObtenidos);
        return PuntosObtenidos;
    }


    //Metodo Mensajes de error
    public boolean MensajesError(EditText ElEditText, Spinner ElSpinner, Double PuntosObtenidos, int TotalPuntos) {
        if (ElEditText.getText().toString().equals("") == true) { //Si el EditText esta en blanco mandar true
            Log.d("Metodo MensajesError", "Esta en blanco");
            return true;
        }

        if (ElSpinner.getSelectedItemId() == 0) {
            if (Double.parseDouble(ElEditText.getText().toString()) > 100) {
                VisibilidadObjetos();
                ElEditText.setTextColor(Color.RED);
                Toast toast = Toast.makeText(this, "El porcentaje obtenido no puede ser mayor al 100%", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            } else {
                ElEditText.setTextColor(Color.WHITE);
            }
        } else {
            if (ElSpinner.getSelectedItemId() == 1) {
                if (Double.parseDouble(ElEditText.getText().toString()) > TotalPuntos) {
                    VisibilidadObjetos();
                    ElEditText.setTextColor(Color.RED);
                    Toast toast = Toast.makeText(this, "El punto obtenido no puede ser mayor a " + TotalPuntos + " Puntos", Toast.LENGTH_SHORT);
                    toast.show();
                    return true;
                } else {
                    ElEditText.setTextColor(Color.WHITE);
                }
            }
        }
        return false;
    }


    public void SiEsVacio() {
        if (et1parcial.getText().toString().equals("")) {//Si es vacio
            et1parcial.setText("0");
        }
        if (et2parcial.getText().toString().equals("")) {//Si es vacio
            et2parcial.setText("0");
        }
        if (etTp.getText().toString().equals("")) {//Si es vacio
            etTp.setText("0");
        }
    }

    public void Felicitar(View view) {
        DecimalFormat df = new DecimalFormat("#.###");
        if (Resultado >= PuntosParaExonerar) {
            tvFeli.setTextColor(Color.GREEN);
            tvFeli.setText("EXONERASTE!");
            tvFeli.setVisibility(View.VISIBLE);
            ivEmoji.setVisibility(ImageView.VISIBLE);
            ivEmoji.setImageResource(R.drawable.feliz);
            tvFaltante.setVisibility(TextView.VISIBLE);
            tvResultado.setVisibility(TextView.VISIBLE);
            if (Resultado >= MinNota5) {
                tvFaltante.setText("Obtuviste nota 5");
            } else {
                tvFaltante.setText("Obtuviste nota 4");
            }
        } else {
            if (Resultado < PuntosParaExonerar && Resultado >= 32) {
                tvFeli.setTextColor(Color.YELLOW);
                tvFeli.setText("Casi exoneraste");
                ivEmoji.setVisibility(ImageView.VISIBLE);
                ivEmoji.setImageResource(R.drawable.serio);
                tvFaltante.setText("Te faltaron " + df.format(PuntosParaExonerar - (PuntosObtenidos1 + PuntosObtenidos2 + PuntosObtenidos3)) + " Puntos para Exonerar");
                tvFeli.setVisibility(View.VISIBLE);
                tvFaltante.setVisibility(TextView.VISIBLE);
                tvResultado.setVisibility(TextView.VISIBLE);
                btnBonificacion.setVisibility(TextView.INVISIBLE);
            } else {
                if (Resultado < PuntosParaExonerar) {
                    tvFeli.setTextColor(Color.RED);
                    tvFeli.setText("No exoneraste");
                    ivEmoji.setVisibility(ImageView.VISIBLE);
                    ivEmoji.setImageResource(R.drawable.triste);
                    tvFaltante.setText("Te faltaron " + df.format(PuntosParaExonerar - (PuntosObtenidos1 + PuntosObtenidos2 + PuntosObtenidos3)) + " Puntos para Exonerar");
                    tvFeli.setVisibility(View.VISIBLE);
                    tvFaltante.setVisibility(TextView.VISIBLE);
                    tvResultado.setVisibility(TextView.VISIBLE);
                    btnBonificacion.setVisibility(TextView.VISIBLE);
                }
            }
        }
    }


    public void OcultarTrabajoPractico() {
        if (TotalPuntos3 == 0) {
            etTp.setVisibility(View.INVISIBLE);
            sp3.setVisibility(View.INVISIBLE);
            tvTotalText3.setVisibility(View.INVISIBLE);
        }
    }

    public void OcultarTeclado(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /*  Compartir la captura de pantalla  */
    private void CompartirCaptura(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= 26) { //Api 26 es Android 8.0
            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);//Convierte la ruta del archivo en Uri para compartir
        } else {
            uri = Uri.fromFile(file);//Convierte la ruta del archivo en Uri para compartir
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.sharing_text));
        intent.putExtra(Intent.EXTRA_STREAM, uri);//pass uri here
        startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
    }

    private void VisibilidadObjetos() {
        tvFaltante.setVisibility(View.INVISIBLE);
        tvResultado.setVisibility(View.INVISIBLE);
        tvFeli.setVisibility(View.INVISIBLE);
        ivEmoji.setVisibility(ImageView.INVISIBLE);
        btnBonificacion.setVisibility(View.INVISIBLE);
    }

    /*  Método que tomará una captura de pantalla en Bases de captura de pantalla Tipo ENUM  */
    private void TomarCaptura(ScreenshotType screenshotType) {
        Bitmap b = null;
        switch (screenshotType) {
            case FULL:
                //If Screenshot type is FULL take full page screenshot i.e our root content.
                b = ScreenshotUtils.getScreenShot(layoutCompartir);
                break;
            case CUSTOM:
                //If Screenshot type is CUSTOM

                btnCompartir.setVisibility(View.INVISIBLE);//set the visibility to INVISIBLE of first button
                //hiddenText.setVisibility(View.VISIBLE);//set the visibility to VISIBLE of hidden text

                b = ScreenshotUtils.getScreenShot(layoutCompartir);

                //After taking screenshot reset the button and view again
                btnCompartir.setVisibility(View.VISIBLE);//set the visibility to VISIBLE of first button again
                //hiddenText.setVisibility(View.INVISIBLE);//set the visibility to INVISIBLE of hidden text

                //NOTE:  You need to use visibility INVISIBLE instead of GONE to remove the view from frame else it wont consider the view in frame and you will not get screenshot as you required.
                break;
        }

        //If bitmap is not null
        if (b != null) {
            MostrarCapturaEnImage(b);//show bitmap over imageview

            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
            File file = ScreenshotUtils.store(b, "screenshot" + screenshotType + ".jpg", saveFile);//save the screenshot to selected path
            CompartirCaptura(file);//finally share screenshot
        } else
            //If bitmap is null show toast message
            Toast.makeText(this, R.string.screenshot_take_failed, Toast.LENGTH_SHORT).show();

    }

    /*  Mostrar captura de pantalla Bitmap */
    private void MostrarCapturaEnImage(Bitmap b) {
        ivCompartir.setImageBitmap(b);
    }
}