package com.exoapp.recyclerview.rv_listafacu;

public class itemListaFacu {
    private int codigo;
    private int imagen;
    private String facultad;
    private String sigla;
    private int exoneracion;


    public itemListaFacu(int codigo, int idimagen, String facultad, String sigla, int exoneracion){
        this.codigo = codigo;
        this.imagen = idimagen;
        this.facultad = facultad;
        this.sigla = sigla;
        this.exoneracion = exoneracion;
    }


    public int getCodigo() {
        return codigo;
    }

    public int getImagen() {
        return imagen;
    }

    public String getFacultad() {
        return facultad;
    }

    public String getSigla() {
        return sigla;
    }

    public int getExoneracion() {
        return exoneracion;
    }
}
