package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 9/28/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Muestra {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("parametro")
    @Expose
    private Parametro parametro;
    @SerializedName("valor")
    @Expose
    private Double valor;
    @SerializedName("muestra")
    @Expose
    private Muestra_ muestra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Muestra_ getMuestra() {
        return muestra;
    }

    public void setMuestra(Muestra_ muestra) {
        this.muestra = muestra;
    }

}