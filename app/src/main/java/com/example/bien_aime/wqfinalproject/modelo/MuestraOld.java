//package com.example.bien_aime.wqfinalproject.modelo;
//
//import java.util.Date;
//
///**
// * Created by Bien-aime on 8/23/2017.
// */
//
//import java.util.List;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class Muestra {
//
//    @SerializedName("id")
//    @Expose
//    private Integer id;
//    @SerializedName("normativa")
//    @Expose
//    private Normativa normativa;
//    @SerializedName("contenedor")
//    @Expose
//    private Object contenedor;
//    @SerializedName("listadoValores")
//    @Expose
//    private List<ListadoValore> listadoValores = null;
//    @SerializedName("notificaciones")
//    @Expose
//    private List<Notificacione> notificaciones = null;
//    @SerializedName("dispositivo")
//    @Expose
//    private Dispositivo dispositivo;
//    @SerializedName("localizacion")
//    @Expose
//    private Object localizacion;
//    @SerializedName("fechaMuestra")
//    @Expose
//    private String fechaMuestra;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Normativa getNormativa() {
//        return normativa;
//    }
//
//    public void setNormativa(Normativa normativa) {
//        this.normativa = normativa;
//    }
//
//    public Object getContenedor() {
//        return contenedor;
//    }
//
//    public void setContenedor(Object contenedor) {
//        this.contenedor = contenedor;
//    }
//
//    public List<ListadoValore> getListadoValores() {
//        return listadoValores;
//    }
//
//    public void setListadoValores(List<ListadoValore> listadoValores) {
//        this.listadoValores = listadoValores;
//    }
//
//    public List<Notificacione> getNotificaciones() {
//        return notificaciones;
//    }
//
//    public void setNotificaciones(List<Notificacione> notificaciones) {
//        this.notificaciones = notificaciones;
//    }
//
//    public Dispositivo getDispositivo() {
//        return dispositivo;
//    }
//
//    public void setDispositivo(Dispositivo dispositivo) {
//        this.dispositivo = dispositivo;
//    }
//
//    public Object getLocalizacion() {
//        return localizacion;
//    }
//
//    public void setLocalizacion(Object localizacion) {
//        this.localizacion = localizacion;
//    }
//
//    public String getFechaMuestra() {
//        return fechaMuestra;
//    }
//
//    public void setFechaMuestra(String fechaMuestra) {
//        this.fechaMuestra = fechaMuestra;
//    }
//
//}