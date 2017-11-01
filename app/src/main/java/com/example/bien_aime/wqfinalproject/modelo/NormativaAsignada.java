package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 10/31/2017.
 */

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NormativaAsignada implements Serializable {

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
    @SerializedName("listaParametros")
    @Expose
    private List<ListaParametro> listaParametros = null;
    @SerializedName("nombreNormativa")
    @Expose
    private String nombreNormativa;
    @SerializedName("creadoPor")
    @Expose
    private String creadoPor;
    @SerializedName("fechaCreacion")
    @Expose
    private String fechaCreacion;

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

    public List<ListaParametro> getListaParametros() {
        return listaParametros;
    }

    public void setListaParametros(List<ListaParametro> listaParametros) {
        this.listaParametros = listaParametros;
    }

    public String getNombreNormativa() {
        return nombreNormativa;
    }

    public void setNombreNormativa(String nombreNormativa) {
        this.nombreNormativa = nombreNormativa;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
