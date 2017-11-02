package com.example.bien_aime.wqfinalproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.NonNull;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.adapter.DispositivoRecycleView;
import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //JSONArray jsonArray;
   // private static String username;
    TextView textView;
    String usuarioLlegando;
    List<Dispositivo> dispositivos=new ArrayList<>();
    List<Usuario> usuarios=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        show_toolbar(getResources().getString(R.string.homeToolbar),false);

       // final String username= getIntent().getStringExtra("username");
        //Recibo aqui el objeto json array que contiene toda la informacion del usuario incluyendo los dispositivos de ese usuario
        Intent i = getIntent();
        usuarioLlegando = i.getStringExtra("user");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ApiService apiService= ApiService.retrofit.create(ApiService.class);

        retrofit2.Call<List<Usuario>> call= apiService.getUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Log.d("OnResponse ", response.body().toString());
                Log.d("OnResponse ", response.body().toString());

                System.out.println("Bueno "+response.body());

                usuarios = response.body();
                System.out.println("Usuariossssssssssssssss "+usuarios.get(0).getUsername());

/*                for (Usuario usuario: usuarios){
                    if (usuario.getUsername().equals("admin")) {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+usuario.getListaDispositivos().get(0).getDispositivo().getNombreDispositivo());
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+usuario.getDispositivos());
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+usuario.getDireccion().getSector().getCiudad().getPais().getNombrePais());
//                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+usuario.getListaDispositivos().get(0).getDispositivo().getNombreDispositivo());

//                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + usuario.getDispositivos().get(0));
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + usuario.getUsername());
                    }
                }*/

                for (Usuario usuario: usuarios){
                    if (usuario.getUsername().equals(usuarioLlegando)){
                        for (int j=0;j<usuario.getListaDispositivos().size();j++){
                            System.out.println("-------------------------------- Anndan la");
                            dispositivos.add(new Dispositivo(usuario.getListaDispositivos().get(j).getDispositivo().getId(),usuario.getListaDispositivos().get(j).getDispositivo().getNombreDispositivo(), usuario.getListaDispositivos().get(j).getDispositivo().getDescripcion()));
                        }
                        System.out.println("--------------"+dispositivos.size());;
                    }
                }

                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+dispositivos.size());
                RecyclerView recyclerView=(RecyclerView) findViewById(R.id.dispositivoRecycler);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                DispositivoRecycleView dispositivoRecycleView=new DispositivoRecycleView(dispositivos,HomeActivity.this);
                recyclerView.setAdapter(dispositivoRecycleView);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("failure", String.valueOf(t.getMessage()));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_logout) {

            Intent loginScreen = new Intent(HomeActivity.this, LoginActivity.class);
            loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(HomeActivity.this, "WELCOME TO LOGINSCREEN", Toast.LENGTH_SHORT).show();
            this.finish();
            startActivity(loginScreen);

        } else if (id == R.id.nav_perfil) {

            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);

            intent.putExtra("dispositivos", (Serializable)dispositivos);
            intent.putExtra("usuario",usuarioLlegando);
            intent.putExtra("data", (Serializable) usuarios);

            startActivity(intent);
//            intent.putExtra("dispositivos", usuarioLlegando);
//            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(HomeActivity.this, ManejarDispositivos.class);

            intent.putExtra("dispositivos", (Serializable)dispositivos);
            intent.putExtra("usuario",usuarioLlegando);
            intent.putExtra("data", (Serializable) usuarios);

            startActivity(intent);

//            Intent seachIntent = new Intent(MainActivity.this, ChatMessages.class);
//            startActivity(seachIntent);
        } else if (id == R.id.nav_map) {
//            Intent seachIntent = new Intent(MainActivity.this, MapsActivity.class);
//            startActivity(seachIntent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void show_toolbar(String titulo, boolean upBoton){
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor((getResources().getColor(R.color.toolbarTextColor)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBoton);
    }
}
