package com.example.bien_aime.wqfinalproject.Servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.HomeActivity;
import com.example.bien_aime.wqfinalproject.MuestrasContentProvider;
import com.example.bien_aime.wqfinalproject.R;
import com.example.bien_aime.wqfinalproject.adapter.DispositivoRecycleView;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;
import com.example.bien_aime.wqfinalproject.verMuestraNotificacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioNotificacion extends Service {
    private Timer timer = new Timer();
    String usuarioLlegando;
    List<Dispositivo> dispositivos=new ArrayList<>();
    List<Usuario> usuarios=new ArrayList<>();

    public ServicioNotificacion() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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

        ApiService apiService= ApiService.retrofit.create(ApiService.class);

        usuarioLlegando = intent.getStringExtra("user");

        retrofit2.Call<List<Usuario>> call= apiService.getUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Log.d("OnResponse ", response.body().toString());
                Log.d("OnResponse ", response.body().toString());


                usuarios = response.body();

                for (Usuario usuario: usuarios){
                    if (usuario.getUsername().equals(usuarioLlegando)){
                        for (int j=0;j<usuario.getListaDispositivos().size();j++){
                            //Chequear muestra de cada dispositivo

                            dispositivos.add(new Dispositivo(usuario.getListaDispositivos().get(j).getDispositivo().getId(),usuario.getListaDispositivos().get(j).getDispositivo().getNombreDispositivo(), usuario.getListaDispositivos().get(j).getDispositivo().getDescripcion(),usuario.getListaDispositivos().get(j).getDispositivo().getLocalizacion()));
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("failure", String.valueOf(t.getMessage()));
            }
        });


//                        Usuario usuarioAChequear=getUser();
//
//                        if(usuarioAChequear!=null)
//                            System.out.println("El usuario requerido es:::::::::::::::::::::::::::: : "+usuarioAChequear.getUsername());
//
//                        if (usuarioAChequear != null && muestra.getMuestra().getListaNotificaciones().get(i).getNombre().equalsIgnoreCase("noPotable") && !usuarioAChequear.getSilenciarNotificacion()) {
//
//                            if (!muestra.getNotificada()) {
//
//                                Intent notificationIntent = new Intent(ServiceMonitoreo.this, verMuestraNotificacion.class).putExtra("muestras", (Serializable) muestra);
//
//                                PendingIntent contentIntent = PendingIntent.getActivity(ServiceMonitoreo.this, 0, notificationIntent,
//                                        PendingIntent.FLAG_UPDATE_CURRENT);
//
//                                NotificationCompat.Builder builder =
//                                        new NotificationCompat.Builder(ServiceMonitoreo.this)
//                                                .setSmallIcon(R.drawable.logo)
//                                                .setAutoCancel(true)
//                                                .setContentTitle("Agua No potable")
//                                                .setContentText("Se considera que ese agua es no potable")
//                                                .addAction(R.drawable.common_full_open_on_phone, "Ver Informacion", contentIntent)
//                                                .setColor(getColor(R.color.colorPrimary));
//
//
//                                builder.setContentIntent(contentIntent);
//                                builder.setAutoCancel(true);
//                                builder.setLights(Color.BLUE, 500, 500);
//                                long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
//                                builder.setVibrate(pattern);
//                                builder.setStyle(new NotificationCompat.InboxStyle());
//                                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                                builder.setSound(alarmSound);
//                                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                                manager.notify(1, builder.build());



    }
}
