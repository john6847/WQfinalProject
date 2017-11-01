package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 9/28/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Muestra_ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("normativa")
    @Expose
    private Normativa normativa;
    @SerializedName("contenedor")
    @Expose
    private Object contenedor;
    @SerializedName("notificaciones")
    @Expose
    private List<Notificacione> notificaciones = null;
    @SerializedName("dispositivo")
    @Expose
    private Dispositivo dispositivo;
    @SerializedName("localizacion")
    @Expose
    private Localizacion_ localizacion;
    @SerializedName("fechaMuestra")
    @Expose
    private String fechaMuestra;
    @SerializedName("estado")
    @Expose
    private String estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Normativa getNormativa() {
        return normativa;
    }

    public void setNormativa(Normativa normativa) {
        this.normativa = normativa;
    }

    public Object getContenedor() {
        return contenedor;
    }

    public void setContenedor(Object contenedor) {
        this.contenedor = contenedor;
    }

    public List<Notificacione> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacione> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Localizacion_ getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(Localizacion_ localizacion) {
        this.localizacion = localizacion;
    }

    public String getFechaMuestra() {
        return fechaMuestra;
    }

    public void setFechaMuestra(String fechaMuestra) {
        this.fechaMuestra = fechaMuestra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
