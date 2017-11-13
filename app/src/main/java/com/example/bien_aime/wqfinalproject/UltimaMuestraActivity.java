package com.example.bien_aime.wqfinalproject;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UltimaMuestraActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String dispositivoName;
    Muestra muestraFinal;
    ListView mListView;
    Double latitud;
    Double longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultima_muestra);


        Intent i = getIntent();
        dispositivoName = i.getStringExtra("dispositivo");


        final TextView fechaMuestra = (TextView) findViewById(R.id.load_immueble_fecha);

        mListView = (ListView) findViewById(R.id.list);
        final ArrayList<String> listaMuestra = new ArrayList<>();


        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        final retrofit2.Call<List<Muestra>> call = apiService.getValores();
        call.enqueue(new Callback<List<Muestra>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
                List<Muestra> muestras = response.body();

                for (int i = 0; i < muestras.size(); i++) {
                    if (muestras.get(i).getMuestra().getDispositivo().getNombreDispositivo().equals(dispositivoName)) {
                        fechaMuestra.setText(muestras.get(i).getMuestra().getFechaMuestra());
                        latitud = muestras.get(i).getMuestra().getLocalizacion().getLatitud();
                        longitud = muestras.get(i).getMuestra().getLocalizacion().getLongitud();
                        listaMuestra.add(muestras.get(i).getParametro().getNombreParametro() + "         " + muestras.get(i).getValor());

                    }
                }
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(UltimaMuestraActivity.this, android.R.layout.simple_list_item_1, listaMuestra);
                // Set The Adapter
                mListView.setAdapter(arrayAdapter);

                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(UltimaMuestraActivity.this);


            }

            @Override
            public void onFailure(Call<List<Muestra>> call, Throwable t) {
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MapFragment f = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
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
    @Override
    public void onMapReady(GoogleMap retMap) {

        mMap = retMap;

        setUpMap();

    }

    public void setUpMap() {

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if(longitud!=null && latitud!=null) {
            System.out.println("La encontro");
            LatLng sydney = new LatLng(latitud, longitud);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Santiago"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));
        }else{
            Toast.makeText(this, "Unable to fetch the current location", Toast.LENGTH_SHORT).show();
        }

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
