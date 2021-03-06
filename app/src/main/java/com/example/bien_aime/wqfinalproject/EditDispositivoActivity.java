package com.example.bien_aime.wqfinalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import static com.example.bien_aime.wqfinalproject.Servicios.ProveerPaises.countryStr;
import static com.example.bien_aime.wqfinalproject.Servicios.ProveerPaises.sector_Santiago;
import static com.example.bien_aime.wqfinalproject.Servicios.ProveerPaises.states_RepublicaDominicana;

public class EditDispositivoActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    String dispositivoName;
    Dispositivo dispositivo;
    ProgressDialog mDialog;

    private Spinner country;
    private Spinner city;
    private Spinner sector;

    String GuardarCiudad;
    String GuardarSector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dispositivo);

        Intent i = getIntent();
        mDialog = new ProgressDialog(this);
        dispositivo = (Dispositivo) i.getSerializableExtra("dispositivo");

//        final TextView sector=(TextView) findViewById(R.id.sectorEdit);
        final TextView calle=(TextView) findViewById(R.id.calleEdit);

        country = (Spinner) findViewById(R.id.spinnerCountryDispositivo);
        country.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter <String> c = new ArrayAdapter <String> (this,android.R.layout.simple_spinner_item,countryStr);
        c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(c);

        city=(Spinner)findViewById(R.id.spinnerCityDispositivo);

        city.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        city.setEnabled(true);

        sector=(Spinner)findViewById(R.id.spinnerSectorDispositivo);
        sector.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        sector.setEnabled(true);

        if(dispositivo.getDireccion().getCalle()!=null || dispositivo.getDireccion().getSector()!=null) {

            String  CompareValue= dispositivo.getDireccion().getSector().getCiudad().getPais().getNombrePais();

            if (!CompareValue.equals(null)) {
                int SpinnerPostion = c.getPosition(CompareValue);
                country.setSelection(SpinnerPostion);
                SpinnerPostion = 0;
            }

//            sector.setText(dispositivo.getDireccion().getSector().getNombreSector());
            calle.setText(dispositivo.getDireccion().getCalle());
            GuardarCiudad= dispositivo.getDireccion().getSector().getCiudad().getNombreCiudad();
            GuardarSector=dispositivo.getDireccion().getSector().getNombreSector();
        }

        final Button buttonGuardar = (Button) findViewById(R.id.guardarEditButton);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(
                    city.getSelectedItem()==null || country.getSelectedItem().toString().equals("Seleccionar Pais") ||
                    calle.getText().toString().trim().equals("")) {
                        country.setPrompt("Debe Completar todos los campos");
                        city.setPrompt("Debe Completar todos los campos");
                        calle.setError("Debe completar todos los campos!!!");
                }else {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                //Your code goes here
                                HttpClient httpClient = new DefaultHttpClient();
                                HttpPost httpPost = new HttpPost("https://waterquality.pionot.com/API/editarDispositivoDireccion/");

                                String json = "{" + "id:" + dispositivo.getId()
                                        +",sector:"+sector.getSelectedItem().toString()
//                                        + ",sector:" + sector.getText().toString() + ",ciudad:"
                                        + ",ciudad:"+city.getSelectedItem().toString() + ",pais:" + country.getSelectedItem().toString()
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
                                    HttpResponse response = httpClient.execute(httpPost);
                                    System.out.println("Responseee" + response.getStatusLine().getStatusCode());
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


                Intent loginScreen = new Intent(EditDispositivoActivity.this, DispositivoPersonaActivity.class);
                loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(loginScreen.putExtra("dispositivo", dispositivo.getNombreDispositivo()));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }


    public void onItemSelected(final AdapterView<?> parent, View view, int position, long id)
    {
        city.setEnabled(true);
        ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        ((TextView) parent.getChildAt(0)).setTextSize(18);

        switch(parent.getId())
        {
            case R.id.spinnerCountryDispositivo:
                city.setEnabled(true);
                if(country.getSelectedItem().equals("Republica Dominicana")) {
                    ArrayAdapter<String> s1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states_RepublicaDominicana);
                    s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    city.setAdapter(s1);

                    String CompareValue2 = GuardarCiudad;

                    if (GuardarCiudad != null) {
                        if (!CompareValue2.equals(null)) {
                            int SpinnerPostion = s1.getPosition(CompareValue2);
                            city.setSelection(SpinnerPostion);
                            SpinnerPostion = 0;
                        }
                    }

                    city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View view,int position, long id) {


                            ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                            if (city.getSelectedItem().equals("Santiago de los Caballeros")) {
                                ArrayAdapter<String> s11 = new ArrayAdapter<String>(EditDispositivoActivity.this, android.R.layout.simple_spinner_item, sector_Santiago);
                                s11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sector.setAdapter(s11);

                                String CompareValue3 = GuardarSector;

                                if (GuardarSector != null) {
                                    if (!CompareValue3.equals(null)) {
                                        int SpinnerPostion = s11.getPosition(CompareValue3);
                                        sector.setSelection(SpinnerPostion);
                                        SpinnerPostion = 0;
                                    }
                                }
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }
                    });


                }
                else  if(country.getSelectedItem().equals("Pakistan"))
                {
//                    ArrayAdapter <String> s2 = new ArrayAdapter <String> (this,android.R.layout.simple_spinner_item,states_pak);
//                    s2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    city.setAdapter(s2);
                }
                else  if(country.getSelectedItem().equals("China"))
                {
//                    ArrayAdapter <String> s3 = new ArrayAdapter <String> (this,android.R.layout.simple_spinner_item,states_china);
//                    s3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    city.setAdapter(s3);
                }
                break;

            case R.id.spinnerCityDispositivo:
                String cityStr1=city.getSelectedItem().toString();
                Log.e("city1",cityStr1);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
        city.setEnabled(true);
    }

}
