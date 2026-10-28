package com.exoapp.utilidades;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.exoapp.R;

/** Dialogos de la app con el mismo estilo (colores, esquinas y fuente) en modo claro y oscuro. */
public class Dialogos {

    /**
     * Dialogo de confirmacion con icono, titulo, mensaje (opcional) y dos botones.
     * El boton de la derecha es el principal. alCancelar / alAceptar pueden ser null.
     */
    public static void Confirmar(Activity activity, int iconoRes, String titulo, String mensaje,
                                 String textoCancelar, final Runnable alCancelar,
                                 String textoAceptar, final Runnable alAceptar,
                                 boolean cancelable) {
        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        final AppCompatDialog dialogo = new AppCompatDialog(activity, R.style.DialogoApp);
        dialogo.setContentView(R.layout.dialog_confirmacion);
        dialogo.setCancelable(cancelable);
        dialogo.setCanceledOnTouchOutside(cancelable);

        ImageView ivIcono = dialogo.findViewById(R.id.ivDialogoIcono);
        TextView tvTitulo = dialogo.findViewById(R.id.tvDialogoTitulo);
        TextView tvMensaje = dialogo.findViewById(R.id.tvDialogoMensaje);
        Button btnCancelar = dialogo.findViewById(R.id.btnDialogoCancelar);
        Button btnAceptar = dialogo.findViewById(R.id.btnDialogoAceptar);

        ivIcono.setImageResource(iconoRes);
        tvTitulo.setText(titulo);
        if (mensaje == null || mensaje.isEmpty()) {
            tvMensaje.setVisibility(View.GONE);
        } else {
            tvMensaje.setText(mensaje);
        }
        btnCancelar.setText(textoCancelar);
        btnAceptar.setText(textoAceptar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                if (alCancelar != null) {
                    alCancelar.run();
                }
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                if (alAceptar != null) {
                    alAceptar.run();
                }
            }
        });

        dialogo.show();

        // Ancho: casi toda la pantalla en celulares, maximo 400dp en tablets
        Window ventana = dialogo.getWindow();
        if (ventana != null) {
            DisplayMetrics dm = activity.getResources().getDisplayMetrics();
            int margen = (int) (24 * dm.density) * 2;
            int maximo = (int) (400 * dm.density);
            ventana.setLayout(Math.min(dm.widthPixels - margen, maximo), ViewGroup.LayoutParams.WRAP_CONTENT);
            ventana.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
