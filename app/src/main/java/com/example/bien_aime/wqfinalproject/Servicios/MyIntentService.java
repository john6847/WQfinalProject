package com.example.bien_aime.wqfinalproject.Servicios;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.amitshekhar.utils.DatabaseHelper;
import com.example.bien_aime.wqfinalproject.API.ApiService;
import com.example.bien_aime.wqfinalproject.ModeloDB.MuestraSQLiteHelper;
import com.example.bien_aime.wqfinalproject.MuestrasContentProvider;
import com.example.bien_aime.wqfinalproject.modelo.Muestra;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "DownloadService";

    static final Uri CONTENT_URL =
            Uri.parse("com.exemple.bien_aime.wqfinalproject.MuestrasContentProvider/cpmuestras");

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Toast.makeText(this,"Empieza el servicio", Toast.LENGTH_LONG).show();
        //final String nombreDispositivo=intent.getStringExtra("dispositivo");
        final String nombreDispositivo = intent.getStringExtra("dispositivo");
        //Toast.makeText(this,"Empieza el servicio"+nombreDispositivo, Toast.LENGTH_LONG).show();
        System.out.println("empiezaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + nombreDispositivo);


//        ApiService apiService = ApiService.retrofit.create(ApiService.class);
//        final retrofit2.Call<List<Muestra>> call = apiService.getMuestras();
//
//        call.enqueue(new Callback<List<Muestra>>() {
//            @Override
//            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
//                List<Muestra> muestras = response.body();
//
//                //String insertQuery="INSERT INTO Muestras(nombreParametro,valor,fecha,dispositivo) values"
//                MuestraSQLiteHelper instanciaDB =
//                        new MuestraSQLiteHelper(MyIntentService.this, "MuestrasDatabase", null, 1);
//
//                SQLiteDatabase db = instanciaDB.getReadableDatabase();
//                //Si hemos abierto la DB correctamente
//                if (db != null) {
//                    for (Muestra muestra : muestras) {
//                        if (muestra.getDispositivo().getNombreDispositivo().equals(nombreDispositivo)) {
//                            db.execSQL("INSERT INTO Muestras(id,nombreParametro,valor,fecha)" + " VALUES(" + muestra.getId() + ", " + muestra.getListadoValores().iterator().next().getParametro().getNombreParametro() + ", " + muestra.getListadoValores().iterator().next().getValor().toString() + ", " + muestra.getFechaMuestra() + ")");
//                        }
//
//                    }
//                }
////                    db.close();
//
//
////                CursorLoader cursorLoader;
////
////                Toast.makeText(MyIntentService.this,"Empieza el servicio"+muestras.size(), Toast.LENGTH_LONG).show();
////
////                // Provides access to other applications Content Providers
////                ContentResolver resolver;
////                resolver = getContentResolver();
////                // Put in the column name and the value
////                ContentValues values = new ContentValues();
////                for (Muestra muestra:muestras){
////                    values.put("nombreParametro",muestra.getListadoValores().iterator().next().getParametro().getNombreParametro());
////                    values.put("valor",muestra.getListadoValores().iterator().next().getValor().toString());
////                    values.put("fecha",muestra.getFechaMuestra());
////                    values.put("dispositivo",nombreDispositivo);
////                }
////
////                resolver.insert(CONTENT_URL,values);
//
//                //Abrimos la base de datos 'DBUsuarios' en modo escritura
////                MuestraSQLiteHelper instanciaDB =
////                        new MuestraSQLiteHelper(getBaseContext(), "DBmuestras", null, 1);
////
////                SQLiteDatabase db = instanciaDB.getWritableDatabase();
////
////                //Si hemos abierto la DB correctamente
////                if(db != null){
////                    for(Muestra muestra: muestras){
////                        if(muestra.getDispositivo().getNombreDispositivo().equals(nombreDispositivo)){
////                            //db.execSQL("INSERT INTO Muestras(id,nombreParametro,valor,fecha)"+" VALUES("+muestra.getId()+", "+ muestra.getListadoValores().iterator().next().getParametro().getNombreParametro()+", "+muestra.getListadoValores().iterator().next().getValor().toString()+", "+muestra.getFechaMuestra()+")");
////                        }
////
////                    }
////
//            }
//
//            @Override
//            public void onFailure(Call<List<Muestra>> call, Throwable t) {
//
//            }
//
//        });
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
