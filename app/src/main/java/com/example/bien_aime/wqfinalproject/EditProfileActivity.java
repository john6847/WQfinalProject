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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import static org.parceler.guava.base.Predicates.equalTo;

@SuppressWarnings("deprecation")
public class EditProfileActivity extends AppCompatActivity {

    List<Usuario> usuarios=new ArrayList<>();
    String usuarioLlegando;
    static String idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent i = getIntent();
        usuarios= (List<Usuario>) i.getSerializableExtra("usuariosEditable");
        usuarioLlegando=i.getStringExtra("usuario");


        final EditText username = (EditText) findViewById(R.id.usernameEdit);
        final EditText name = (EditText) findViewById(R.id.nombreEdit);
        final EditText direccion = (EditText) findViewById(R.id.direccionEdit);
        final EditText telefono = (EditText) findViewById(R.id.telefonoEdit);
        final EditText email = (EditText) findViewById(R.id.emailEdit);
        final Button buttonGuardar = (Button) findViewById(R.id.guardarEditButton);

        for (Usuario usuario: usuarios){
            if(usuario.getUsername().equals(usuarioLlegando)){
                System.out.println("&&&&&&&&&&&&"+usuario.getId());
                idUsuario=usuario.getId().toString();

                name.setText(usuario.getNombre());
                email.setText("bencosky50@gmail.com");
                telefono.setText(usuario.getTelefono().toString());
                direccion.setText(usuario.getDireccion().toString());
                username.setText(usuario.getUsername());
            }
        }
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            //Your code goes here
                            HttpClient httpClient=new DefaultHttpClient();
                            HttpPost httpPost=new HttpPost("http://waterqualityjohn.herokuapp.com/API/EditarProfile/");

                            String json="{"+"id:"+idUsuario +",nombre:"+name.getText().toString()+",direccion:"+direccion.getText().toString()+",telefono:"+telefono.getText().toString()+",username:"+username.getText().toString()+"}";

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


                Toast.makeText(EditProfileActivity.this,"Guardado",Toast.LENGTH_LONG).show();
                Snackbar.make(view, "If you edit your profile You will have to get back to the log Screen to notice the changes", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent loginScreen = new Intent(EditProfileActivity.this, LoginActivity.class);
                loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(EditProfileActivity.this, "WELCOME TO LOGINSCREEN", Toast.LENGTH_SHORT).show();
                EditProfileActivity.this.finish();
                startActivity(loginScreen);
            }
        });
    }


//    public void whenPostJsonUsingHttpClient_thenCorrect()
//            throws ClientProtocolException, IOException {
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http://www.example.com");
//
//        String json = "{"id":1,"name":"John"}";
//        StringEntity entity = new StringEntity(json);
//        httpPost.setEntity(entity);
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-type", "application/json");
//
//        CloseableHttpResponse response = client.execute(httpPost);
//        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
//        client.close();
//    }
}

