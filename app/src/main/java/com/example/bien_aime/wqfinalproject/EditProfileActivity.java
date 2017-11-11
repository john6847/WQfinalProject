package com.example.bien_aime.wqfinalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.hamcrest.Matcher;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;
import com.example.bien_aime.wqfinalproject.modelo.Usuario;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.parceler.guava.base.Predicates.equalTo;


public class EditProfileActivity extends AppCompatActivity {

    List<Usuario> usuarios = new ArrayList<>();
    String usuarioLlegando;
    static long idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent i = getIntent();
        usuarios = (List<Usuario>) i.getSerializableExtra("usuariosEditable");
        usuarioLlegando = i.getStringExtra("usuario");


        final EditText username = (EditText) findViewById(R.id.usernameEdit);
        final EditText name = (EditText) findViewById(R.id.nombreEdit);
        final EditText telefono = (EditText) findViewById(R.id.telefonoEdit);
        final EditText email = (EditText) findViewById(R.id.emailEdit);
        final EditText sector = (EditText) findViewById(R.id.direccionSectorEdit);
        final EditText ciudad = (EditText) findViewById(R.id.direccionCiudadEdit);
        final EditText pais = (EditText) findViewById(R.id.direccionPaisEdit);
        final EditText calle = (EditText) findViewById(R.id.direccionCalleEdit);
        final Button buttonGuardar = (Button) findViewById(R.id.guardarEditUsuarioButton);

        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(usuarioLlegando)) {
                idUsuario =usuario.getId();

                name.setText(usuario.getNombre());
                email.setText("bencosky50@gmail.com");
                telefono.setText(usuario.getTelefono());
                ciudad.setText(usuario.getDireccion().getSector().getCiudad().getNombreCiudad());
                sector.setText(usuario.getDireccion().getSector().getNombreSector());
                pais.setText(usuario.getDireccion().getSector().getCiudad().getPais().getNombrePais());
                calle.setText(usuario.getDireccion().getCalle());
                username.setText(usuario.getUsername());
            }
        }

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost("http://manueltm24.me:8080/API/EditarProfile/");

                            System.out.println("Id usuario: "+idUsuario);
                            System.out.println("Id usuario: "+ name.getText().toString() );
                            System.out.println("Id usuario: "+telefono.getText().toString());
                            System.out.println("Id usuario: "+sector.getText().toString());
                            System.out.println("Id usuario: "+ciudad.getText().toString());
                            System.out.println("Id usuario: "+pais.getText().toString());
                            System.out.println("Id usuario: "+calle.getText().toString());

                            String json=
                                    "{"+"id:"+idUsuario
                                    +",nombre:" + name.getText().toString()
                                    + ",telefono:" + telefono.getText().toString()
                                    + ",sector:" + sector.getText().toString()
                                    + ",ciudad:" + ciudad.getText().toString()
                                    + ",pais:" + pais.getText().toString()
                                    + ",calle:" + calle.getText().toString()
                                    + "}";


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
                                System.out.println("Responseee " + response.getStatusLine().getStatusCode());
                                System.out.println("Responseee " + Arrays.toString(response.getAllHeaders()));
                                System.out.println("Responseee " + response.getStatusLine().getReasonPhrase());

                                BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                                StringBuilder total = new StringBuilder();

                                String line = null;

                                while ((line = r.readLine()) != null) {
                                    total.append(line);
                                }
                                r.close();
                                System.out.println("88888888888888888888888888888888888"+total.toString());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();

                Toast.makeText(EditProfileActivity.this, "Guardado", Toast.LENGTH_LONG).show();
//                Snackbar.make(view, "If you edit your profile You will have to get back to the log Screen to notice the changes", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent loginScreen = new Intent(EditProfileActivity.this, ProfileActivity.class);
                loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                Toast.makeText(EditProfileActivity.this, "WELCOME TO LOGINSCREEN", Toast.LENGTH_SHORT).show();
//                EditProfileActivity.this.supportFinishAfterTransition();
                startActivity(loginScreen.putExtra("usuario", usuarioLlegando).putExtra("data", (Serializable) usuarios));
            }
        });
    }
}