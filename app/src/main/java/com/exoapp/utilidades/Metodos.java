package com.exoapp.utilidades;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.ContextCompat;

import com.exoapp.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Metodos {

    /* En pantallas grandes (tablet) limita el ancho del contenido y lo centra. En celulares no hace nada. */
    public static void LimitarAnchoContenido(Activity activity) {
        final int anchoMaxDp = 560;
        if (activity.getResources().getConfiguration().screenWidthDp <= anchoMaxDp) {
            return;
        }
        ViewGroup contenido = activity.findViewById(android.R.id.content);
        if (contenido == null || contenido.getChildCount() == 0) {
            return;
        }
        View raiz = contenido.getChildAt(0);
        ViewGroup.LayoutParams lp = raiz.getLayoutParams();
        if (lp instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams flp = (FrameLayout.LayoutParams) lp;
            flp.width = (int) (anchoMaxDp * activity.getResources().getDisplayMetrics().density);
            flp.gravity = Gravity.CENTER_HORIZONTAL;
            raiz.setLayoutParams(flp);
            activity.getWindow().setBackgroundDrawable(
                    new ColorDrawable(ContextCompat.getColor(activity, R.color.fondo_app)));
        }
    }

    /* Convierte el texto de un EditText a double aceptando coma o punto decimal. Devuelve 0 si no es un numero valido. */
    public static double ParseDoble(CharSequence texto) {
        if (texto == null) {
            return 0;
        }
        try {
            return Double.parseDouble(texto.toString().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void OcultarTeclado(View v, Activity elActivity) {
        InputMethodManager imm = (InputMethodManager) elActivity.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /*private void MetodoBanner(AdView elAdview, int elElementoAdview) {
        elAdview = findViewById(elElementoAdview);
        AdRequest adRequest = new AdRequest.Builder().build();
        elAdview.loadAd(adRequest);
        elAdview.setAdListener(new AdListener() {
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
    }*/
}
