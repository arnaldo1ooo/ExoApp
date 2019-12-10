package com.exoapp.recyclerview.rv_listafacu;

public class itemListaFacu {
    private int codigo;
    private int imagen;
    private String facultad;
    private String sigla;
    private int exoneracion;
    private double nota2;
    private double nota3;
    private double nota4;
    private double nota5;


    public itemListaFacu(int codigo, int idimagen, String facultad, String sigla, int exoneracion, double nota2, double nota3, double nota4, double nota5){
        this.codigo = codigo;
        this.imagen = idimagen;
        this.facultad = facultad;
        this.sigla = sigla;
        this.exoneracion = exoneracion;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
        this.nota5 = nota5;
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

    public double getNota2() {
        return nota2;
    }

    public double getNota3() {
        return nota3;
    }

    public double getNota4() {
        return nota4;
    }

    public double getNota5() {
        return nota5;
    }
}
