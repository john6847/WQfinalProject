package com.example.bien_aime.wqfinalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.hamcrest.Matcher;

import static com.example.bien_aime.wqfinalproject.Servicios.ProveerPaises.countryStr;
import static com.example.bien_aime.wqfinalproject.Servicios.ProveerPaises.states_RepublicaDominicana;
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
import java.util.Collections;
import java.util.List;
import java.util.Locale;


import static org.parceler.guava.base.Predicates.equalTo;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    List<Usuario> usuarios = new ArrayList<>();
    Usuario usuario;
    static long idUsuario;

    String GuardarCiudad;

    private Spinner country;
    private Spinner city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent i = getIntent();
        usuarios = (List<Usuario>) i.getSerializableExtra("usuariosEditable");
        usuario = (Usuario) i.getSerializableExtra("usuario");


        final EditText username = (EditText) findViewById(R.id.usernameEdit);
        final EditText name = (EditText) findViewById(R.id.nombreEdit);
        final EditText telefono = (EditText) findViewById(R.id.telefonoEdit);
        final EditText email = (EditText) findViewById(R.id.emailEdit);
        final EditText sector = (EditText) findViewById(R.id.direccionSectorEdit);
        final EditText calle = (EditText) findViewById(R.id.direccionCalleEdit);
        final Button buttonGuardar = (Button) findViewById(R.id.guardarEditUsuarioButton);


        country = (Spinner) findViewById(R.id.spinner1);

        country.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter <String> c = new ArrayAdapter <String> (this,android.R.layout.simple_spinner_item,countryStr);
        c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(c);

        city=(Spinner)findViewById(R.id.spinnerCity);
        city.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        calle.setFilters(new InputFilter[] { filter });
        username.setFocusable(false);
        city.setEnabled(true);


        idUsuario =usuario.getId();

        name.setText(usuario.getNombre());
        email.setText(usuario.getEmail());
        telefono.setText(usuario.getTelefono());

        if(usuario.getDireccion()!=null){
            String  CompareValue= usuario.getDireccion().getSector().getCiudad().getPais().getNombrePais();

            if (!CompareValue.equals(null)) {
                int SpinnerPostion = c.getPosition(CompareValue);
                country.setSelection(SpinnerPostion);
                SpinnerPostion = 0;
            }

            sector.setText(usuario.getDireccion().getSector().getNombreSector());
            GuardarCiudad= usuario.getDireccion().getSector().getCiudad().getNombreCiudad();
            calle.setText(usuario.getDireccion().getCalle());
        }

        username.setText(usuario.getUsername());
//            }
//        }

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Presiono el boton guardar");
                if( username.getText().toString().trim().equals("") || name.getText().toString().trim().equals("") ||
                        telefono.getText().toString().trim().equals("") || sector.getText().toString().trim().equals("") ||
                        city.getSelectedItem()==null || country.getSelectedItem().toString().equals("Seleccionar Pais") ||
                        calle.getText().toString().trim().equals(""))

                {

                    Toast.makeText(EditProfileActivity.this,"Debe de llenar todos los campos",Toast.LENGTH_LONG).show();

                    username.setError("Debe completar todos los campos!!!");
                    name.setError("Debe completar todos los campos!!!");
                    telefono.setError("Debe completar todos los campos!!!");
                    sector.setError("Debe completar todos los campos!!!");
                    country.setElevation(5);
                    city.setElevation(5);
                    calle.setError("Debe completar todos los campos!!!");

                }else if(!isValidEmail(email.getText().toString()) ){
                    Toast.makeText(EditProfileActivity.this,"Favor entrar un correo correcto",Toast.LENGTH_LONG);
                }else if(!isValidPhoneNumber(telefono.getText().toString())){
                    Toast.makeText(EditProfileActivity.this,"Favor entrar un numero de telefono correcto",Toast.LENGTH_LONG);
                }
                else {


                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                HttpClient httpClient = new DefaultHttpClient();
                                HttpPost httpPost = new HttpPost("https://waterquality.pionot.com/API/EditarProfile/");

                                String json =
                                        "{" + "id:" + idUsuario
                                                + ",nombre:" + name.getText().toString()
                                                + ",telefono:" + telefono.getText().toString()
                                                + ",sector:" + sector.getText().toString()
                                                + ",ciudad:" + city.getSelectedItem().toString()
                                                + ",pais:" + country.getSelectedItem().toString()
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
                                    System.out.println("88888888888888888888888888888888888 " + total.toString());

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
                    Intent loginScreen = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                Toast.makeText(EditProfileActivity.this, "WELCOME TO LOGINSCREEN", Toast.LENGTH_SHORT).show();
//                EditProfileActivity.this.supportFinishAfterTransition();
                    startActivity(loginScreen.putExtra("usuario", usuario.getUsername()).putExtra("data", (Serializable) usuarios));
                }
            }
        });
    }

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            String blockCharacterSet = "~#^|$%&*!";
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    private boolean isValidEmail(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    @Override
    public void onClick(View view) {
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,long id)
    {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) parent.getChildAt(0)).setTextSize(18);

        switch(parent.getId())
        {
            case R.id.spinner1:
                city.setEnabled(true);
                if(country.getSelectedItem().equals("Republica Dominicana"))
                {
                    ArrayAdapter <String> s1 = new ArrayAdapter <String> (this,android.R.layout.simple_spinner_item,states_RepublicaDominicana);
                    s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    city.setAdapter(s1);


                    String  CompareValue2= GuardarCiudad;

                    if (!CompareValue2.equals(null)) {
                        int SpinnerPostion = s1.getPosition(CompareValue2);
                        city.setSelection(SpinnerPostion);
                        SpinnerPostion = 0;
                    }

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

            case R.id.spinnerCity:
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