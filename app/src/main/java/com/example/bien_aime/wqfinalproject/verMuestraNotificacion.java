package com.example.bien_aime.wqfinalproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.ModeloDB.Muestras;
import com.example.bien_aime.wqfinalproject.adapter.ParameterRecyclerView;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class verMuestraNotificacion extends AppCompatActivity {

    Muestra muestraLLegando;
    HashMap<String,Muestras> muestraLis = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_muestra_notificacion);

        Intent i = getIntent();

        muestraLLegando= (Muestra) i.getSerializableExtra("muestras");

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    //Your code goes here
                    HttpClient httpClient=new DefaultHttpClient();
//                    HttpPost httpPost=new HttpPost("http://manueltm24.me:8080/API/ActivateNotification/");
                    HttpPost httpPost=new HttpPost("http://manueltm24.me:8080/API/activarNotificacion/");
                    //Hay que pensar en cambiar esto porque podria haber mas de una notificacion
                    String json="{"+"id:"+muestraLLegando.getId() +",notificada:"+"true"+"}";

                    StringEntity entity = null;
                    try {
                        entity = new StringEntity(json);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    httpPost.setEntity(entity);
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");

                    try {
                        HttpResponse response=httpClient.execute(httpPost);
                        System.out.println("Responseee"+response.getStatusLine().getStatusCode());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println("Muestras        "+muestraLLegando);
        Button btnLocalizacion=(Button) findViewById(R.id.load_direction);

        show_toolbar("Muestra Notificada", false);

        ApiService apiService=ApiService.retrofit.create(ApiService.class);
        final retrofit2.Call<List<Muestra>> call= apiService.getValores();

        btnLocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Dispositivo: "+muestraLLegando.getMuestra().getLocalizacion().getLatitud());
                System.out.println("Dispositivo: "+muestraLLegando.getMuestra().getLocalizacion().getLongitud());

                String uri = "http://maps.google.com/maps?daddr=" + muestraLLegando.getMuestra().getLocalizacion().getLatitud().toString()+","+muestraLLegando.getMuestra().getLocalizacion().getLongitud().toString();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

        call.enqueue(new Callback<List<Muestra>>() {
            @Override
            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
                List<Muestra> muestras=response.body();

                for (Muestra muestra: muestras){
                    if(Objects.equals(muestra.getId(), muestraLLegando.getId())){
                        muestraLis.put(String.valueOf(muestra.getId()),new Muestras("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png", muestra.getParametro().getNombreParametro(),muestra.getValor(), muestra.getMuestra().getFechaMuestra()));
                    }
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pictureRecycler);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                ParameterRecyclerView parameterRecycler = new ParameterRecyclerView(new ArrayList<>(muestraLis.values()), verMuestraNotificacion.this);
                recyclerView.setAdapter(parameterRecycler);
                recyclerView.setLayoutManager(linearLayoutManager);

            }

            @Override
            public void onFailure(Call<List<Muestra>> call, Throwable t) {

            }
        });
    }

    public void show_toolbar(String titulo, boolean upBoton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor((getResources().getColor(R.color.toolbarTextColor)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBoton);
    }
}
