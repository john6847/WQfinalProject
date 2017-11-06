package com.example.bien_aime.wqfinalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Switch;
import android.widget.TextView;

import com.example.bien_aime.wqfinalproject.adapter.DispositivoRecycleView;
import com.example.bien_aime.wqfinalproject.adapter.DispositivosUsuarioRecyclerView;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    List<Usuario> usuarios=new ArrayList<>();
    String usuarioLlegando;
    Usuario usuarioFinal=new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
//        dispositivos= (List<Dispositivo>) i.getSerializableExtra("dispositivos");
        usuarios= (List<Usuario>) i.getSerializableExtra("data");
        usuarioLlegando=i.getStringExtra("usuario");

        final TextView nombre= (TextView) findViewById(R.id.tvNumber1);
        final TextView telefono= (TextView) findViewById(R.id.tvTelefono);
        final TextView ciudad= (TextView) findViewById(R.id.tvCiudad);
        final TextView sector= (TextView) findViewById(R.id.tvSector);
        final TextView pais= (TextView) findViewById(R.id.tvPais);
        final TextView direccion= (TextView) findViewById(R.id.tvdireccion);

        final Switch simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);
//        simpleSwitch.setTextOn("SI");
//        simpleSwitch.setTextOff("NO");


//        Boolean switchState = simpleSwitch.isChecked();

        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        for (Usuario usuario: usuarios){
            if(usuario.getUsername().equals(usuarioLlegando)){
                nombre.setText(usuario.getNombre());

                usuarioFinal=usuario;
                telefono.setText(usuario.getTelefono());
                direccion.setText(usuario.getDireccion().getCalle());
                sector.setText(usuario.getDireccion().getSector().getNombreSector());
                ciudad.setText(usuario.getDireccion().getSector().getCiudad().getNombreCiudad());
                pais.setText(usuario.getDireccion().getSector().getCiudad().getPais().getNombrePais());
                collapsingToolbarLayout.setTitle(usuario.getUsername());

                if(!usuario.getSilenciarNotificacion()){
                    simpleSwitch.setChecked(false);
                }else {
                    simpleSwitch.setChecked(true);
                }
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


        simpleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(simpleSwitch.isChecked()){
                    System.out.println("No Estaba checked y no estaba silenciada");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                    builder.setTitle("Advertencia").setIcon(R.drawable.silenciar);

                    builder.setMessage("Si presiona Ok, no le va a llegar ninguna notificacion de esa aplicacion!!!")
                            .setCancelable(false)
                            .setPositiveButton("Silenciar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Thread thread = new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            try {

                                                HttpClient httpClient = new DefaultHttpClient();
                                                HttpPost httpPost = new HttpPost("http://manueltm24.me:8080/API/silenciarNotificacion");
                                                String json = "{" + "id:" + usuarioFinal.getId() + ",silenciarNotificacion:" + "true" + "}";

                                                StringEntity entity = null;
                                                try {
                                                    entity = new StringEntity(json);
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                                httpPost.setEntity(entity);
                                                httpPost.setHeader("Accept", "application/json");
                                                httpPost.setHeader("Content-type", "application/json");

                                                try {
                                                    HttpResponse response = httpClient.execute(httpPost);
                                                    System.out.println("Responseee " + response.getStatusLine().getStatusCode());

                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    thread.start();
                                    simpleSwitch.setChecked(true);

                                }
                                });
                                final AlertDialog alert = builder.create();
                                alert.setOnShowListener( new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface arg0) {
                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary1));
                                    }
                                });
                                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        simpleSwitch.setChecked(false);
                                        dialog.dismiss();
                                    }
                            });
                                alert.setOnShowListener( new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface arg0) {
                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary1));
                                    }
                                });
                    alert.show();

                }else if(!simpleSwitch.isChecked()){
                    System.out.println("Estaba chequeada y silenciada");

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                HttpClient httpClient=new DefaultHttpClient();
                                HttpPost httpPost=new HttpPost("http://manueltm24.me:8080/API/silenciarNotificacion");
                                String json="{"+"id:"+usuarioFinal.getId()+",silenciarNotificacion:"+"false"+"}";

                                StringEntity entity = null;
                                try {
                                    entity = new StringEntity(json);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                httpPost.setEntity(entity);
                                httpPost.setHeader("Accept", "application/json");
                                httpPost.setHeader("Content-type", "application/json");

                                try {
                                    HttpResponse response=httpClient.execute(httpPost);
                                    System.out.println("Responseee "+response.getStatusLine().getStatusCode());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    simpleSwitch.setChecked(false);
                }
            }
        });
    }
}