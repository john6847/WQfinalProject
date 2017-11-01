package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 9/6/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notificacione  implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("statusEnviada")
    @Expose
    private Boolean statusEnviada;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getStatusEnviada() {
        return statusEnviada;
    }

    public void setStatusEnviada(Boolean statusEnviada) {
        this.statusEnviada = statusEnviada;
    }

}