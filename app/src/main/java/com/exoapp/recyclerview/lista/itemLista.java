package com.exoapp.recyclerview.lista;

public class itemLista {
    private int imagen;
    private String titulo;
    private String descripcion;

    public itemLista(int imagen, String titulo, String descripcion){
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
