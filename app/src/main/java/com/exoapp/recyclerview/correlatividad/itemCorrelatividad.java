package com.exoapp.recyclerview.correlatividad;

import android.util.Log;
import android.widget.CheckBox;

public class itemCorrelatividad {
    private String cbMateria;
    private String tvCorrelatividad;

    public itemCorrelatividad(String cbMateria, String tvCorrelatividad){
        this.cbMateria = cbMateria;
        this.tvCorrelatividad = tvCorrelatividad;
        Log.d("tvCorrelatividad", this.tvCorrelatividad);
    }

    public String getcbMateria() {
        return cbMateria;
    }

    public String gettvCorrelatividad() {
        return tvCorrelatividad;
    }
}
