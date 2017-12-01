//package com.example.bien_aime.wqfinalproject.modelo;
//
//import com.google.gson.annotations.SerializedName;
//import java.io.Serializable;
//import java.util.List;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.util.List;

/**
 * Created by Bien-aime on 9/5/2017.
// */
//import java.io.Serializable;
//import java.util.List;
//import com.google.gson.annotations.Expose;
//
//public class Usuario implements Serializable {
//
//    @SerializedName("id")
//    @Expose
//    private Integer id;
//    @SerializedName("modificadoPor")
//    @Expose
//    private String modificadoPor;
//    @SerializedName("habilitado")
//    @Expose
//    private Boolean habilitado;
//    @SerializedName("dateCreated")
//    @Expose
//    private String dateCreated;
//    @SerializedName("passwordExpired")
//    @Expose
//    private Boolean passwordExpired;
//    @SerializedName("lastUpdated")
//    @Expose
//    private String lastUpdated;
//    @SerializedName("apellido")
//    @Expose
//    private String apellido;
//    @SerializedName("accountExpired")
//    @Expose
//    private Boolean accountExpired;
//    @SerializedName("creadoPor")
//    @Expose
//    private String creadoPor;
//    @SerializedName("listaDispositivos")
//    @Expose
//    private List<ListaDispositivo> listaDispositivos = null;
//    @SerializedName("username")
//    @Expose
//    private String username;
//    @SerializedName("accountLocked")
//    @Expose
//    private Boolean accountLocked;
//    @SerializedName("telefono")
//    @Expose
//    private String telefono;
//    @SerializedName("password")
//    @Expose
//    private String password;
//    @SerializedName("nombre")
//    @Expose
//    private String nombre;
//    @SerializedName("enabled")
//    @Expose
//    private Boolean enabled;
//    @SerializedName("direccion")
//    @Expose
//    private Direccion direccion;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getModificadoPor() {
//        return modificadoPor;
//    }
//
//    public void setModificadoPor(String modificadoPor) {
//        this.modificadoPor = modificadoPor;
//    }
//
//    public Boolean getHabilitado() {
//        return habilitado;
//    }
//
//    public void setHabilitado(Boolean habilitado) {
//        this.habilitado = habilitado;
//    }
//
//    public String getDateCreated() {
//        return dateCreated;
//    }
//
//    public void setDateCreated(String dateCreated) {
//        this.dateCreated = dateCreated;
//    }
//
//    public Boolean getPasswordExpired() {
//        return passwordExpired;
//    }
//
//    public void setPasswordExpired(Boolean passwordExpired) {
//        this.passwordExpired = passwordExpired;
//    }
//
//    public String getLastUpdated() {
//        return lastUpdated;
//    }
//
//    public void setLastUpdated(String lastUpdated) {
//        this.lastUpdated = lastUpdated;
//    }
//
//    public String getApellido() {
//        return apellido;
//    }
//
//    public void setApellido(String apellido) {
//        this.apellido = apellido;
//    }
//
//    public Boolean getAccountExpired() {
//        return accountExpired;
//    }
//
//    public void setAccountExpired(Boolean accountExpired) {
//        this.accountExpired = accountExpired;
//    }
//
//    public String getCreadoPor() {
//        return creadoPor;
//    }
//
//    public void setCreadoPor(String creadoPor) {
//        this.creadoPor = creadoPor;
//    }
//
//    public List<ListaDispositivo> getListaDispositivos() {
//        return listaDispositivos;
//    }
//
//    public void setListaDispositivos(List<ListaDispositivo> listaDispositivos) {
//        this.listaDispositivos = listaDispositivos;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public Boolean getAccountLocked() {
//        return accountLocked;
//    }
//
//    public void setAccountLocked(Boolean accountLocked) {
//        this.accountLocked = accountLocked;
//    }
//
//    public String getTelefono() {
//        return telefono;
//    }
//
//    public void setTelefono(String telefono) {
//        this.telefono = telefono;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public Boolean getEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(Boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public Direccion getDireccion() {
//        return direccion;
//    }
//
//    public void setDireccion(Direccion direccion) {
//        this.direccion = direccion;
//    }


//import java.io.Serializable;
//import java.util.List;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;

package com.example.bien_aime.wqfinalproject.modelo;

import java.io.Serializable;
import java.util.List;

import com.example.bien_aime.wqfinalproject.modelo.ListaDispositivo;
import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Usuario implements Serializable
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
    @SerializedName("passwordExpired")
    @Expose
    private Boolean passwordExpired;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("accountExpired")
    @Expose
    private Boolean accountExpired;
    @SerializedName("creadoPor")
    @Expose
    private String creadoPor;
    @SerializedName("listaDispositivos")
    @Expose
    private List<ListaDispositivo> listaDispositivos;

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("accountLocked")
    @Expose
    private Boolean accountLocked;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("direccion")
    @Expose
    private Direccion direccion;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SerializedName("email")
    @Expose
    private String email;

    public Boolean getSilenciarNotificacion() {
        return silenciarNotificacion;
    }

    public void setSilenciarNotificacion(Boolean silenciarNotificacion) {
        this.silenciarNotificacion = silenciarNotificacion;
    }

    @SerializedName("silenciarNotificacion")
    @Expose
    private Boolean silenciarNotificacion;

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

    public Boolean getPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public List<ListaDispositivo> getListaDispositivos() {
        return listaDispositivos;
    }

    public void setListaDispositivos(List<ListaDispositivo> listaDispositivos) {
        this.listaDispositivos = listaDispositivos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

}