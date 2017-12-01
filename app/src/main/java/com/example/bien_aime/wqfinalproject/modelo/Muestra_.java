package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 9/28/2017.
 */
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Muestra_ implements Serializable
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
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("creadoPor")
    @Expose
    private String creadoPor;
    @SerializedName("fechaMuestra")
    @Expose
    private String fechaMuestra;
    @SerializedName("normativa")
    @Expose
    private Normativa normativa;
    @SerializedName("enviada")
    @Expose
    private Boolean enviada;
    @SerializedName("dispositivo")
    @Expose
    private Dispositivo dispositivo;
    @SerializedName("localizacion")
    @Expose
    private Localizacion_ localizacion;
    @SerializedName("contenedor")
    @Expose
    private Object contenedor;
    @SerializedName("estadoMuestra")
    @Expose
    private EstadoMuestra estadoMuestra;
    @SerializedName("direccion")
    @Expose
    private Direccion direccion;
    @SerializedName("listaNotificaciones")
    @Expose
    private List<ListaNotificacione> listaNotificaciones = null;


    private final static long serialVersionUID = -4926385673316871288L;

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

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getFechaMuestra() {
        return fechaMuestra;
    }

    public void setFechaMuestra(String fechaMuestra) {
        this.fechaMuestra = fechaMuestra;
    }

    public Normativa getNormativa() {
        return normativa;
    }

    public void setNormativa(Normativa normativa) {
        this.normativa = normativa;
    }

    public Boolean getEnviada() {
        return enviada;
    }

    public void setEnviada(Boolean enviada) {
        this.enviada = enviada;
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

    public Object getContenedor() {
        return contenedor;
    }

    public void setContenedor(Object contenedor) {
        this.contenedor = contenedor;
    }

    public EstadoMuestra getEstadoMuestra() {
        return estadoMuestra;
    }

    public void setEstadoMuestra(EstadoMuestra estadoMuestra) {
        this.estadoMuestra = estadoMuestra;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<ListaNotificacione> getListaNotificaciones() {
        return listaNotificaciones;
    }

    public void setListaNotificaciones(List<ListaNotificacione> listaNotificaciones) {
        this.listaNotificaciones = listaNotificaciones;
    }

}