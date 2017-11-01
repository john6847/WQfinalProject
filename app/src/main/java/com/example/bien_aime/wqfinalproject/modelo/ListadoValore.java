package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 9/6/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListadoValore implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("parametro")
    @Expose
    private Parametro_ parametro;
    @SerializedName("valor")
    @Expose
    private Double valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Parametro_ getParametro() {
        return parametro;
    }

    public void setParametro(Parametro_ parametro) {
        this.parametro = parametro;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}