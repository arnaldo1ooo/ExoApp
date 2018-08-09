package com.exofpune;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
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

import java.io.File;
import java.lang.annotation.ElementType;
import java.text.DecimalFormat;

import capturapantalla.ScreenshotType;
import capturapantalla.ScreenshotUtils;

public class activity_evalu1 extends AppCompatActivity {

    int TotalPuntos1 = 15;
    int TotalPuntos2 = 15;
    int TotalPuntos3 = 10;
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
    TextView tvPuntosFinal;
    TextView tvNota;
    double resultado;
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

    //Crear boton compartir en action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compartir, menu);
        return true;
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
        setContentView(R.layout.activity_evalu1);

        //Action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        try {
            Bundle extras = getIntent().getExtras();
            TotalPuntos1 = extras.getInt("puntototal1parcial");
            TotalPuntos2 = extras.getInt("puntototal2parcial");
            TotalPuntos3 = extras.getInt("puntototaltp");
            MinNota5 = extras.getDouble("puntotonota5");
            PuntosParaExonerar = extras.getDouble("Minimo Exoneracion");
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnCalculo = (Button) findViewById(R.id.btnCalculo);
        et1parcial = (EditText) findViewById(R.id.et1Parcial);
        et2parcial = (EditText) findViewById(R.id.et2parcial);
        etTp = (EditText) findViewById(R.id.etTp);
        tvResultado = (TextView) findViewById(R.id.tvResultado);
        sp1 = (Spinner) findViewById(R.id.sp1);
        sp2 = (Spinner) findViewById(R.id.sp2);
        sp3 = (Spinner) findViewById(R.id.sp3);
        tvTotalText1 = (TextView) findViewById(R.id.tvTotalText1);
        tvTotalText2 = (TextView) findViewById(R.id.tvTotalText2);
        tvTotalText3 = (TextView) findViewById(R.id.tvTotalText3);
        tvFeli = (TextView) findViewById(R.id.tvFeli);
        tvFaltante = (TextView) findViewById(R.id.tvFaltante);
        tvPuntosFinal = (TextView) findViewById(R.id.tvPuntosFinal);
        tvNota = (TextView) findViewById(R.id.tvNota);
        btnCompartir = (Button) findViewById(R.id.btnCompartir);
        ivCompartir = (ImageView) findViewById(R.id.ivCompartir);
        layoutCompartir = (LinearLayout) findViewById(R.id.layoutCompartir);

        //Spinner
        ArrayAdapter<String> adaptadorsp = new ArrayAdapter<String>(this, R.layout.spinner_configuracion, array);
        sp1.setAdapter(adaptadorsp);
        sp2.setAdapter(adaptadorsp);
        sp3.setAdapter(adaptadorsp);

        //Se pone invisible los text view
        PonerTexViewInvisibles();

        //Llamada de metodos
        AlCambiarValorSpinner(sp1, et1parcial, tvTotalText1, TotalPuntos1, PuntosObtenidos1);
        AlCambiarValorSpinner(sp2, et2parcial, tvTotalText2, TotalPuntos2, PuntosObtenidos2);
        AlCambiarValorSpinner(sp3, etTp, tvTotalText3, TotalPuntos3, PuntosObtenidos3);
        AlEscribirEnEditText(et1parcial, PuntosObtenidos1, TotalPuntos1);
        AlEscribirEnEditText(et2parcial, PuntosObtenidos2, TotalPuntos2);
        AlEscribirEnEditText(etTp, PuntosObtenidos3, TotalPuntos3);

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

        btnCalculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat df = new DecimalFormat("#.###");
                OcultarTeclado(v);
                SiEsVacio();

