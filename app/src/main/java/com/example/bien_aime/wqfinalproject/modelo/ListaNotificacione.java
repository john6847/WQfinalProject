package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 10/31/2017.
 */

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListaNotificacione implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("modificadoPor")
    @Expose
    private String modificadoPor;
    @SerializedName("habilitado")
    @Expose
    private Boolean habilitado;
    @SerializedName("statusEnviada")
    @Expose
    private Boolean statusEnviada;
    @SerializedName("dateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("quiereRecibirNotificacion")
    @Expose
    private Boolean quiereRecibirNotificacion;
    @SerializedName("creadoPor")
    @Expose
    private String creadoPor;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    private final static long serialVersionUID = -7524851850437805546L;

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

    public Boolean getStatusEnviada() {
        return statusEnviada;
    }

    public void setStatusEnviada(Boolean statusEnviada) {
        this.statusEnviada = statusEnviada;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getQuiereRecibirNotificacion() {
        return quiereRecibirNotificacion;
    }

    public void setQuiereRecibirNotificacion(Boolean quiereRecibirNotificacion) {
        this.quiereRecibirNotificacion = quiereRecibirNotificacion;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
