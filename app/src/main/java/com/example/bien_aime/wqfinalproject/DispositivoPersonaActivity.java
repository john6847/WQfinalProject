package com.example.bien_aime.wqfinalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.adapter.ParameterRecyclerView;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.ListaDispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DispositivoPersonaActivity extends AppCompatActivity  {
    String dispositivoName;
    Dispositivo dispositivoFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivo_persona);

        Intent i = getIntent();
        dispositivoName = i.getStringExtra("dispositivo");

        //final TextView textView=(TextView) findViewById(R.id.tvDispositivoPersonal);
        final TextView nameAfterProfileCollapse=(TextView) findViewById(R.id.usernameProfileAfter);
        final TextView descripcionDispo=(TextView) findViewById(R.id.tvDescripcion);
        final TextView nombreDispo=(TextView) findViewById(R.id.tvNombreDispositivo);
        final TextView fechaCreacionDispo=(TextView) findViewById(R.id.tvFechaCreacion);
        final TextView normativaAsignadaDispo=(TextView) findViewById(R.id.tvNormativaAsignada);
        final TextView sector=(TextView) findViewById(R.id.tvSector);
        final TextView ciudad=(TextView) findViewById(R.id.tvCiudad);
        final TextView pais=(TextView) findViewById(R.id.tvPais);
        final TextView calle=(TextView) findViewById(R.id.tvCalle);
        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout1);

        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        final Call<List<Dispositivo>> call = apiService.getDispositivos();

        call.enqueue(new Callback<List<Dispositivo>>() {
            @Override
            public void onResponse(Call<List<Dispositivo>> call, Response<List<Dispositivo>> response) {
                List<Dispositivo> dispositivos= response.body();
                for (Dispositivo dispositivo: dispositivos){
                    if (dispositivo.getNombreDispositivo().equals(dispositivoName)){

                        collapsingToolbarLayout.setTitle(dispositivo.getNombreDispositivo());
                        nameAfterProfileCollapse.setText(dispositivo.getNombreDispositivo());
                        if(dispositivo.getDescripcion()!=null){
                            descripcionDispo.setText(dispositivo.getDescripcion());
                        }

                        if(dispositivo.getDireccion()!=null){
                            if (dispositivo.getDireccion().getSector() != null) {
                                sector.setText(dispositivo.getDireccion().getSector().getNombreSector());
                                ciudad.setText(dispositivo.getDireccion().getSector().getCiudad().getNombreCiudad());
                                pais.setText(dispositivo.getDireccion().getSector().getCiudad().getPais().getNombrePais());
                            }
                            if (dispositivo.getDireccion().getCalle() != null) {
                                calle.setText(dispositivo.getDireccion().getCalle());
                            }
                        }

                        nombreDispo.setText(dispositivo.getNombreDispositivo());
                        fechaCreacionDispo.setText(dispositivo.getDateCreated().replace("Z"," ").replace("T"," "));
//                        fechaCreacionDispo.setText(dispositivo.getDateCreated());
                        normativaAsignadaDispo.setText(dispositivo.getNormativaAsignada().getNombreNormativa());
                        dispositivoFinal=dispositivo;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Dispositivo>> call, Throwable t) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.editDispositivofab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(DispositivoPersonaActivity.this,EditDispositivoActivity.class);
                intent.putExtra("dispositivo",(Serializable)dispositivoFinal);
                startActivity(intent);
            }
        });
    }
}
