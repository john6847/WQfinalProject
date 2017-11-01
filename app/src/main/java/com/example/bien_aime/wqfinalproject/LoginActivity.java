package com.example.bien_aime.wqfinalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    ProgressDialog mDialog;
   // ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ApiService apiService= ApiService.retrofit.create(ApiService.class);
//        final retrofit2.Call<List<Usuario>> call= apiService.getUsuarios();
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.passwrd);
        final Button buttonLogin = (Button) findViewById(R.id.loginButton);
        mDialog = new ProgressDialog(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {

                            HttpClient httpClient=new DefaultHttpClient();
                            HttpPost httpPost=new HttpPost("http://manueltm24.me:8080/API/logear");
                            //Recordar que la primera ver no hay id para ese usuario(No puedo buscar por Id)
                            //Se buscar por username ya que es unico
                            String json="{"+"id:"+ 1 +",username:"+username.getText().toString()+",password:"+password.getText().toString()+"}";

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

                                if(response.getStatusLine().toString().equals("no valid password")){
                                    System.out.println("Password is not valid");
                                }else{
                                    System.out.println("Status Lineeeee: "+response.getStatusLine());
                                    String jsonString = EntityUtils.toString(response.getEntity());
                                    final JSONObject jsonObj = new JSONObject(jsonString);
                                    System.out.println("Status Lineeeee: "+jsonString);
                                    System.out.println("Status Lineeeee: "+jsonObj.get("username"));

                                    if (TextUtils.isEmpty(username.getText().toString())) {
                                        Toast.makeText(LoginActivity.this, "Ingresar un correo Electronico", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else if (TextUtils.isEmpty(password.getText().toString())) {
                                        Toast.makeText(LoginActivity.this, "Ingresar una contrasena", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else if (password.getText().toString().length() < 3) {
                                        Toast.makeText(LoginActivity.this, "La contrasena debe ser mayor que 6 caracteres", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                        //System.out.println("---------------------------------))))))"+usuario.getDispositivos().get(0).getNombreDispositivo());

                                    LoginActivity.this.runOnUiThread(new Runnable() {
                                        public void run() {
                                            mDialog.setMessage("Estas Logeando...");
                                            mDialog.setCanceledOnTouchOutside(false);
                                            mDialog.show();

                                            try {
                                                startActivity(new Intent(LoginActivity.this, HomeActivity.class).putExtra("user", (String) jsonObj.get("username")));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                            }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

//        buttonLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //lLAMAMOS A LA FUNCION GETuSUARIOS
//                //apiService=APIclient.getClient().create(ApiService.class);
//
//                //La cambie de lugar para verificar porque esta tomando tantos tiempos
//                ApiService apiService= ApiService.retrofit.create(ApiService.class);
//                final retrofit2.Call<List<Usuario>> call= apiService.getUsuarios();
//
//                call.enqueue(new Callback<List<Usuario>>() {
//                    @Override
//                    public void onResponse(retrofit2.Call<List<Usuario>> call, Response<List<Usuario>> response) {
//                        List<Usuario> usuarios = response.body();
////                        System.out.println("---------------" + usuarios.get(0).getApellido());
//                        System.out.println("Thereeee");
//
//                        for (Usuario usuario : usuarios) {
//                            if (TextUtils.isEmpty(username.getText().toString())) {
//                                Toast.makeText(LoginActivity.this, "Ingresar un correo Electronico", Toast.LENGTH_SHORT).show();
//                                return;
//                            } else if (TextUtils.isEmpty(password.getText().toString())) {
//                                Toast.makeText(LoginActivity.this, "Ingresar una contrasena", Toast.LENGTH_SHORT).show();
//                                return;
//                            } else if (password.getText().toString().length() < 3) {
//                                Toast.makeText(LoginActivity.this, "La contrasena debe ser mayor que 6 caracteres", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            if (username.getText().toString().equals(usuario.getUsername()) && password.getText().toString().equals(usuario.getContrasena())) {
//                                System.out.println("Siiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
//
//                                //System.out.println("---------------------------------))))))"+usuario.getDispositivos().get(0).getNombreDispositivo());
//                                mDialog.setMessage("Estas Logeando...");
//                                mDialog.setCanceledOnTouchOutside(false);
//                                mDialog.show();
//
//                                startActivity(new Intent(LoginActivity.this, HomeActivity.class).putExtra("user",usuario.getUsername()));
//
//                            } else {
//                                mDialog.dismiss();
//                                Toast.makeText(LoginActivity.this, "Error al logear", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(retrofit2.Call<List<Usuario>> call, Throwable t) {
//                        Toast.makeText(LoginActivity.this,"La aplicacion no encuentra ese usuario", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//        });


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.nav_logout){


        }else if (id == R.id.nav_cuenta){

        }else if (id == R.id.nav_mensaje) {

        }else if (id == R.id.nav_map) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}