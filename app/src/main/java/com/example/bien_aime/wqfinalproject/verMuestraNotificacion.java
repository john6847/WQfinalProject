package com.example.bien_aime.wqfinalproject;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class verMuestraNotificacion extends AppCompatActivity {

//    Muestra muestraLLegando;
    List<Muestra> muestrasLLegando;
    HashMap<String,Muestras> muestraLis = new HashMap<>();
//    HashMap<String, Muestras> mapaMuestra = new HashMap<String, Muestras>();
    List<Muestras> mapaMuestra = new ArrayList<>();
    Usuario usuario;
    Dispositivo dispositivo;
    String usuarioId;
    String dispositivoId;
    Double phMin=6.5;
    Double phMax=8.5;
    Double turbiedadMin=0.0;
    Double turbiedadMax=5.0;
    Double conductividadMin=50.0;
    Double conductividadMax=1500.0;
    Double temperaturaMin=5.0;
    Double getTemperaturaMax=25.0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_muestra_notificacion);

        Intent intent=getIntent();
        usuarioId=intent.getStringExtra("usuarioId");
        dispositivoId= intent.getStringExtra("idDispositivo");

        Intent i = getIntent();
        muestrasLLegando= (List<Muestra>) i.getSerializableExtra("muestras");


        for(Muestra muestra:muestrasLLegando){
            System.out.println("Hay "+muestra.getValor());
        }

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println("Tratando de mandarle a false.");
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://manueltm24.me:8080/API/notificarUsuarioDispositivo/");
                        String json = "{"+"idUsuario:" + usuarioId + ",idDispositivo:" + dispositivoId + ",notificacion:" + "false" + "}";

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
                            HttpResponse response = httpClient.execute(httpPost);
                            System.out.println("Responseee" + response.getStatusLine().getStatusCode());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
//        SharedPreferences.Editor editor =  getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
//        editor.clear();
        editor.putBoolean("leido", true);
        editor.apply();

        System.out.println("El valor es: "+prefs.getBoolean("leido",false));
        Button btnLocalizacion=(Button) findViewById(R.id.load_direction);
        Button btnFullMuestra=(Button) findViewById(R.id.load_fullMuestra);
        TextView mensaje=(TextView) findViewById(R.id.mensaje);

        mensaje.setBackgroundColor(getColor(R.color.backgroundCardView));
        mensaje.setElegantTextHeight(true);
        mensaje.setText("Los siguientes parametros se encuentran fuera de su rango.\nFavor comunicarse con Water Quality Monitoring lo mas pronto posible(829-580-6847).");

        //android:text="[6.5 - 8.5]"
        show_toolbar("Notificacion", false);

        ApiService apiService=ApiService.retrofit.create(ApiService.class);
        final retrofit2.Call<List<Muestra>> call= apiService.getValores();


        btnLocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Dispositivo: "+muestrasLLegando.get(1).getMuestra().getDireccion().getLocalizacion().getLatitud());
//                System.out.println("Dispositivo: "+muestrasLLegando.get(0).getMuestra().getLocalizacion().getLatitud());
//                System.out.println("Dispositivo: "+muestrasLLegando.get(2).getMuestra().getLocalizacion().getLatitud());
//                System.out.println("Dispositivo: "+muestraLLegando.getMuestra().getLocalizacion().getLongitud());

                String uri = "http://maps.google.com/maps?daddr=" + muestrasLLegando.get(0).getMuestra().getDireccion().getLocalizacion().getLatitud().toString()+","+ muestrasLLegando.get(0).getMuestra().getDireccion().getLocalizacion().getLongitud().toString();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });


        for(Muestra muestra:muestrasLLegando){
            System.out.println("Muestra : "+muestra.getValor());
            muestraLis.put(String.valueOf(muestra.getId()),new Muestras("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png", muestra.getParametro().getNombreParametro(),muestra.getValor(), muestra.getMuestra().getFechaMuestra()," "));
            if(muestra.getParametro().getNombreParametro().equalsIgnoreCase("Ph")){
                if(muestra.getValor()<phMin|| muestra.getValor()>phMax){
                    System.out.println("Mala ph");
                    mapaMuestra.add(new Muestras("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png", muestra.getParametro().getNombreParametro(),muestra.getValor(), muestra.getMuestra().getFechaMuestra(),"Nivel minimo:"+phMin+"\nNivel maximo: "+phMax));
                }
            }

            else if(muestra.getParametro().getNombreParametro().equalsIgnoreCase("Turbiedad")){
                if(muestra.getValor()<turbiedadMin|| muestra.getValor()>turbiedadMax){
                    System.out.println("Mala turb");
                    mapaMuestra.add(new Muestras("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png", muestra.getParametro().getNombreParametro(),muestra.getValor(), muestra.getMuestra().getFechaMuestra(),"Nivel minimo:"+turbiedadMin+"\nNivel maximo: "+turbiedadMax));
                }
            }
            else if(muestra.getParametro().getNombreParametro().equalsIgnoreCase("Conductividad")){
                if(muestra.getValor()<conductividadMin|| muestra.getValor()>conductividadMax){
                    System.out.println("Mala conduc");
                    mapaMuestra.add(new Muestras("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png", muestra.getParametro().getNombreParametro(),muestra.getValor(), muestra.getMuestra().getFechaMuestra(),"Nivel minimo:"+conductividadMin+"\nNivel maximo: "+conductividadMax));
                }
            }
            else if(muestra.getParametro().getNombreParametro().equalsIgnoreCase("Temperatura")){
                if(muestra.getValor()<temperaturaMin|| muestra.getValor()>getTemperaturaMax){
                    System.out.println("Mala tmp");
                    mapaMuestra.add(new Muestras("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png", muestra.getParametro().getNombreParametro(),muestra.getValor(), muestra.getMuestra().getFechaMuestra(),"Nivel minimo:"+temperaturaMin+"\nNivel maximo: "+getTemperaturaMax));
                }
            }
        }

        btnFullMuestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(verMuestraNotificacion.this, verFullMuestra.class);

                intent.putExtra("muestras", (Serializable) new ArrayList<>(muestraLis.values()));

                startActivity(intent);
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pictureRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

