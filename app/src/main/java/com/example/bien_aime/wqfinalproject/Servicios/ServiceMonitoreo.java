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
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.HomeActivity;
import com.example.bien_aime.wqfinalproject.LoginActivity;
import com.example.bien_aime.wqfinalproject.ModeloDB.MuestraSQLiteHelper;
import com.example.bien_aime.wqfinalproject.MonitoreoActivity;
import com.example.bien_aime.wqfinalproject.MuestrasContentProvider;
import com.example.bien_aime.wqfinalproject.R;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
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

import static android.R.attr.name;

public class ServiceMonitoreo extends Service {
    private Timer timer = new Timer();
    List<Muestra> muestras=new ArrayList<>();

    static final Uri CONTENT_URL =
            Uri.parse("com.exemple.bien_aime.wqfinalproject.MuestrasContentProvider/cpmuestras");
    Handler mHandler;

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
                System.out.println("Everytime brooooooooooooooooooooooooooooooooooooooooo");
                //sendRequest(intent);
                requestValor(intent);
            }
        }, 0, 10000);//5 Seconds

        return START_STICKY;
    }

    public void requestValor(Intent intent){
        final String nombreDispositivo=intent.getStringExtra("dispositivo");
        ApiService apiService=ApiService.retrofit.create(ApiService.class);

        final retrofit2.Call<List<Muestra>> call= apiService.getValores();
        call.enqueue(new Callback<List<Muestra>>() {
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
                            if(!muestra.getMuestra().getListaNotificaciones().get(i).getStatusEnviada()){
                                System.out.println("Nooooooooooooooooooooooooooooooooooooooooooooooo");

                                NotificationCompat.Builder builder =
                                        new NotificationCompat.Builder(ServiceMonitoreo.this)
                                                .setSmallIcon(R.drawable.profile_icon)
                                                .setContentTitle("Agua No potable")
                                                .setContentText("Se considera que ese agua es no potable5");


                                Intent notificationIntent = new Intent(ServiceMonitoreo.this, LoginActivity.class);

                                PendingIntent contentIntent = PendingIntent.getActivity(ServiceMonitoreo.this, 0, notificationIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);

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



                                Thread thread = new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        try  {
                                            //Your code goes here
                                            HttpClient httpClient=new DefaultHttpClient();
                                            HttpPost httpPost=new HttpPost("http://waterqualityjohn.herokuapp.com/API/ActivateNotification/");
                                            //Hay que pensar en cambiar esto porque podria haber mas de una notificacion
                                            String json="{"+"id:"+muestra.getMuestra().getListaNotificaciones().get(0).getId() +",statusEnviada:"+"true"+"}";

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
                                                //assertThat(response.getStatusLine().getStatusCode(), (Matcher<? super Integer>) equalTo(200));
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
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

