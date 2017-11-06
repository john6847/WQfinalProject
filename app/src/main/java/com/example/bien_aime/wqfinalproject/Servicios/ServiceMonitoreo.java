package com.example.bien_aime.wqfinalproject.Servicios;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.HomeActivity;
import com.example.bien_aime.wqfinalproject.LoginActivity;
import com.example.bien_aime.wqfinalproject.ModeloDB.MuestraSQLiteHelper;
import com.example.bien_aime.wqfinalproject.MonitoreoActivity;
import com.example.bien_aime.wqfinalproject.MuestrasContentProvider;
import com.example.bien_aime.wqfinalproject.R;
import com.example.bien_aime.wqfinalproject.adapter.DispositivoRecycleView;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;
import com.example.bien_aime.wqfinalproject.verMuestraNotificacion;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.SimpleDateFormat;
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

import static android.R.attr.cacheColorHint;
import static android.R.attr.name;

public class ServiceMonitoreo extends Service {
    private Timer timer = new Timer();
    List<Muestra> muestras=new ArrayList<>();

    static final Uri CONTENT_URL =
            Uri.parse("com.exemple.bien_aime.wqfinalproject.MuestrasContentProvider/cpmuestras");
    Handler mHandler;
    String usuarioLlegando;
    List<Usuario> usuarios=new ArrayList<>();

    public ServiceMonitoreo() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId){


//        usuarioLlegando = intent.getStringExtra("user");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                requestValor(intent);
            }
        }, 0, 10000);

        return START_STICKY;
    }

    public void requestValor(Intent intent){
        final String nombreDispositivo=intent.getStringExtra("dispositivo");
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

                    for(int i=0;i<muestra.getMuestra().getListaNotificaciones().size();i++){
                        if(muestra.getMuestra().getListaNotificaciones().get(i).getNombre().equalsIgnoreCase("noPotable")){
                            Usuario[] usuarios=getUser();
//                            if(!muestra.getMuestra().getListaNotificaciones().get(i).getStatusEnviada()){
                            if(!muestra.getNotificada()){

                                Intent notificationIntent = new Intent(ServiceMonitoreo.this, verMuestraNotificacion.class).putExtra("muestras",(Serializable) muestra);

                                PendingIntent contentIntent = PendingIntent.getActivity(ServiceMonitoreo.this, 0, notificationIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);

                                NotificationCompat.Builder builder =
                                        new NotificationCompat.Builder(ServiceMonitoreo.this)
                                                .setSmallIcon(R.drawable.logo)
                                                .setAutoCancel(true)
                                                .setContentTitle("Agua No potable")
                                                .setContentText("Se considera que ese agua es no potable")
                                                .addAction(R.drawable.common_full_open_on_phone, "Ver Informacion", contentIntent)
                                                .setColor(getColor(R.color.colorPrimary));


                                builder.setContentIntent(contentIntent);
                                builder.setAutoCancel(true);
                                builder.setLights(Color.BLUE, 500, 500);
                                long[] pattern = {500,500,500,500,500,500,500,500,500};
                                builder.setVibrate(pattern);
                                builder.setStyle(new NotificationCompat.InboxStyle());
                                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                builder.setSound(alarmSound);
                                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                manager.notify(1, builder.build());
                            }
                        }
                    }
                }
                System.out.println("**************************************"+muestras.size());
            }

            @Override
            public void onFailure(Call<List<Muestra>> call, Throwable t) {

            }
        });
    }

    public Usuario[] getUser(){
        ApiService apiService= ApiService.retrofit.create(ApiService.class);

        final Usuario[] usuarioRequerido = {new Usuario()};
        retrofit2.Call<List<Usuario>> call= apiService.getUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Log.d("OnResponse ", response.body().toString());
                Log.d("OnResponse ", response.body().toString());

                usuarios = response.body();

                for (Usuario usuario: usuarios){
                    if (usuario.getUsername().equals(usuarioLlegando)){
                        usuarioRequerido[0] =usuario;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("failure", String.valueOf(t.getMessage()));
            }
        });

        return usuarioRequerido;
    }



   /* public void sendRequest(Intent intent){
//Toast.makeText(this,"Empieza el servicio", Toast.LENGTH_LONG).show();
        //final String nombreDispositivo=intent.getStringExtra("dispositivo");
        final String nombreDispositivo = intent.getStringExtra("dispositivo");
        //Toast.makeText(this,"Empieza el servicio"+nombreDispositivo, Toast.LENGTH_LONG).show();
        System.out.println("empiezaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + nombreDispositivo);


        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        final Call<Muestra> call = apiService.getMuestra();

        call.enqueue(new Callback<Muestra>() {
            @Override
            public void onResponse(Call<Muestra> call, Response<Muestra> response) {
                Muestra muestra = response.body();
                System.out.println("Muestraaaaaaaaaaaaaa"+muestra.getFechaMuestra());
                ContentValues contentValues = new ContentValues();
                for (int i=0;i<muestra.getListadoValores().size();i++){
                    if(muestra.getDispositivo().getNombreDispositivo().equals(nombreDispositivo)){
                        contentValues.put(MuestrasContentProvider.nombreParametro,muestra.getListadoValores().get(i).getParametro().getNombreParametro());
                        contentValues.put(MuestrasContentProvider.valor,muestra.getListadoValores().get(i).getValor());
                        contentValues.put(MuestrasContentProvider.fecha,muestra.getFechaMuestra());
                        contentValues.put(MuestrasContentProvider.dispositivo,muestra.getDispositivo().getNombreDispositivo());
                    }
                    Uri uri=getContentResolver().insert(MuestrasContentProvider.CONTENT_URL,contentValues);
                }

                System.out.println("New muestra added");
            }

            @Override
            public void onFailure(Call<Muestra> call, Throwable t) {

            }
        });

}*/
}

