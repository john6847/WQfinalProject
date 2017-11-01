package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 10/31/2017.
 */

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListaParametro_ implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nivelMinimo")
    @Expose
    private Double nivelMinimo;
    @SerializedName("modificadoPor")
    @Expose
    private String modificadoPor;
    @SerializedName("habilitado")
    @Expose
    private Boolean habilitado;
    @SerializedName("dateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("nivelMaximo")
    @Expose
    private Double nivelMaximo;
    @SerializedName("parametro")
    @Expose
    private Parametro_ parametro;
    @SerializedName("creadoPor")
    @Expose
    private String creadoPor;
    private final static long serialVersionUID = -2384204313682557671L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNivelMinimo() {
        return nivelMinimo;
    }

    public void setNivelMinimo(Double nivelMinimo) {
        this.nivelMinimo = nivelMinimo;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Double getNivelMaximo() {
        return nivelMaximo;
    }

    public void setNivelMaximo(Double nivelMaximo) {
        this.nivelMaximo = nivelMaximo;
    }

    public Parametro_ getParametro() {
        return parametro;
    }

    public void setParametro(Parametro_ parametro) {
        this.parametro = parametro;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

}