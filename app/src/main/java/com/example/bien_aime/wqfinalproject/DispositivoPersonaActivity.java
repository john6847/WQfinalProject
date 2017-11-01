package com.example.bien_aime.wqfinalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.adapter.ParameterRecyclerView;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.ListaDispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DispositivoPersonaActivity extends AppCompatActivity  {
    String dispositivoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivo_persona);

        Intent i = getIntent();
        dispositivoName = i.getStringExtra("dispositivo");
        //final TextView textView=(TextView) findViewById(R.id.tvDispositivoPersonal);
        final TextView nameAfterProfileCollapse=(TextView) findViewById(R.id.usernameProfileAfter);
        final TextView descripcionDispo=(TextView) findViewById(R.id.descripcionDispositivo);
        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout1);



        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        final Call<List<Dispositivo>> call = apiService.getDispositivos();

        call.enqueue(new Callback<List<Dispositivo>>() {
            @Override
            public void onResponse(Call<List<Dispositivo>> call, Response<List<Dispositivo>> response) {
                List<Dispositivo> dispositivos= response.body();
                for (Dispositivo dispositivo: dispositivos){
                    if (dispositivo.getNombreDispositivo().equals(dispositivoName)){
                        //textView.setText(dispositivo.getNombreDispositivo());
                        collapsingToolbarLayout.setTitle(dispositivo.getNombreDispositivo());
                        nameAfterProfileCollapse.setText(dispositivo.getNombreDispositivo());
                        descripcionDispo.setText(dispositivo.getDescripcion());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Dispositivo>> call, Throwable t) {

            }
        });


    }


}
