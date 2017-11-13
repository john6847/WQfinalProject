package com.example.bien_aime.wqfinalproject.Servicios;


import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import com.example.bien_aime.wqfinalproject.API.ApiService;

import com.example.bien_aime.wqfinalproject.MuestrasContentProvider;

import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;
import com.example.bien_aime.wqfinalproject.verMuestraNotificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceMonitoreo extends Service {
    private Timer timer = new Timer();

    static final Uri CONTENT_URL =
            Uri.parse("com.exemple.bien_aime.wqfinalproject.MuestrasContentProvider/cpmuestras");
    String usuarioLlegando;

    public ServiceMonitoreo() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                requestValor(intent);
            }
        }, 0, 10000);

        return START_STICKY;
    }

    public void requestValor(Intent intent){
        Bundle extras = intent.getExtras();
        final String nombreDispositivo = extras.getString("dispositivo");
        usuarioLlegando = extras.getString("user");

        ApiService apiService=ApiService.retrofit.create(ApiService.class);



        final retrofit2.Call<List<Muestra>> call= apiService.getValores();
        call.enqueue(new Callback<List<Muestra>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
                List<Muestra> muestras=response.body();

                ContentValues contentValues = new ContentValues();
                for (final Muestra muestra: muestras){
                    if(muestra.getMuestra().getDispositivo().getNombreDispositivo().equals(nombreDispositivo)){
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>"+muestra.getValor());
                        contentValues.put(MuestrasContentProvider.nombreParametro,muestra.getParametro().getNombreParametro());
                        contentValues.put(MuestrasContentProvider.valor,muestra.getValor());
                        contentValues.put(MuestrasContentProvider.fecha,muestra.getMuestra().getFechaMuestra());
                        contentValues.put(MuestrasContentProvider.dispositivo,muestra.getMuestra().getDispositivo().getNombreDispositivo());
                    }
                    Uri uri=getContentResolver().insert(MuestrasContentProvider.CONTENT_URL,contentValues);

                }
                System.out.println("**************************************"+muestras.size());
            }

            @Override
            public void onFailure(Call<List<Muestra>> call, Throwable t) {

            }
        });
    }
}



