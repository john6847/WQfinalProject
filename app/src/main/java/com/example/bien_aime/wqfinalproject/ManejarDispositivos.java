package com.example.bien_aime.wqfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.bien_aime.wqfinalproject.adapter.DispositivosUsuarioRecyclerView;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ManejarDispositivos extends AppCompatActivity {

    List<Dispositivo> dispositivos=new ArrayList<>();
    List<Usuario> usuarios=new ArrayList<>();
    String usuarioLlegando;
    // ArrayList<Dispositivo> dispositivos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manejar_dispositivos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        show_toolbar(getResources().getString(R.string.toolbar_title_ListaDispositivo), true);

        Intent i = getIntent();
        dispositivos= (List<Dispositivo>) i.getSerializableExtra("dispositivos");
        usuarios= (List<Usuario>) i.getSerializableExtra("data");
        usuarioLlegando=i.getStringExtra("usuario");


        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.dispositivoProfileRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        DispositivosUsuarioRecyclerView dispositivosUsuarioRecyclerView=new DispositivosUsuarioRecyclerView(dispositivos,ManejarDispositivos.this);
        recyclerView.setAdapter(dispositivosUsuarioRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void show_toolbar(String titulo, boolean upBoton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor((getResources().getColor(R.color.toolbarTextColor)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBoton);
    }
}
