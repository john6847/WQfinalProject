package com.example.bien_aime.wqfinalproject.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bien_aime.wqfinalproject.DispositivoPersonaActivity;
import com.example.bien_aime.wqfinalproject.MonitoreoActivity;
import com.example.bien_aime.wqfinalproject.R;
import com.example.bien_aime.wqfinalproject.Servicios.DownloadResultReceiver;
import com.example.bien_aime.wqfinalproject.Servicios.ServiceMonitoreo;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.List;

import static com.example.bien_aime.wqfinalproject.R.layout.cardviw_dispositivos;

/**
 * Created by Bien-aime on 9/21/2017.
 */

public class DispositivosUsuarioRecyclerView extends RecyclerView.Adapter<DispositivosUsuarioRecyclerView.DispositivoViewHolder> {
    private List<Dispositivo> dispositivos;
    private Activity activity;


    public DispositivosUsuarioRecyclerView(List<Dispositivo> dispositivos, Activity activity) {
        this.dispositivos = dispositivos;
//        this.ressources = ressources;
        this.activity = activity;
    }

    @Override
    public DispositivoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(cardviw_dispositivos, parent, false);
        return new DispositivosUsuarioRecyclerView.DispositivoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DispositivoViewHolder holder, int position) {
        final Dispositivo dispositivo = dispositivos.get(position);
        holder.textView.setText(dispositivo.getNombreDispositivo());
       // holder.textView1.setText(String.valueOf(dispositivo.getId()));
        //holder.imageView.setImageResource(Integer.parseInt(muestra.getPicture()));

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.startActivity(new Intent(activity, DispositivoPersonaActivity.class).putExtra("dispositivo", dispositivo.getNombreDispositivo()));
                //activity.startService(new Intent(activity, ServiceMonitoreo.class).putExtra("dispositivo", dispositivo.getNombreDispositivo()));
                //activity.startService(new Intent(activity, MyIntentService.class).putExtra("dispositivo",dispositivo.getNombreDispositivo()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dispositivos.size();
    }

    public class DispositivoViewHolder extends RecyclerView.ViewHolder {
        MapView mapView;
        GoogleMap map;
        private ImageView imageView;
        private TextView textView;
        //private TextView textView1;

        public DispositivoViewHolder(View itemView) {
            super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.imageDispositivo);
            textView=(TextView) itemView.findViewById(R.id.nombreDispositivo);
            //textView1=(TextView) itemView.findViewById(R.id.locationDispositivo);
        }
    }
}
