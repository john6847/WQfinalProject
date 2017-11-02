package com.example.bien_aime.wqfinalproject;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.bien_aime.wqfinalproject.adapter.DispositivoRecycleView;
import com.example.bien_aime.wqfinalproject.adapter.DispositivosUsuarioRecyclerView;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
//    List<Dispositivo> dispositivos=new ArrayList<>();
    List<Usuario> usuarios=new ArrayList<>();
    String usuarioLlegando;
   // ArrayList<Dispositivo> dispositivos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       // show_toolbar("",false);

        Intent i = getIntent();
//        dispositivos= (List<Dispositivo>) i.getSerializableExtra("dispositivos");
        usuarios= (List<Usuario>) i.getSerializableExtra("data");
        usuarioLlegando=i.getStringExtra("usuario");

        final TextView nombre= (TextView) findViewById(R.id.tvNumber1);
        final TextView sexo= (TextView) findViewById(R.id.tvNumber2);
        final TextView telefono= (TextView) findViewById(R.id.tvTelefono);
        final TextView ciudad= (TextView) findViewById(R.id.tvCiudad);
        final TextView sector= (TextView) findViewById(R.id.tvSector);
        final TextView pais= (TextView) findViewById(R.id.tvPais);
        final TextView direccion= (TextView) findViewById(R.id.tvdireccion);

        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

//        System.out.println("====================================@@@@@"+dispositivos.size());
        for (Usuario usuario: usuarios){
            if(usuario.getUsername().equals(usuarioLlegando)){
                nombre.setText(usuario.getNombre()+" "+usuario.getApellido());

                telefono.setText(usuario.getTelefono());
                direccion.setText(usuario.getDireccion().toString());
                sector.setText(usuario.getDireccion().getSector().getNombreSector());
                ciudad.setText(usuario.getDireccion().getSector().getCiudad().getNombreCiudad());
                pais.setText(usuario.getDireccion().getSector().getCiudad().getPais().getNombrePais());
                collapsingToolbarLayout.setTitle(usuario.getUsername());
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ProfileActivity.this,EditProfileActivity.class);
                intent.putExtra("usuariosEditable",(Serializable) usuarios);
                intent.putExtra("usuario",usuarioLlegando);
                startActivity(intent);
                Snackbar.make(view, "If you edit your profile You will have to get back to the log Screen to notice the changes", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.dispositivoProfileRecycler);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        DispositivosUsuarioRecyclerView dispositivosUsuarioRecyclerView=new DispositivosUsuarioRecyclerView(dispositivos,ProfileActivity.this);
//        recyclerView.setAdapter(dispositivosUsuarioRecyclerView);
//        recyclerView.setLayoutManager(linearLayoutManager);

    }

}