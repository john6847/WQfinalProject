package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 9/28/2017.
 */
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Muestra implements Serializable
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
    @SerializedName("valor")
    @Expose
    private Double valor;
    @SerializedName("muestra")
    @Expose
    private Muestra_ muestra;
    @SerializedName("dateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("parametro")
    @Expose
    private Parametro__ parametro;
    @SerializedName("creadoPor")
    @Expose
    private String creadoPor;
    private final static long serialVersionUID = 3232391496774018485L;

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

    public Parametro__ getParametro() {
        return parametro;
    }

    public void setParametro(Parametro__ parametro) {
        this.parametro = parametro;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

}