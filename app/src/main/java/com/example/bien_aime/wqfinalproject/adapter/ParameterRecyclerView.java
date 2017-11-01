package com.example.bien_aime.wqfinalproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bien_aime.wqfinalproject.ModeloDB.MuestraSQLiteHelper;
import com.example.bien_aime.wqfinalproject.ModeloDB.Muestras;
import com.example.bien_aime.wqfinalproject.R;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.bien_aime.wqfinalproject.R.layout.cardview_parameter;


/**
 * Created by Bien-aime on 8/23/2017.
 */

public class ParameterRecyclerView extends RecyclerView.Adapter<ParameterRecyclerView.ParameterViewHolder>  {
    private List<Muestras> muestras;
//    private int ressources;
    private Activity activity;

    public ParameterRecyclerView(List<Muestras> muestras,Activity activity) {
        this.muestras = muestras;
//        this.ressources = ressources;
        this.activity = activity;
    }

    @Override
    public ParameterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(cardview_parameter,parent,false);
        return new ParameterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParameterViewHolder holder, int position) {
        Muestras muestra=muestras.get(position);

                holder.textView.setText(muestra.getTitulo());
                holder.textView1.setText(String.valueOf(muestra.getCantidad()));
                holder.textView3.setText(String.valueOf(muestra.getFecha()));
                Picasso.with(activity).load(muestra.getPicture()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return muestras.size();
    }

    public class ParameterViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        private TextView textView1;
        private TextView textView3;

        public ParameterViewHolder(View itemView) {
            super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.imageParameter);
            textView=(TextView) itemView.findViewById(R.id.nombreParametro);
            textView1=(TextView) itemView.findViewById(R.id.cantidadParametro);
            textView3=(TextView) itemView.findViewById(R.id.fechaParametro);

        }


    }
}
