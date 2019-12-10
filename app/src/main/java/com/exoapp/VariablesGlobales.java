package com.exoapp;

import android.app.Application;

class VariablesGlobales extends Application {
    private String idFacuSelect;

    public String getIdFacuSelect() {
        return idFacuSelect;
    }

    public void setIdFacuSelect(String str) {
        idFacuSelect = str;
    }
}