package com.example.bien_aime.wqfinalproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.Servicios.ServiceMonitoreo;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UltimaMuestraActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String dispositivoName;
    Muestra muestraFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultima_muestra);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = getIntent();
        dispositivoName = i.getStringExtra("dispositivo");


        final TextView fechaMuestra=(TextView) findViewById(R.id.load_immueble_fecha);
        final TextView normativaMuestra=(TextView) findViewById(R.id.load_normativaAsignada);
        final TextView dispositivoMuestra=(TextView) findViewById(R.id.load_immueble_dispositivo);
        final TextView contenedorMuestra=(TextView) findViewById(R.id.load_immueble_Contenedor);
        final TextView estadoMuestra=(TextView) findViewById(R.id.load_immueble_estado);

        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        final retrofit2.Call<List<Muestra>> call= apiService.getValores();
        call.enqueue(new Callback<List<Muestra>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
                List<Muestra> muestras=response.body();

                ContentValues contentValues = new ContentValues();
                for (final Muestra muestra: muestras) {
                    if (muestra.getMuestra().getDispositivo().getNombreDispositivo().equals(dispositivoName)) {
                        fechaMuestra.setText(muestra.getMuestra().getFechaMuestra());
                        normativaMuestra.setText(muestra.getMuestra().getNormativa().getNombreNormativa());
                        dispositivoMuestra.setText(muestra.getMuestra().getDispositivo().getNombreDispositivo());
//                        contenedorMuestra.setText(muestra.getMuestra().getContenedor());
                        estadoMuestra.setText(muestra.getMuestra().getEstadoMuestra().getNombre());

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Muestra>> call, Throwable t) {
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

       // LatLng sydney = new LatLng(-70.66545, 19.44835);
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng sydney = new LatLng(19.44835,-70.66545);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Santiago"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f));

        //Add a marker in Sydney, Australia, and move the camera.

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }
}
