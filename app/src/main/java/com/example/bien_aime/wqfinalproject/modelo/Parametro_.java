package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 9/6/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parametro_ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombreParametro")
    @Expose
    private String nombreParametro;
    @SerializedName("sensor")
    @Expose
    private List<Object> sensor = null;
    @SerializedName("unidadMedida")
    @Expose
    private String unidadMedida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreParametro() {
        return nombreParametro;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }

    public List<Object> getSensor() {
        return sensor;
    }

    public void setSensor(List<Object> sensor) {
        this.sensor = sensor;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

}