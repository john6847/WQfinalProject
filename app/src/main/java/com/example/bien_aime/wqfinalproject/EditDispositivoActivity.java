package com.example.bien_aime.wqfinalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dispositivo);

        Intent i = getIntent();
        mDialog = new ProgressDialog(this);
//        dispositivoName = i.getStringExtra("dispositivo");
        dispositivo = (Dispositivo) i.getSerializableExtra("dispositivo");

        //final TextView textView=(TextView) findViewById(R.id.tvDispositivoPersonal);
        final TextView pais=(TextView) findViewById(R.id.paisEdit);
        final TextView ciudad=(TextView) findViewById(R.id.ciudadEdit);
        final TextView sector=(TextView) findViewById(R.id.sectorEdit);
        final TextView calle=(TextView) findViewById(R.id.calleEdit);
//        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout1);

        if(dispositivo.getDireccion().getCalle()!=null || dispositivo.getDireccion().getSector()!=null || dispositivo.getDireccion().getSector().getCiudad()!=null || dispositivo.getDireccion().getSector().getCiudad().getPais()!=null) {

            pais.setText(dispositivo.getDireccion().getSector().getCiudad().getPais().getNombrePais());
            ciudad.setText(dispositivo.getDireccion().getSector().getCiudad().getNombreCiudad());
            sector.setText(dispositivo.getDireccion().getSector().getNombreSector());
            calle.setText(dispositivo.getDireccion().getCalle());
        }


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
                            HttpPost httpPost=new HttpPost("http://manueltm24.me:8080/API/editarDispositivoDireccion/");

                            String json="{"+"id:"+dispositivo.getId()
                                    + ",sector:" + sector.getText().toString() + ",ciudad:"
                                    + ciudad.getText().toString() + ",pais:" + pais.getText().toString()
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

                Toast.makeText(EditDispositivoActivity.this, "Guardado", Toast.LENGTH_LONG).show();
//                Snackbar.make(view, "If you edit your profile You will have to get back to the log Screen to notice the changes", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent loginScreen = new Intent(EditDispositivoActivity.this, DispositivoPersonaActivity.class);
                loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                Toast.makeText(EditDispositivoActivity.this, "WELCOME TO LOGINSCREEN", Toast.LENGTH_SHORT).show();
//                EditDispositivoActivity.this.finish();
                mDialog.setMessage("Estas Logeando...");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setProgress(7);
                mDialog.show();
                startActivity(loginScreen.putExtra("dispositivo", dispositivo.getNombreDispositivo()));


            }
        });
    }
}
