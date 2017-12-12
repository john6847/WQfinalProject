package com.example.bien_aime.wqfinalproject.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.HomeActivity;
import com.example.bien_aime.wqfinalproject.MonitoreoActivity;
import com.example.bien_aime.wqfinalproject.R;
import com.example.bien_aime.wqfinalproject.ReferenceActivity;
import com.example.bien_aime.wqfinalproject.Servicios.DownloadResultReceiver;
import com.example.bien_aime.wqfinalproject.Servicios.MyIntentService;
import com.example.bien_aime.wqfinalproject.Servicios.ServiceMonitoreo;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bien_aime.wqfinalproject.R.layout.cardviw_dispositivos;


/**
 * Created by Bien-aime on 8/23/2017.
 */

public class DispositivoRecycleView extends RecyclerView.Adapter<DispositivoRecycleView.ParameterViewHolder>  {
    private List<Dispositivo> dispositivos;
    private List<Usuario> usuarios;
    private Activity activity;
    String usuarioLlegando;
    Usuario usuarioRequerido;
    private DownloadResultReceiver mReceiver;
    private GoogleMap mMap;

    Double latitud;
    Double longitud;

    public DispositivoRecycleView(List<Dispositivo> dispositivos, Activity activity) {
        this.dispositivos = dispositivos;
//        this.ressources = ressources;
        this.activity = activity;
    }

    @Override
    public ParameterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(cardviw_dispositivos, parent, false);
        return new ParameterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParameterViewHolder holder, int position) {
        final Dispositivo dispositivo = dispositivos.get(position);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(DispositivoRecycleView.this);

        Intent i = activity.getIntent();

        assert i != null;
        usuarioLlegando = i.getStringExtra("user");

        holder.textView.setText(dispositivo.getNombreDispositivo());
        System.out.println("Nombre Dispositivo "+dispositivo.getNombreDispositivo());
        ProgressDialog mDialog = mDialog = new ProgressDialog(activity);
        ApiService apiService= ApiService.retrofit.create(ApiService.class);
        retrofit2.Call<List<Usuario>> call= apiService.getUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {

            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Log.d("OnResponse ", response.body().toString());
                Log.d("OnResponse ", response.body().toString());

                usuarios = response.body();

                    for (Usuario usuario: usuarios){
                        if (usuario.getUsername().equals(usuarioLlegando)){
                            usuarioRequerido =usuario;
                        }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("failure", String.valueOf(t.getMessage()));
            }
        });


        final ProgressDialog finalMDialog = mDialog;
        holder.monitoreoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, MonitoreoActivity.class).putExtra("dispositivo", dispositivo.getNombreDispositivo()));

                Bundle extras = new Bundle();
                extras.putString("user",usuarioRequerido.getUsername());
                extras.putString("dispositivo",dispositivo.getNombreDispositivo());
                activity.startService(new Intent(activity, ServiceMonitoreo.class).putExtras(extras));

            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, MonitoreoActivity.class).putExtra("dispositivo", dispositivo.getNombreDispositivo()));

                Bundle extras = new Bundle();
                extras.putString("user",usuarioRequerido.getUsername());
                extras.putString("dispositivo",dispositivo.getNombreDispositivo());
                activity.startService(new Intent(activity, ServiceMonitoreo.class).putExtras(extras));
            }
        });

        holder.monitoreoButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, MonitoreoActivity.class).putExtra("dispositivo", dispositivo.getNombreDispositivo()));

                Bundle extras = new Bundle();
                extras.putString("user",usuarioRequerido.getUsername());
                extras.putString("dispositivo",dispositivo.getNombreDispositivo());
                activity.startService(new Intent(activity, ServiceMonitoreo.class).putExtras(extras));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dispositivos.size();
    }


//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//
//    @Override
//    public void onMapReady(GoogleMap retMap) {
//
//        mMap = retMap;
//
//        setUpMap();
//
//    }
//
//    public void setUpMap() {
//
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//
//        if(longitud!=null && latitud!=null) {
//            System.out.println("La encontro");
//            LatLng sydney = new LatLng(latitud, longitud);
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Santiago"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18f));
//        }else{
//            System.out.println("No se todavia");
//        }
//
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                mMap.clear();
//
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
//                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
//            }
//        });
//    }

    public class ParameterViewHolder extends RecyclerView.ViewHolder {
        MapView mapView;
        GoogleMap map;
        private ImageButton imageView;
        private TextView textView;
        private TextView textView1;
        private TextView monitoreoButton;
        private ImageButton monitoreoButton1;


        public ParameterViewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.nombreDispositivo);
            imageView=(ImageButton) itemView.findViewById(R.id.imageDispositivo);
            monitoreoButton=(TextView) itemView.findViewById(R.id.buttonMonitoreo);
            monitoreoButton1=(ImageButton) itemView.findViewById(R.id.dispo);


        }


        }

}



