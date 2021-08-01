package com.exoapp.conversion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.exoapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentConversionSelectConve#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConversionSelectConve extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View vistaFragment;
    private RadioButton rbPorcAPunto, rbPuntoAPorc;

    public FragmentConversionSelectConve() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentConversionSelectConve newInstance(String param1, String param2) {
        FragmentConversionSelectConve fragment = new FragmentConversionSelectConve();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vistaFragment = inflater.inflate(R.layout.fragment_conversion_select_conve, container, false); // Inflar el layout para este Fragmento
        rbPorcAPunto = vistaFragment.findViewById(R.id.rbPorcAPunto);
        rbPuntoAPorc = vistaFragment.findViewById(R.id.rbPuntoAPorc);


        rbPorcAPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbPorcAPunto.isChecked()) {
                    rbPuntoAPorc.setChecked(false);
                } else {
                    rbPuntoAPorc.setChecked(true);
                }
            }
        });

        rbPuntoAPorc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rbPuntoAPorc.isChecked()) {
                    rbPorcAPunto.setChecked(false);
                } else {
                    rbPorcAPunto.setChecked(true);
                }
            }
        });


        return vistaFragment;
    }

    public RadioButton getRbPuntoAPorc() { //Para que se pueda acceder desde el activity
        return rbPuntoAPorc;
    }

    public RadioButton getRbPorcAPunto() { //Para que se pueda acceder desde el activity
        return rbPorcAPunto;
    }
}