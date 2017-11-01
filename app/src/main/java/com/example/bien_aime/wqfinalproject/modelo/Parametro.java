package com.example.bien_aime.wqfinalproject.modelo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parametro implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("unidadMedida")
    @Expose
    private Object unidadMedida;
    @SerializedName("nombreParametro")
    @Expose
    private String nombreParametro;
    @SerializedName("listaSensores")
    @Expose
    private List<Object> listaSensores = null;
    @SerializedName("creadoPor")
    @Expose
    private String creadoPor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Object getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(Object unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getNombreParametro() {
        return nombreParametro;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }

    public List<Object> getListaSensores() {
        return listaSensores;
    }

    public void setListaSensores(List<Object> listaSensores) {
        this.listaSensores = listaSensores;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }
}