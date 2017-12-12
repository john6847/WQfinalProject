package com.example.bien_aime.wqfinalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.bien_aime.wqfinalproject.ModeloDB.Muestras;
import com.example.bien_aime.wqfinalproject.adapter.ParameterRecyclerView;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;

import java.util.ArrayList;
import java.util.List;

public class verFullMuestra extends AppCompatActivity {

    List<Muestras> muestrasLLegando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_full_muestra);

        Intent i = getIntent();
        muestrasLLegando= (List<Muestras>) i.getSerializableExtra("muestras");

        show_toolbar("Muestra Completa",false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pictureRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

//        Button regresar=(Button)findViewById(R.id.load_regresar);

        ParameterRecyclerView parameterRecycler = new ParameterRecyclerView(muestrasLLegando, verFullMuestra.this);
        recyclerView.setAdapter(parameterRecycler);
        recyclerView.setLayoutManager(linearLayoutManager);

//        regresar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

    public void show_toolbar(String titulo, boolean upBoton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor((getResources().getColor(R.color.toolbarTextColor)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBoton);
    }
}
