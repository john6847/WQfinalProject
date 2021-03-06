package com.example.bien_aime.wqfinalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.bien_aime.wqfinalproject.Servicios.ServiceMonitoreo;
import com.example.bien_aime.wqfinalproject.Servicios.ServicioNotificacion;
import com.example.bien_aime.wqfinalproject.adapter.DispositivoRecycleView;
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
import java.util.Arrays;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class LoginActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    ProgressDialog mDialog;
    public static final String PREFS_NAME = "com.example.bien_aime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.passwrd);
        final Button buttonLogin = (Button) findViewById(R.id.loginButton);
        mDialog = new ProgressDialog(this);


        SharedPreferences lector = getDefaultSharedPreferences(getApplicationContext());




        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( username.getText().toString().trim().equals("")){
                    username.setError( "Se requiere que introducza el nombre de usuario!" );
                }else if( password.getText().toString().trim().equals("")){
                    password.setError( "Se requiere que introducza el nombre de usuario!" );
                }else {

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {

                                HttpClient httpClient = new DefaultHttpClient();
                                HttpPost httpPost = new HttpPost("https://waterquality.pionot.com/API/logear");
                                String json = "{" + "username:" + username.getText().toString() + ",password:" + password.getText().toString() + "}";

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

                                    if (Arrays.toString(response.getAllHeaders()).contains("[X-Application-Context: application:development, Content-Type: text/html;charset=utf-8")) {
                                        LoginActivity.this.runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(LoginActivity.this, "Problema al tratar de logearse", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {


                                        LoginActivity.this.runOnUiThread(new Runnable() {
                                            public void run() {
                                                mDialog.setMessage("Estas Logeando...");
                                                mDialog.setCanceledOnTouchOutside(false);
                                                mDialog.setProgress(7);
                                                mDialog.show();
                                            }
                                        });

                                        System.out.println("Status Lineeeee: " + Arrays.toString(response.getAllHeaders()));
                                        String jsonString = EntityUtils.toString(response.getEntity());

                                        final JSONObject jsonObj = new JSONObject(jsonString);
                                        System.out.println("Status Lineeeee: " + jsonString);
                                        System.out.println("Status Lineeeee: " + jsonObj.get("username"));

                                        if (TextUtils.isEmpty(username.getText().toString())) {
                                            LoginActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(LoginActivity.this, "Ingresar un nombre de usuario", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            return;
                                        } else if (TextUtils.isEmpty(password.getText().toString())) {
                                            LoginActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(LoginActivity.this, "Ingresar una contrasena", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            return;
                                        } else if (password.getText().toString().length() < 3) {
                                            LoginActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(LoginActivity.this, "La contrasena debe ser mayor que 6 caracteres", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            return;
                                        }
                                        LoginActivity.this.runOnUiThread(new Runnable() {
                                            public void run() {

                                                try {

                                                    startActivity(new Intent(LoginActivity.this, WelcomeActivity.class).putExtra("user", (String) jsonObj.get("username")));
                                                    new Intent(LoginActivity.this, ServiceMonitoreo.class).putExtra("user", (String) jsonObj.get("username"));
                                                    new Intent(LoginActivity.this, DispositivoRecycleView.class).putExtra("user", (String) jsonObj.get("username"));
                                                    startService(new Intent(LoginActivity.this, ServicioNotificacion.class).putExtra("user", (String) jsonObj.get("username")));

//                                                startActivity(new Intent(LoginActivity.this, HomeActivity.class).putExtra("user", (String) jsonObj.get("username")));
//                                                new Intent(LoginActivity.this, ServiceMonitoreo.class).putExtra("user", (String) jsonObj.get("username"));
//                                                new Intent(LoginActivity.this, DispositivoRecycleView.class).putExtra("user", (String) jsonObj.get("username"));
//                                                startService(new Intent(LoginActivity.this, ServicioNotificacion.class).putExtra("user", (String) jsonObj.get("username")));

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
            }
        });
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
