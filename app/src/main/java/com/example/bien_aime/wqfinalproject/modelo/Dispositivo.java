package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 8/23/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Dispositivo implements Serializable{

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
    @SerializedName("localizacion")
    @Expose
    private Localizacion_ localizacion;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("creadoPor")
    @Expose
    private String creadoPor;
    @SerializedName("nombreDispositivo")
    @Expose
    private String nombreDispositivo;
    @SerializedName("normativaAsignada")
    @Expose
    private NormativaAsignada normativaAsignada;
    @SerializedName("direccion")
    @Expose
    private Direccion direccion;

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

    public Localizacion_ getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(Localizacion_ localizacion) {
        this.localizacion = localizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getNombreDispositivo() {
        return nombreDispositivo;
    }

    public void setNombreDispositivo(String nombreDispositivo) {
        this.nombreDispositivo = nombreDispositivo;
    }

    public NormativaAsignada getNormativaAsignada() {
        return normativaAsignada;
    }

    public void setNormativaAsignada(NormativaAsignada normativaAsignada) {
        this.normativaAsignada = normativaAsignada;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Dispositivo(Integer id, String nombreDispositivo, String descripcion,Localizacion_ localizacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombreDispositivo = nombreDispositivo;
        this.localizacion = localizacion;
        this.nombreDispositivo = nombreDispositivo;
    }
}