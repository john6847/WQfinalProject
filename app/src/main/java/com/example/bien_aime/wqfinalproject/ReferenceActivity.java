package com.example.bien_aime.wqfinalproject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.adapter.DispositivoRecycleView;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bien-aime on 9/8/2017.
 */

public class ReferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);

        Intent i = getIntent();
        final String dispositivoName = i.getStringExtra("dispositivo");

        // Stores a key value pair
        final ContentValues values = new ContentValues();



//        ApiService apiService= ApiService.retrofit.create(ApiService.class);
//        final retrofit2.Call<List<Muestra>> call= apiService.getMuestras();
//
//        call.enqueue(new Callback<List<Muestra>>() {
//            @Override
//            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
//                List<Muestra> muestras= response.body();
//
//                for (Muestra muestra: muestras){
//                    if(muestra.getDispositivo().equals(dispositivoName))
//                    values.put(MuestrasContentProvider.nombreParametro, muestra.getListadoValores().iterator().next().getParametro().getNombreParametro());
//                    values.put(MuestrasContentProvider.valor, muestra.getListadoValores().iterator().next().getValor());
//                    values.put(MuestrasContentProvider.fecha, muestra.getFechaMuestra());
//
//                }
//                System.out.println("Contact addeddddddddddddddddddddddddddddddddddddddddddd");
//                // Provides access to other applications Content Providers
//                Uri uri = getContentResolver().insert(MuestrasContentProvider.CONTENT_URL, values);
//                if (uri != null) {
//                    getBaseContext().getContentResolver().notifyChange(uri,null);
//                }
//                Toast.makeText(getBaseContext(), "New Contact Added", Toast.LENGTH_LONG)
//                        .show();
//            }
//
//
//            @Override
//            public void onFailure(Call<List<Muestra>> call, Throwable t) {
//
//            }
//        });



    }

}
