package com.example.bien_aime.wqfinalproject.Servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import android.util.Log;
import android.widget.Toast;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.HomeActivity;
import com.example.bien_aime.wqfinalproject.ModeloDB.Muestras;
import com.example.bien_aime.wqfinalproject.MuestrasContentProvider;
import com.example.bien_aime.wqfinalproject.R;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;
import com.example.bien_aime.wqfinalproject.verMuestraNotificacion;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class ServicioNotificacion extends Service {
    private Timer timer = new Timer();
    String usuarioLlegando;
    List<Dispositivo> dispositivos=new ArrayList<>();
    List<Usuario> usuarios=new ArrayList<>();
    List<Muestra> muestras;


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
        }, 0, 20000);

        return START_STICKY;
    }

    public void requestValor(Intent intent){

        usuarioLlegando = intent.getStringExtra("user");

        ApiService apiService= ApiService.retrofit.create(ApiService.class);

        final List<Muestra> muestras=getMuestras();

        retrofit2.Call<List<Usuario>> call= apiService.getUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Log.d("OnResponse ", response.body().toString());
                Log.d("OnResponse ", response.body().toString());
                usuarios = response.body();

                for (final Usuario usuario: usuarios){
                    if (usuario.getUsername().equals(usuarioLlegando)){
                        for (int j=0;j<usuario.getListaDispositivos().size();j++) {

                            if (muestras != null) {
                                for (int i = 0; i < muestras.size(); i++) {
                                    if (Objects.equals(muestras.get(0).getMuestra().getDispositivo().getId(), usuario.getListaDispositivos().get(j).getDispositivo().getId())) {

                                        //No hay notificacion
                                        if( muestras.get(0).getMuestra().getListaNotificaciones().size()!= 0)
                                            if (muestras.get(0).getMuestra().getListaNotificaciones().get(0).getNombre().equalsIgnoreCase("noPotable") && !usuario.getSilenciarNotificacion()) {
                                                System.out.println("Gritando No potable");

                                                final int finalJ = j;
                                                Thread thread = new Thread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        try  {
                                                            HttpClient httpClient=new DefaultHttpClient();
                                                            HttpPost httpPost=new HttpPost("https://waterquality.pionot.com/API/notificarUsuarioDispositivo/");
                                                            String json="{"+"idUsuario:"+usuario.getId()+",idDispositivo:"+usuario.getListaDispositivos().get(finalJ).getId() +",notificacion:"+"true"+"}";

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
                                                                System.out.println("Responseee "+response.getStatusLine().getStatusCode());
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                                thread.start();

                                                SharedPreferences lector = getDefaultSharedPreferences(getApplicationContext());

    //                                            SharedPreferences lector =   getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                                                boolean prueba=lector.getBoolean("leido",true);

                                                System.out.println("El valor de leido: "+prueba);

                                                if(usuario.getListaDispositivos().get(j).getUsuarioNotificado() && !prueba) {
//                                                if(usuario.getListaDispositivos().get(j).getUsuarioNotificado()) {
                                                    System.out.println("Deberia entrar aqui");
                                                    Intent notificationIntent = new Intent(ServicioNotificacion.this, verMuestraNotificacion.class).putExtra("muestras", (Serializable) muestras).putExtra("usuarioId", usuario.getId().toString()).putExtra("idDispositivo", usuario.getListaDispositivos().get(j).getDispositivo().getId().toString());

                                                    PendingIntent contentIntent = PendingIntent.getActivity(ServicioNotificacion.this, 0, notificationIntent,
                                                            PendingIntent.FLAG_UPDATE_CURRENT);
//Define sound URI
                                                    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                                    NotificationCompat.Builder builder =
                                                            new NotificationCompat.Builder(ServicioNotificacion.this)
                                                                    .setSmallIcon(R.drawable.m)
                                                                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                                                            R.mipmap.m))
                                                                    .setAutoCancel(true)
                                                                    .setPriority(1)
                                                                    .setSound(soundUri)
                                                                    .setContentTitle("Agua No potable")
                                                                    .setContentText("Se ha recorrido una muestra no potable")
                                                                    .addAction(R.drawable.common_full_open_on_phone, "Ver Informacion", contentIntent)
                                                                    .setColor(getColor(R.color.colorPrimaryDark));

                                                    builder.setContentIntent(contentIntent);
                                                    builder.setAutoCancel(true);
                                                    builder.setColor(getColor(R.color.colorPrimaryDark));

//                                                    builder.set(getColor(R.color.colorPrimaryDark));
                                                    builder.setLights(Color.RED, 500, 500);
                                                    long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
                                                    builder.setVibrate(pattern);
                                                    builder.setStyle(new NotificationCompat.BigTextStyle());
                                                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                                    builder.setSound(alarmSound);
                                                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                    manager.notify(1, builder.build());


                                                }

                                            }
                                        }
                                    }
                                    dispositivos.add(new Dispositivo(usuario.getListaDispositivos().get(j).getDispositivo().getId(),"http://www.radix-int.com/wp-content/uploads/2015/03/mdmMockup.png" ,usuario.getListaDispositivos().get(j).getDispositivo().getNombreDispositivo(), usuario.getListaDispositivos().get(j).getDispositivo().getDescripcion(), usuario.getListaDispositivos().get(j).getDispositivo().getDireccion()));
                                }
                            }
                        }
                    }
                }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("failure", String.valueOf(t.getMessage()));
            }
        });
    }


    public List<Muestra> getMuestras()
    {
        ApiService apiService=ApiService.retrofit.create(ApiService.class);

        final retrofit2.Call<List<Muestra>> call= apiService.getValores();
        call.enqueue(new Callback<List<Muestra>>() {
            @Override
            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
                muestras = response.body();

                SharedPreferences p = getDefaultSharedPreferences(getApplicationContext());
                int muestraId = p.getInt("idMuestra", -1);

                if (muestras.size() != 0) {
                    System.out.println("Muestra id : " + muestraId);
                    System.out.println("Muestra id 1: " + muestras.get(0).getMuestra().getId());

                    if (muestraId != muestras.get(0).getMuestra().getId()) {
                        System.out.println("Set la pa fet");

                        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.putInt("idMuestra", muestras.get(0).getMuestra().getId());
                        editor.putBoolean("leido", false);
                        editor.apply();
                    }
                }else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ServicioNotificacion.this.getApplicationContext(),"Problema con la Muestra",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Muestra>> call, Throwable t) {

            }
        });

        return muestras;
    }
}
