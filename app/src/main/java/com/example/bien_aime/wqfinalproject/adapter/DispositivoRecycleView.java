package com.example.bien_aime.wqfinalproject.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bien_aime.wqfinalproject.MonitoreoActivity;
import com.example.bien_aime.wqfinalproject.R;
import com.example.bien_aime.wqfinalproject.ReferenceActivity;
import com.example.bien_aime.wqfinalproject.Servicios.DownloadResultReceiver;
import com.example.bien_aime.wqfinalproject.Servicios.MyIntentService;
import com.example.bien_aime.wqfinalproject.Servicios.ServiceMonitoreo;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.bien_aime.wqfinalproject.R.layout.cardviw_dispositivos;


/**
 * Created by Bien-aime on 8/23/2017.
 */

public class DispositivoRecycleView extends RecyclerView.Adapter<DispositivoRecycleView.ParameterViewHolder> {
    private List<Dispositivo> dispositivos;
    private Activity activity;
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
        holder.textView.setText(dispositivo.getNombreDispositivo());
        holder.textView1.setText(String.valueOf(dispositivo.getId()));
        //holder.imageView.setImageResource(Integer.parseInt(muestra.getPicture()));

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // activity.startActivity(new Intent(activity, ReferenceActivity.class).putExtra("dispositivo",dispositivo.getNombreDispositivo()));
                //activity.startService(new Intent(activity, MyIntentService.class).putExtra("dispositivo",dispositivo.getNombreDispositivo()));
                activity.startActivity(new Intent(activity, MonitoreoActivity.class).putExtra("dispositivo", dispositivo.getNombreDispositivo()));
                activity.startService(new Intent(activity, ServiceMonitoreo.class).putExtra("dispositivo", dispositivo.getNombreDispositivo()));

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

        public ParameterViewHolder(View itemView) {
            super(itemView);
//            // Gets the MapView from the XML layout and creates it
//            mapView = (MapView) itemView.findViewById(R.id.mapview);
//
//            // Gets to GoogleMap from the MapView and does initialization stuff
//
//            map.getUiSettings().setMyLocationButtonEnabled(false);
//            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                map.setMyLocationEnabled(true);
//            }

            imageView=(ImageView) itemView.findViewById(R.id.imageDispositivo);
            textView=(TextView) itemView.findViewById(R.id.nombreDispositivo);
            textView1=(TextView) itemView.findViewById(R.id.locationDispositivo);
        }

//        public void bind(final Dispositivo dispositivo,final OnItemCLickedListener listener){
//            this.textView.setText(dispositivo.getNombre());
//            this.textView1.setText(dispositivo.getLocation());

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onItemClicked(dispositivo,getAdapterPosition());
//                }
//            });
        }

    }

//    public interface OnItemCLickedListener{
//        void onItemClicked(Dispositivo dispositivo,int position);
//    }
//}

