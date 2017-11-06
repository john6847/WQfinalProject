package com.example.bien_aime.wqfinalproject;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bien_aime.wqfinalproject.modelo.Dispositivo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EditDispositivoActivity extends AppCompatActivity {

    String dispositivoName;
    Dispositivo dispositivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dispositivo);

        Intent i = getIntent();
//        dispositivoName = i.getStringExtra("dispositivo");
        dispositivo = (Dispositivo) i.getSerializableExtra("dispositivo");

        //final TextView textView=(TextView) findViewById(R.id.tvDispositivoPersonal);
        final TextView pais=(TextView) findViewById(R.id.paisEdit);
        final TextView ciudad=(TextView) findViewById(R.id.ciudadEdit);
        final TextView sector=(TextView) findViewById(R.id.sectorEdit);
        final TextView calle=(TextView) findViewById(R.id.calleEdit);
//        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout1);

//        pais.setText(dispositivo.getDireccion().getSector().getCiudad().getPais().getNombrePais());
//        ciudad.setText(dispositivo.getDireccion().getSector().getCiudad().getNombreCiudad());
//        sector.setText(dispositivo.getDireccion().getSector().getNombreSector());
//        calle.setText(dispositivo.getDireccion().getCalle());

        final Button buttonGuardar = (Button) findViewById(R.id.guardarEditButton);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            //Your code goes here
                            HttpClient httpClient=new DefaultHttpClient();
                            HttpPost httpPost=new HttpPost("http://manueltm24.me:8080/API/EditarDispositivos/");

                            String json="{"+"id:"+dispositivo.getId() +",pais:"+pais.getText().toString()
                                    +",ciudad:"+ciudad.getText().toString()
                                    +",sector:"+sector.getText().toString()
                                    +",username:"+calle.getText().toString()+"}";

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
                                System.out.println("Responseee"+response.getStatusLine().getStatusCode());
                                //assertThat(response.getStatusLine().getStatusCode(), (Matcher<? super Integer>) equalTo(200));
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
    }
}
