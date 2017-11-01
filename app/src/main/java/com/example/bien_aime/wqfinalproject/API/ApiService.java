package com.example.bien_aime.wqfinalproject.API;

import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.ListaDispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.example.bien_aime.wqfinalproject.modelo.Muestra_;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Bien-aime on 9/5/2017.
 */

public interface  ApiService {
    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("API/usuarios")
    //public void getUsuarios(Callback<List<Usuario>> response);
    Call<List<Usuario>> getUsuarios();
    //Call<Usuario> getUsuarios();


    @GET("API/muestras")
        //public void getUsuarios(Callback<List<Usuario>> response);
    Call<Muestra_> getMuestra();

    @GET("API/dispositivos")
    Call<List<Dispositivo>> getDispositivos();

    @GET("API/valores")
        //public void getUsuarios(Callback<List<Usuario>> response);
    Call<List<Muestra>> getValores();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://manueltm24.me:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