//        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
//        sendPostReqAsyncTask.execute();

   // public void sendPostRequest(String givenId,String givenUsername, String givenName, String givenTelefono, String givenDireccion, String givenEmail) {
  /* void sendPostRequest(String response) {

       System.out.println("&&&&&&&&&&&&&&&&&&&&&&"+response);
   }

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String paramId=params[0];
                String paramUsername = params[1];
                String paramName = params[2];
                String paramTelefono = params[3];
                String paramDireccion = params[4];
                //String paramEmail = params[5];



                System.out.println("*** doInBackground ** paramUsername "
                        + paramUsername + " paramPassword :"+paramId);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(
                        "http://waterqualityjohn.herokuapp.com/API/EditarProfile/");
                httpPost.addHeader("Content-type",
                        "application/raw");
                BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair(
                        "username", paramUsername);
                BasicNameValuePair nameBasicNameValuePair = new BasicNameValuePair(
                        "nombre", paramName);
                BasicNameValuePair telefonoBasicNameValuePair = new BasicNameValuePair(
                        "telefono", paramTelefono);
                BasicNameValuePair direccionBasicNameValuePair = new BasicNameValuePair(
                        "direccion", paramDireccion);
//                BasicNameValuePair emailBasicNameValuePair = new BasicNameValuePair(
//                        "email", paramEmail);
                BasicNameValuePair idBasicNameValuePair = new BasicNameValuePair(
                        "id", paramId);



                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(usernameBasicNameValuePair);
                nameValuePairList.add(nameBasicNameValuePair);
                nameValuePairList.add(telefonoBasicNameValuePair);
                nameValuePairList.add(direccionBasicNameValuePair);
              //  nameValuePairList.add(emailBasicNameValuePair);
                nameValuePairList.add(idBasicNameValuePair);

                try {
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
                            nameValuePairList);
                    httpPost.setEntity(urlEncodedFormEntity);

                    try {
                        HttpResponse httpResponse = httpClient
                                .execute(httpPost);
                        System.out.println("+++++++++++++++++++++++++++"+httpResponse.toString());
                        InputStream inputStream = httpResponse.getEntity()
                                .getContent();
                        InputStreamReader inputStreamReader = new InputStreamReader(
                                inputStream);
                        BufferedReader bufferedReader = new BufferedReader(
                                inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String bufferedStrChunk = null;
                        while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                            stringBuilder.append(bufferedStrChunk);
                        }

                        return stringBuilder.toString();


                    } catch (ClientProtocolException cpe) {
                        System.out
                                .println("First Exception coz of HttpResponese :"
                                        + cpe);
                        cpe.printStackTrace();
                    } catch (IOException ioe) {
                        System.out
                                .println("Second Exception coz of HttpResponse :"
                                        + ioe);
                        ioe.printStackTrace();
                    }

                } catch (UnsupportedEncodingException uee) {
                    System.out
                            .println("An Exception given because of UrlEncodedFormEntity argument :"
                                    + uee);
                    uee.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                sendPostRequest(result);
//                if(result.equals("working")){
//                    Toast.makeText(getApplicationContext(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(getApplicationContext(), "Invalid POST req...", Toast.LENGTH_LONG).show();
//                }
            }

        }
//        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
//        sendPostReqAsyncTask.execute(givenId,givenUsername, givenName,givenDireccion,givenTelefono,givenEmail);

    }
//}



//    class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            String paramUsername = params[0];
//            String paramName = params[1];
//            String paramTelefono= params[2];
//            String paramDireccion= params[3];
//            String paramEmail= params[4];
//
//
//            System.out.println("*** doInBackground ** paramUsername "
//                    + paramUsername + " paramPassword :" );
//
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpPost httpPost = new HttpPost(
//                    "http://lib-dm.process9.com/libertydm/ValidateUserHandler.ashx");// replace with your url
//            httpPost.addHeader("Content-type",
//                    "application/x-www-form-urlencoded");
//            BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair(
//                    "UserId", paramUsername);  // Make your own key value pair
//            BasicNameValuePair nameBasicNameValuePair = new BasicNameValuePair(
//                    "Name", paramName);// make your own key value pair
//            BasicNameValuePair telefonoBasicNameValuePair = new BasicNameValuePair(
//                    "Telefono", paramTelefono);// make your own key value pair
//            BasicNameValuePair direccionBasicNameValuePair = new BasicNameValuePair(
//                    "Direccion", paramDireccion);// make your own key value pair
//            BasicNameValuePair emailBasicNameValuePair = new BasicNameValuePair(
//                    "Email", paramEmail);// make your own key value pair
//
//            // You can add more parameters like above
//
//            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
//            nameValuePairList.add(usernameBasicNameValuePair);
//            nameValuePairList.add(nameBasicNameValuePair);
//            nameValuePairList.add(telefonoBasicNameValuePair);
//            nameValuePairList.add(direccionBasicNameValuePair);
//            nameValuePairList.add(emailBasicNameValuePair);
//
//            try {
//                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
//                        nameValuePairList);
//                httpPost.setEntity(urlEncodedFormEntity);
//
//                try {
//                    HttpResponse httpResponse = httpClient
//                            .execute(httpPost);
//                    InputStream inputStream = httpResponse.getEntity()
//                            .getContent();
//                    InputStreamReader inputStreamReader = new InputStreamReader(
//                            inputStream);
//                    BufferedReader bufferedReader = new BufferedReader(
//                            inputStreamReader);
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String bufferedStrChunk = null;
//                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(bufferedStrChunk);
//                    }
//
//                    return stringBuilder.toString();
//
//                } catch (ClientProtocolException cpe) {
//                    System.out
//                            .println("First Exception coz of HttpResponese :"
//                                    + cpe);
//                    cpe.printStackTrace();
//                } catch (IOException ioe) {
//                    System.out
//                            .println("Second Exception coz of HttpResponse :"
//                                    + ioe);
//                    ioe.printStackTrace();
//                }
//
//            } catch (UnsupportedEncodingException uee) {
//                System.out
//                        .println("An Exception given because of UrlEncodedFormEntity argument :"
//                                + uee);
//                uee.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//        }
//
//    }
//
////    public void sendPostRequest(String givenUsername, String givenName, String givenTelefono, String givenDireccion, String givenEmail) {
////
////    }

*/
