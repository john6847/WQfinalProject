package com.example.bien_aime.wqfinalproject.modelo;

/**
 * Created by Bien-aime on 9/5/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario_ implements Serializable {

    @SerializedName("_ref")
    @Expose
    private String ref;
    @SerializedName("class")
    @Expose
    private String _class;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }
}