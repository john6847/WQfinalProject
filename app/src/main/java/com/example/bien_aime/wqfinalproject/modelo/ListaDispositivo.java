package com.example.bien_aime.wqfinalproject.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bien-aime on 10/31/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListaDispositivo {

    @SerializedName("dispositivo")
    @Expose
    private Dispositivo dispositivo;


    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

}