//        ParameterRecyclerView parameterRecycler = new ParameterRecyclerView(new ArrayList<>(muestraLis.values()), verMuestraNotificacion.this);
        ParameterRecyclerView parameterRecycler = new ParameterRecyclerView(mapaMuestra, verMuestraNotificacion.this);
        recyclerView.setAdapter(parameterRecycler);
        recyclerView.setLayoutManager(linearLayoutManager);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(1);

        /*call.enqueue(new Callback<List<Muestra>>() {
            @Override
            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
                List<Muestra> muestras=response.body();

                for (Muestra muestra: muestras){
                    if(Objects.equals(muestra.getId(), muestrasLLegando.get(0).getId())){
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

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(1);

            }

            @Override
            public void onFailure(Call<List<Muestra>> call, Throwable t) {

            }
        });*/

//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try  {
//                    //Your code goes here
//                    HttpClient httpClient=new DefaultHttpClient();
////                    HttpPost httpPost=new HttpPost("http://manueltm24.me:8080/API/ActivateNotification/");
//                    HttpPost httpPost=new HttpPost("http://manueltm24.me:8080/API/activarNotificacion/");
//                    //Hay que pensar en cambiar esto porque podria haber mas de una notificacion
//                    String json="{"+"id:"+muestraLLegando.getId() +",notificada:"+"true"+"}";
//
//                    StringEntity entity = null;
//                    try {
//                        entity = new StringEntity(json);
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    httpPost.setEntity(entity);
//                    httpPost.setHeader("Accept", "application/json");
//                    httpPost.setHeader("Content-type", "application/json");
//
//                    try {
//                        HttpResponse response=httpClient.execute(httpPost);
//                        System.out.println("Responseee"+response.getStatusLine().getStatusCode());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//        System.out.println("Muestras        "+muestraLLegando);
//        Button btnLocalizacion=(Button) findViewById(R.id.load_direction);
//
//        show_toolbar("Muestra Notificada", false);
//
//        ApiService apiService=ApiService.retrofit.create(ApiService.class);
//        final retrofit2.Call<List<Muestra>> call= apiService.getValores();
//
//        btnLocalizacion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                System.out.println("Dispositivo: "+muestraLLegando.getMuestra().getLocalizacion().getLatitud());
//                System.out.println("Dispositivo: "+muestraLLegando.getMuestra().getLocalizacion().getLongitud());
//
//                String uri = "http://maps.google.com/maps?daddr=" + muestraLLegando.getMuestra().getLocalizacion().getLatitud().toString()+","+muestraLLegando.getMuestra().getLocalizacion().getLongitud().toString();
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
//                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                startActivity(intent);
//            }
//        });
//
//        call.enqueue(new Callback<List<Muestra>>() {
//            @Override
//            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
//                List<Muestra> muestras=response.body();
//
//                for (Muestra muestra: muestras){
//                    if(Objects.equals(muestra.getId(), muestraLLegando.getId())){
//                        muestraLis.put(String.valueOf(muestra.getId()),new Muestras("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png", muestra.getParametro().getNombreParametro(),muestra.getValor(), muestra.getMuestra().getFechaMuestra()));
//                    }
//                }
//
//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pictureRecycler);
//                recyclerView.setHasFixedSize(true);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//                ParameterRecyclerView parameterRecycler = new ParameterRecyclerView(new ArrayList<>(muestraLis.values()), verMuestraNotificacion.this);
//                recyclerView.setAdapter(parameterRecycler);
//                recyclerView.setLayoutManager(linearLayoutManager);
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Muestra>> call, Throwable t) {
//
//            }
//        });
    }

    public void show_toolbar(String titulo, boolean upBoton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor((getResources().getColor(R.color.toolbarTextColor)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBoton);
    }
}