                if (MensajesError(et1parcial, sp1, PuntosObtenidos1, TotalPuntos1) == false &&
                        MensajesError(et2parcial, sp2, PuntosObtenidos2, TotalPuntos2) == false &&
                        MensajesError(etTp, sp3, PuntosObtenidos3, TotalPuntos3) == false) {
                    resultado = PuntosObtenidos1 + PuntosObtenidos2 + PuntosObtenidos3;
                    tvResultado.setText(df.format(resultado) + " Puntos de 40 Puntos");
                    Felicitar();
                }
            }
        });
    }

    private void AlEscribirEnEditText(final EditText ElEditText, final Double PuntosObtenidos, final int TotalPuntos) {
        //Al cambiar texto1
        ElEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  //Antes de escritir

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  //Cuando se esta modificando un texto

            }

            @Override
            public void afterTextChanged(Editable s) { //Despues de escribir
                if (ElEditText == et1parcial) {
                    PuntosObtenidos1 = ConversionPorcyPunto(sp1, et1parcial, tvTotalText1, TotalPuntos1, PuntosObtenidos1);
                } else {
                    if (ElEditText == et2parcial) {
                        PuntosObtenidos2 = ConversionPorcyPunto(sp2, et2parcial, tvTotalText2, TotalPuntos2, PuntosObtenidos2);
                    } else {
                        if (ElEditText == etTp) {
                            PuntosObtenidos3 = ConversionPorcyPunto(sp3, etTp, tvTotalText3, TotalPuntos3, PuntosObtenidos3);
                        }
                    }
                }
            }
        });
    }

    public double ConversionPorcyPunto(Spinner ElSpinner, EditText ElEditText, TextView tvTotal, int Totalpuntos, double PuntosObtenidos) {
        DecimalFormat df = new DecimalFormat("#.###");
        if (ElEditText.getText().toString().equals("") == false) {
            if (ElSpinner.getSelectedItemId() == 0) {
                Log.d("Metodo Conversion", "Seleccion de Spinner % %");
                if (MensajesError(ElEditText, ElSpinner, PuntosObtenidos, Totalpuntos) == false) { //Revisa si cumple ELEditText
                    PuntosObtenidos = (Double.parseDouble(ElEditText.getText().toString()) * Totalpuntos) / 100;
                    tvTotal.setText(df.format(PuntosObtenidos) + " de " + Totalpuntos + " Puntos");
                } else {
                    PuntosObtenidos = 0.0;
                    tvTotal.setText("0 de " + Totalpuntos + " Puntos");
                }
            }

            if (ElSpinner.getSelectedItemId() == 1) {
                Log.d("Metodo Conversion", "Seleccion de Spinner Puntos");
                if (MensajesError(ElEditText, ElSpinner, Double.parseDouble(ElEditText.getText().toString()), Totalpuntos) == false) { //Revisa si cumple ELEditText
                    PuntosObtenidos = (Double.parseDouble(ElEditText.getText().toString()) * 100) / Totalpuntos;
                    tvTotal.setText(df.format(PuntosObtenidos) + "% de 100%");
                } else {
                    PuntosObtenidos = 0.0;
                    tvTotal.setText("0 de " + Totalpuntos + " Puntos");
                }
            }

        } else {
            Log.d("Metodo Conversion", "Esta en blanco");
            tvTotal.setText("0 de " + Totalpuntos + " Puntos");
        }
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
                ElEditText.setTextColor(Color.RED);
                Toast toast = Toast.makeText(this, "El porcentaje obtenido no puede ser mayor al 100%", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            } else {
                ElEditText.setTextColor(Color.WHITE);
            }
        } else {
            if (ElSpinner.getSelectedItemId() == 1) {
                if (PuntosObtenidos > TotalPuntos) {
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

    private void AlCambiarValorSpinner(final Spinner ElSpinner, final EditText ElEditText, final TextView tvTotal, final int TotalPuntos, final Double PuntosObtenidos) {
        //Al cambiar valor de spinner
        ElSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
                if (ElSpinner == sp1) {
                    Log.d("Mensaje", "Se cambió de valor el 1° spinner");

                    if (ElSpinner.getSelectedItemId() == 0) {
                        Log.d("Mensaje", "Se seleccionó el item 0 en el sp1");
                        ConversionPorcyPunto(ElSpinner, ElEditText, tvTotal, TotalPuntos, PuntosObtenidos);
                    }

                    if (ElSpinner.getSelectedItemId() == 1) {
                        Log.d("Mensaje", "Se seleccionó el item 1 en el sp1");
                        ConversionPorcyPunto(ElSpinner, ElEditText, tvTotal, TotalPuntos, PuntosObtenidos);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(getApplicationContext(), "Ninguno seleccionado", Toast.LENGTH_SHORT).show();
            }
        });
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

    public void Felicitar() {
        DecimalFormat df = new DecimalFormat("#.###");
        if (resultado >= PuntosParaExonerar) {
            tvFeli.setTextColor(Color.GREEN);
            tvFeli.setText("EXONERASTE!! :D");
            tvFeli.setVisibility(View.VISIBLE);
            tvFaltante.setVisibility(TextView.VISIBLE);
            tvResultado.setVisibility(TextView.VISIBLE);
            tvPuntosFinal.setVisibility(TextView.INVISIBLE);
            if (resultado >= MinNota5) {
                tvFaltante.setText("Obtuviste nota 5");
            } else {
                tvFaltante.setText("Obtuviste nota 4");
            }
        } else {
            if (resultado < PuntosParaExonerar && resultado >= 32) {
                tvFeli.setTextColor(Color.YELLOW);
                tvFeli.setText("Casi exoneraste :/");
                tvFaltante.setText("Te faltaron " + df.format(PuntosParaExonerar - (PuntosObtenidos1 + PuntosObtenidos2 + PuntosObtenidos3)) + " Puntos para Exonerar");
                tvFeli.setVisibility(View.VISIBLE);
                tvFaltante.setVisibility(TextView.VISIBLE);
                tvResultado.setVisibility(TextView.VISIBLE);
                tvPuntosFinal.setVisibility(TextView.VISIBLE);
                tvNota.setVisibility(TextView.VISIBLE);
                tvPuntosFinal.setText("Debes hacer " + df.format(((60 - (PuntosObtenidos1 + PuntosObtenidos2 + PuntosObtenidos3)) * 100) / 60) + "% en la final (" + df.format(60 - (PuntosObtenidos1 + PuntosObtenidos2 + PuntosObtenidos3)) + " de 60)");
            } else{
                if (resultado < PuntosParaExonerar) {
                    tvFeli.setTextColor(Color.RED);
                    tvFeli.setText("No exoneraste :(");
                    tvFaltante.setText("Te faltaron " + df.format(PuntosParaExonerar - (PuntosObtenidos1 + PuntosObtenidos2 + PuntosObtenidos3)) + " Puntos para Exonerar");
                    tvFeli.setVisibility(View.VISIBLE);
                    tvFaltante.setVisibility(TextView.VISIBLE);
                    tvResultado.setVisibility(TextView.VISIBLE);
                    tvPuntosFinal.setVisibility(TextView.VISIBLE);
                    tvNota.setVisibility(TextView.VISIBLE);
                    tvPuntosFinal.setText("Debes hacer " + df.format(((60 - (PuntosObtenidos1 + PuntosObtenidos2 + PuntosObtenidos3)) * 100) / 60) + "% en la final (" + df.format(60 - (PuntosObtenidos1 + PuntosObtenidos2 + PuntosObtenidos3)) + " de 60)");
                }
            }
        }
    }

    public void OcultarTeclado(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /*  Compartir la captura de pantalla  */
    private void CompartirCaptura(File file) {
        Uri uri = Uri.fromFile(file);//Convert file path into Uri for sharing
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.sharing_text));
        intent.putExtra(Intent.EXTRA_STREAM, uri);//pass uri here
        startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
    }

    private void PonerTexViewInvisibles() {
        tvFaltante.setVisibility(View.INVISIBLE);
        tvResultado.setVisibility(View.INVISIBLE);
        tvFeli.setVisibility(View.INVISIBLE);
        tvPuntosFinal.setVisibility(TextView.INVISIBLE);
        tvNota.setVisibility(TextView.INVISIBLE);
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