package com.example.bien_aime.wqfinalproject.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
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

public class DispositivoRecycleView extends RecyclerView.Adapter<DispositivoRecycleView.ParameterViewHolder> {
    private List<Dispositivo> dispositivos;
    private List<Usuario> usuarios;
    private Activity activity;
    String usuarioLlegando;
    Usuario usuarioRequerido;
    private DownloadResultReceiver mReceiver;



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


        Intent i = activity.getIntent();

        assert i != null;
        usuarioLlegando = i.getStringExtra("user");

        holder.textView.setText(dispositivo.getNombreDispositivo());
        System.out.println("Nombre Dispositivo "+dispositivo.getNombreDispositivo());
        //holder.imageView.setImageResource(Integer.parseInt(muestra.getPicture()));
        ProgressDialog mDialog = mDialog = new ProgressDialog(activity);


        ApiService apiService= ApiService.retrofit.create(ApiService.class);

        retrofit2.Call<List<Usuario>> call= apiService.getUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {

            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                System.out.println("Encontro una resuesta?????????");
                Log.d("OnResponse ", response.body().toString());
                Log.d("OnResponse ", response.body().toString());

                usuarios = response.body();

                    for (Usuario usuario: usuarios){
                        if (usuario.getUsername().equals(usuarioLlegando)){
                            System.out.println("Encontro el maldito usuario?????????????");
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

                // activity.startActivity(new Intent(activity, ReferenceActivity.class).putExtra("dispositivo",dispositivo.getNombreDispositivo()));
                //activity.startService(new Intent(activity, MyIntentService.class).putExtra("dispositivo",dispositivo.getNombreDispositivo()));
                activity.startActivity(new Intent(activity, MonitoreoActivity.class).putExtra("dispositivo", dispositivo.getNombreDispositivo()));

                Bundle extras = new Bundle();
                extras.putString("user",usuarioRequerido.getUsername());
                extras.putString("dispositivo",dispositivo.getNombreDispositivo());
//                activity.startService(new Intent(activity, ServiceMonitoreo.class).putExtra("dispositivo", dispositivo.getNombreDispositivo()));
                activity.startService(new Intent(activity, ServiceMonitoreo.class).putExtras(extras));

            }
        });
    }

    @Override
    public int getItemCount() {
        return dispositivos.size();
    }

    public class ParameterViewHolder extends RecyclerView.ViewHolder {
        MapView mapView;
        GoogleMap map;
        private ImageView imageView;
        private TextView textView;
        private TextView textView1;
        private TextView monitoreoButton;

        public ParameterViewHolder(View itemView) {
            super(itemView);

            textView=(TextView) itemView.findViewById(R.id.nombreDispositivo);

            monitoreoButton=(TextView) itemView.findViewById(R.id.buttonMonitoreo);
        }


        }

//    public Usuario getUser(){
//        ApiService apiService= ApiService.retrofit.create(ApiService.class);
//
//        retrofit2.Call<List<Usuario>> call= apiService.getUsuarios();
//
//        call.enqueue(new Callback<List<Usuario>>() {
//
//            @Override
//            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
//                System.out.println("Encontro una resuesta?????????");
//                Log.d("OnResponse ", response.body().toString());
//                Log.d("OnResponse ", response.body().toString());
//
//                usuarios = response.body();
//
//                for (Usuario usuario: usuarios) {
//                    for (int i=0;i<usuario.getListaDispositivos().size();i++) {
//                        if (usuario.getListaDispositivos().get(i).getDispositivo().getNombreDispositivo().equals(dispositivos.get(i).getNombreDispositivo())) {
//                            System.out.println("Encontro el maldito usuario");
//                            usuarioRequerido = usuario;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Usuario>> call, Throwable t) {
//                Log.e("failure", String.valueOf(t.getMessage()));
//            }
//        });
//
//        return usuarioRequerido;
//    }

}



