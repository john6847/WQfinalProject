package com.example.bien_aime.wqfinalproject.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bien-aime on 10/31/2017.
 */

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListaDispositivo implements Serializable
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
    @SerializedName("dateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("usuario")
    @Expose
    private Usuario_ usuario;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("dispositivo")
    @Expose
    private Dispositivo dispositivo;
    @SerializedName("creadoPor")
    @Expose
    private String creadoPor;

    public Boolean getUsuarioNotificado() {
        return usuarioNotificado;
    }

    public void setUsuarioNotificado(Boolean usuarioNotificado) {
        this.usuarioNotificado = usuarioNotificado;
    }

    @SerializedName("usuarioNotificado")
    @Expose
    private Boolean usuarioNotificado;

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

    public Usuario_ getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario_ usuario) {
        this.usuario = usuario;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

}
