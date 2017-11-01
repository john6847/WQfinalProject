package com.example.bien_aime.wqfinalproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.ModeloDB.MuestraSQLiteHelper;
import com.example.bien_aime.wqfinalproject.ModeloDB.Muestras;
import com.example.bien_aime.wqfinalproject.Servicios.DownloadResultReceiver;
import com.example.bien_aime.wqfinalproject.Servicios.MyIntentService;
import com.example.bien_aime.wqfinalproject.adapter.ParameterRecyclerView;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bien_aime.wqfinalproject.MuestrasContentProvider.CONTENT_URL;

public class MonitoreoActivity extends AppCompatActivity {
    HashMap<String,Muestras> muestras = new HashMap<>();
    static final Uri CONTENT_URL =
            Uri.parse("content://com.exemple.bien_aime.wqfinalproject.MuestrasContentProvider/cpmuestras");

    JSONObject jsonArray;
    NotificationCompat.Builder notification;
    private static final int uniqueId = 45612;
    private Timer autoUpdate;
    String dispositivoName;
    ContentResolver resolver;
    int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);

        Intent i = getIntent();
        dispositivoName = i.getStringExtra("dispositivo");

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        show_toolbar(getResources().getString(R.string.toolbar_title_dispositivo), true);
    }

    @Override
    public void onResume() {
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        updateHTML();
                    }
                });

            }
        }, 0, 50009); // updates each 10 secs
    }

    private void updateHTML() {
        resolver=getContentResolver();
        String[] projection = new String[]{"id", "nombreParametro","valor","fecha","dispositivo"};
        int sizeMuestra=getSizeMuestra();
        System.out.println("::::::::::::::::::::::::::::: "+sizeMuestra);//Buscar porque esta llegando null

        Cursor cursor = resolver.query(CONTENT_URL, projection, null,null," id DESC"+" LIMIT 2",null);

        if (cursor != null && cursor.moveToFirst()) {
            do {

                String id = cursor.getString(cursor.getColumnIndex("id"));
                String nombreParametro = cursor.getString(cursor.getColumnIndex("nombreParametro"));
                String valor = cursor.getString(cursor.getColumnIndex("valor"));
                String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                String dispositivo = cursor.getString(cursor.getColumnIndex("dispositivo"));

                if (dispositivo.equals(dispositivoName)) {
                    System.out.println("))))))))))))))))))))))))" + dispositivo);
                    muestras.put(id,new Muestras("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png", nombreParametro, Double.parseDouble(valor), fecha));
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        System.out.println("Otro ladoooooooooooooooooooooooooooooooooooooooooooooo"+muestras.size());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pictureRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        ParameterRecyclerView parameterRecycler = new ParameterRecyclerView(new ArrayList<>(muestras.values()), MonitoreoActivity.this);
        recyclerView.setAdapter(parameterRecycler);
        recyclerView.setLayoutManager(linearLayoutManager);


        for (int i=0;i<muestras.size();i++){
            muestras.remove(i);
            muestras.clear();
        }
    }

    @Override
    public void onPause() {
        autoUpdate.cancel();
        super.onPause();
    }


    public int getSizeMuestra() {
        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        final List<Muestra> muestraList = new ArrayList<>();

        final retrofit2.Call<List<Muestra>> call = apiService.getValores();
        call.enqueue(new Callback<List<Muestra>>() {
            @Override
            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
                List<Muestra> muestras = response.body();
                cont=0;

                for (Muestra muestra : muestras) {
                    if (muestra.getMuestra().getDispositivo().getNombreDispositivo().equals(dispositivoName)) {
                        muestraList.add(muestra);
                        cont++;
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Muestra>> call, Throwable t) {
            }
        });
        return  cont;
    }

      public void show_toolbar(String titulo, boolean upBoton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor((getResources().getColor(R.color.toolbarTextColor)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBoton);
    }
}


//public class MonitoreoActivity extends AppCompatActivity {
//    static final Uri CONTENT_URL =
//            Uri.parse("com.exemple.bien_aime.wqfinalproject.MuestrasContentProvider/cpmuestras");
//
//    JSONObject jsonArray;
//    NotificationCompat.Builder notification;
//    private static final int uniqueId=45612;
//    private Timer autoUpdate;
//    String dispositivoName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        //Llamando al recurso de muestras
////        MonitoreoActivity.HttpUtils httpUtils=new MonitoreoActivity.HttpUtils();
////        httpUtils.execute("http://waterqualityjohn.herokuapp.com/API/muestras");
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_monitoreo);
//
//        Intent i = getIntent();
//        dispositivoName= i.getStringExtra("dispositivo");
//
//        notification=new NotificationCompat.Builder(this);
//        notification.setAutoCancel(true);
//
//        show_toolbar(getResources().getString(R.string.toolbar_title_dispositivo),true);
//
//
//        List<Muestras> muestras =new ArrayList<>();
//        CursorLoader cursorLoader;
//
//        // Provides access to other applications Content Providers
//        ContentResolver resolver;
//        resolver = getContentResolver();
//
//        // Projection contains the columns we want
//        String[] projection = new String[]{"id", "nombreParametro", "valor", "fecha", "dispositivo"};
//
//        // Pass the URL, projection and I'll cover the other options below
//        Cursor cursor = resolver.query(CONTENT_URL, projection, null, null, null);
//
//        System.out.println("Aquiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
//        String contactList = "";
//
//        // Cycle through and display every row of data
//        if (cursor != null && cursor.moveToFirst()) {
//
//            do {
//                String id = cursor.getString(cursor.getColumnIndex("id"));
//                String nombreParametro = cursor.getString(cursor.getColumnIndex("nombreParametro"));
//                String valor=cursor.getString(cursor.getColumnIndex("valor"));
//                String fecha=cursor.getString(cursor.getColumnIndex("fecha"));
//                String dispositivo=cursor.getString(cursor.getColumnIndex("dispositivo"));
//
//                System.out.println("Helooooooooooooooooooooooooooooooooooo");
//                if (dispositivo.equals(dispositivoName)){
//                    System.out.println("))))))))))))))))))))))))"+dispositivo);
//                    muestras.add(new Muestras("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png",nombreParametro,Double.parseDouble(valor),fecha));
//                }
//                //contactList = contactList + id + " : " + name + "\n";
//
//            } while (cursor.moveToNext());
//
//        }
//
//        if (cursor != null) {
//            cursor.close();
//        }
////        db.close();
//
//
//
////        MuestraSQLiteHelper instanciaDB =
////                new MuestraSQLiteHelper(this, "DBmuestras", null, 1);
////
////        SQLiteDatabase db = instanciaDB.getReadableDatabase();
////
////        Cursor c = db.rawQuery("SELECT id,nombreParametro,valor,fecha FROM Muestras ", null);
////        if(c.moveToFirst()){
////            do{
////                //assing values
////                String id  = c.getString(0);
////                String nombreParametro = c.getString(1);
////                String valor = c.getString(2);
////                String fecha = c.getString(3);
////                //Do something Here with values
////                muestras.add(new Muestras("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png",nombreParametro,Double.parseDouble(valor),fecha));
////
////            }while(c.moveToNext());
////        }
////        c.close();
////        db.close();
//
//
//        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.pictureRecycler);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        ParameterRecyclerView parameterRecycler=new ParameterRecyclerView(muestras,MonitoreoActivity.this);
//        recyclerView.setAdapter(parameterRecycler);
//        recyclerView.setLayoutManager(linearLayoutManager);
////        Intent refresh = new Intent(this, MonitoreoActivity.class);
////        startActivity(refresh);//Start the same Activity
////        overridePendingTransition(0, 0);
////        finish(); //finish Activity.
////        overridePendingTransition(0, 0);
//
//    }
//
////    @Override
////    public void onResume() {
////        super.onResume();
////        autoUpdate = new Timer();
////        autoUpdate.schedule(new TimerTask() {
////            @Override
////            public void run() {
////                runOnUiThread(new Runnable() {
////                    public void run() {
////                        updateHTML();
////                    }
////                });
////            }
////        }, 0, 10000); // updates each 10 secs
////    }
////
////    private void updateHTML(){
////        // your logic here
////        HttpUtils httpUtils=new HttpUtils();
////        httpUtils.execute("http://waterqualityjohn.herokuapp.com/API/muestras");
////
////
////        //Tomando el nombre del dispositivo
////        final String dispositivoName= getIntent().getStringExtra("dispositivo");
////
////        //Toast.makeText(MonitoreoActivity.this, "Le diste click al dispo "+dispositivoName, Toast.LENGTH_SHORT).show();
////        List<Muestra> muestras =new ArrayList<>();
////
////        //Aqui vamos a parsear para tener la informacion como un arreglo de Json
////        try {
////            jsonArray=new JSONObject(httpUtils.get());
////            System.out.println("====================="+jsonArray);
////
////        } catch (JSONException e) {
////            e.printStackTrace();
////        } catch (InterruptedException e1) {
////            e1.printStackTrace();
////        } catch (ExecutionException e1) {
////            e1.printStackTrace();
////        }
////
////        try {
////            JSONObject jsn = new JSONObject(String.valueOf(jsonArray));
////
////            //Llamando a notificaciones para verificar si es noPotable
////            JSONArray notificacion= jsn.getJSONArray("notificaciones");
////            for (int i = 0; i < notificacion.length(); ++i) {
////                JSONObject not = notificacion.getJSONObject(i);
////                String nombre=not.getString("nombre");
////
////                if(nombre.equals("AGUAPOTABLE")) {
////
////
//////                    notification.setSmallIcon(R.drawable.icon);
//////                    notification.setTicker("Ticker");
//////                    notification.setWhen(System.currentTimeMillis());
//////                    notification.setContentTitle("Agua no potable");
//////                    notification.setContentText("Ese agua es considerado no potable");
//////
//////                    Intent intent = new Intent(this, MonitoreoActivity.class);
//////                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//////                    notification.setContentIntent(pendingIntent);
//////
//////                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//////                    notificationManager.notify(uniqueId, notification.build());
////                }
////
////            }
////            //String nombreNotificacion=notificacion.getString("nombre");
////
////            JSONObject array = null;
////            try {
////                array = jsn.getJSONObject("dispositivo");
////
////                String fecha= jsn.getString("fechaMuestra");
////
////                String nombreDispositivo = array.getString("nombreDispositivo");
////
////                if (nombreDispositivo != null) {
////
////                    if(nombreDispositivo.equals(dispositivoName)){
////                        JSONArray valores = null;
////                        valores = jsn.getJSONArray("listadoValores");
////                        for (int l = 0 ; l < valores.length(); l++) {
////                            JSONObject objetoValores = valores.getJSONObject(l);
////                            if (objetoValores != null) {
////
////                                String valorParam = objetoValores.getString("valor");
////                                JSONObject param = objetoValores.getJSONObject("parametro");
////                                String nombreParam=param.getString("nombreParametro");
////                                //muestras.add(new Muestra("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png","TMP",6.5));
////                                muestras.add(new Muestra("http://www.iotsens.com/wp-content/uploads/2016/04/ICON_Smart_waste_water.png",nombreParam,Double.parseDouble(valorParam),fecha));
////                            }
////                        }
////                    }
////                }
////
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//
//
//
//
//       // startActivity(new Intent(this, MonitoreoActivity.class));
//    //}
//
////    @Override
////    public void onPause() {
////        autoUpdate.cancel();
////        super.onPause();
////    }
//
//
//    public void show_toolbar(String titulo, boolean upBoton){
//        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor((getResources().getColor(R.color.toolbarTextColor)));
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(titulo);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(upBoton);
//    }
//
//
//}
