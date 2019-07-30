package com.exoapp.recyclerview.mallafavoritos;

public class itemMallafav {
    private int imagen;
    private String titulo;
    private String descripcion;

    public itemMallafav(int imagen, String titulo, String descripcion){
        this.imagen = imagen;
        this.titulo =titulo;
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
