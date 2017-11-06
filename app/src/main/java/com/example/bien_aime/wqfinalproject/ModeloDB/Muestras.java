package com.example.bien_aime.wqfinalproject.ModeloDB;

/**
 * Created by Bien-aime on 9/6/2017.
 */

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bien-aime on 8/23/2017.
 */

public class Muestras implements Serializable {
    private String picture;
    private String titulo;
    private double cantidad;
    private String fecha;


    public Muestras(String picture, String titulo, double cantidad, String fecha) {
        this.picture = picture;
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

