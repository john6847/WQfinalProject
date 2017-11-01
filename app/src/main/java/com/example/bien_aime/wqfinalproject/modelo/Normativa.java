package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 9/6/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Normativa {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("nombreNormativa")
    @Expose
    private String nombreNormativa;
    @SerializedName("listaParametros")
    @Expose
    private List<ListaParametro> listaParametros = null;
    @SerializedName("fechaCreacion")
    @Expose
    private String fechaCreacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreNormativa() {
        return nombreNormativa;
    }

    public void setNombreNormativa(String nombreNormativa) {
        this.nombreNormativa = nombreNormativa;
    }

    public List<ListaParametro> getListaParametros() {
        return listaParametros;
    }

    public void setListaParametros(List<ListaParametro> listaParametros) {
        this.listaParametros = listaParametros;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
