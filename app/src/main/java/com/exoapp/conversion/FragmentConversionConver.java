package com.exoapp.conversion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exoapp.R;
import com.exoapp.utilidades.Metodos;

import java.text.DecimalFormat;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentConversionConver#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConversionConver extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String selectRadioButton;
    private View vistaFragment;
    private TextView tvTituloTotalConver, tvTotalConverUnidad, tvTituloValorObte, tvValorObteUnidad, tvResultadoConver;
    private EditText etTotalValor, etValorObte;
    private Button btnOk;
    private Metodos metodos = new Metodos();
    private DecimalFormat df = new DecimalFormat("#.##");

    public FragmentConversionConver() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentConversionConver newInstance(String param1, String param2) {
        FragmentConversionConver fragment = new FragmentConversionConver();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            selectRadioButton = getArguments().getString("radiobuttonselect");

            getArguments().clear();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vistaFragment = inflater.inflate(R.layout.fragment_conversion_conver, container, false); // Inflar el layout para este Fragmento

        tvTituloTotalConver = vistaFragment.findViewById(R.id.tvTituloTotalConver);
        tvTituloValorObte = vistaFragment.findViewById(R.id.tvTituloValorObte);
        tvResultadoConver = vistaFragment.findViewById(R.id.tvResultadoConver);
        etTotalValor = vistaFragment.findViewById(R.id.etTotalValor);
        tvTotalConverUnidad = vistaFragment.findViewById(R.id.tvTotalValorUnidad);
        tvValorObteUnidad = vistaFragment.findViewById(R.id.tvValorObteUnidad);
        etValorObte = vistaFragment.findViewById(R.id.etValorObte);
        btnOk = vistaFragment.findViewById(R.id.btnOk);


        if (selectRadioButton.equals("puntoaporc")) {
            tvTituloTotalConver.setText("¿De cuantos puntos es el examen?");
            tvTotalConverUnidad.setText("Puntos");
            tvTituloValorObte.setText("¿Cuantos puntos hiciste?");
            tvValorObteUnidad.setText("Puntos");
        }

        if (selectRadioButton.equals("porcapunto")) {
            tvTituloTotalConver.setText("¿De cuantos puntos es el examen?");
            tvTotalConverUnidad.setText("Puntos");
            tvTituloValorObte.setText("¿Cuantos porciento hiciste?");
            tvValorObteUnidad.setText("%");
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Convertir(v);
            }
        });

        //Al dar enter
        etValorObte.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    btnOk.callOnClick();
                    return true;
                }
                return false;
            }
        });

        return vistaFragment;
    }

    public void Convertir(View v) {
        if (ComprobarCampos(v) == true) {
            Double totalValor = Double.parseDouble(etTotalValor.getText().toString());
            Double valorObte = Double.parseDouble(etValorObte.getText().toString());
            Double totalPorc = 100.0;
            Double conversion;

            if (selectRadioButton.equals("puntoaporc")) {
                conversion = (valorObte * totalPorc) / totalValor;
                if (conversion.isNaN()) {
                    conversion = 0.0;
                }
                tvResultadoConver.setText("Hiciste " + df.format(conversion) + "% de " + df.format(totalPorc) + "%");
            }

            if (selectRadioButton.equals("porcapunto")) {
                conversion = (valorObte * totalValor) / totalPorc;
                tvResultadoConver.setText("Hiciste " + df.format(conversion) + " Puntos de " + df.format(totalValor) + " Puntos");
            }

            metodos.OcultarTeclado(v, getActivity());
            tvResultadoConver.setVisibility(View.VISIBLE);
        }
    }

    private boolean ComprobarCampos(View v) {
        if (etTotalValor.getText().toString().equals("")) {
            etTotalValor.setText("0");
        }

        if (etValorObte.getText().toString().equals("")) {
            etValorObte.setText("0");
        }
        Double totalValor = Double.parseDouble(etTotalValor.getText().toString());
        Double valorObte = Double.parseDouble(etValorObte.getText().toString());
        Double totalPorc = 100.0;

        if (totalPorc < valorObte && tvValorObteUnidad.getText().toString().equals("%")) {
            Toast.makeText(getActivity(), "El Porcentaje obtenido no puede ser mayor al Porcentaje total del examen (" + df.format(totalPorc) + "%)", Toast.LENGTH_LONG).show();
            metodos.OcultarTeclado(v, getActivity());
            tvResultadoConver.setVisibility(View.INVISIBLE);
            etValorObte.requestFocus();
            return false;
        }

        if (totalValor < valorObte && tvValorObteUnidad.getText().toString().equals("Puntos")) {
            Toast.makeText(getActivity(), "El Puntaje obtenido no puede ser mayor al total de Puntos del examen (" + df.format(totalValor) + " Puntos)", Toast.LENGTH_LONG).show();
            metodos.OcultarTeclado(v, getActivity());
            tvResultadoConver.setVisibility(View.INVISIBLE);
            etValorObte.requestFocus();
            return false;
        }
        return true;
    }
}