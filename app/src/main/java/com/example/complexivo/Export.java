package com.example.complexivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.complexivo.DB.DataBase;
import com.example.complexivo.Progresst_Bar.ManejoProgressBar;
import com.example.complexivo.Utilidades.Atributos;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class Export extends AppCompatActivity {

    private JSONObject postData;
    private JSONObject participante;

    private int acaba = 0;
    private Boolean estimg = false;

    private int progreso = 0, count = 0;
    private String host = "http://capacitaciones-continuas-ista.us-east-1.elasticbeanstalk.com/api/";


    private String donde = "export";
    private ManejoProgressBar manejoProgressBar;

    //VISTA
    private ImageView imgexport;
    private ProgressBar pgsexport;
    private Button btnexport;
    private TextView txtvcuent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        //VISTA
        imgexport = (ImageView) findViewById(R.id.imgexporttdata);
        pgsexport = (ProgressBar) findViewById(R.id.pgbexportdata);
        btnexport = (Button) findViewById(R.id.btnexportdata);
        txtvcuent = (TextView) findViewById(R.id.txtvcuenta);

        donde = (String) getIntent().getSerializableExtra("donde");

        Drawable drawablef = getResources().getDrawable(R.drawable.subir_2);
        imgexport.setImageDrawable(drawablef);

        btnexport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                manejoProgressBar = new ManejoProgressBar(pgsexport);
                manejoProgressBar.execute();

                exportAll();
            }
        });
    }

    public void exportAll() {
        System.out.println("--------------------------------------------------------------------------------------------------");
        DataBase conection = new DataBase(Export.this);
        SQLiteDatabase db = conection.getReadableDatabase();

        String[] projection = {"idAsistencia", "fechaAsistencia", "estadoAsistencia", "observacionAsistencia", "idParticipanteMatriculado", "estadoActual"};
        String selection = "estadoSubida = ?";
        String[] selectionArgs = {"0"};

        Cursor cursor = db.query(Atributos.table_asistencia, projection, selection, selectionArgs, null, null, null);

        count = cursor.getCount();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                postData = new JSONObject();
                participante = new JSONObject();

                try {
                    postData.put("idAsistencia", cursor.getInt(0));
                    postData.put("fechaAsistencia", cursor.getString(1));
                    postData.put("estadoAsistencia", cursor.getInt(2) == 1 ? true : false);
                    postData.put("observacionAsistencia", cursor.getString(3));

                    participante.put("idParticipanteMatriculado", cursor.getInt(4));
                    //System.out.println(cursor.getInt(4) + "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                    //System.out.println(participante);
                    postData.put("partipantesMatriculados", participante);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String jsonString = postData.toString();
                int ids = cursor.getInt(0);
                if (cursor.getString(5).equals("Actualizado")) {
                    bayPut(ids, jsonString);
                }

                if (cursor.getString(5).equals("Creado")) {
                    bayNew(ids, jsonString);
                }
            }
        } else {
            if (donde != null && donde.equals("import")) {

                new AsyncTask<Void, Integer, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            // Realizar tareas de exportación aquí
                            Thread.sleep(2 * 1000);
                            publishProgress(91);
                            publishProgress(92);
                            Thread.sleep(2 * 1000);
                            publishProgress(93);
                            publishProgress(94);
                            Thread.sleep(2 * 1000);
                            publishProgress(95);
                            publishProgress(96);
                            Thread.sleep(2 * 1000);
                            publishProgress(97);
                            publishProgress(98);
                            Thread.sleep(2 * 1000);
                            publishProgress(99);
                            publishProgress(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        System.out.println(values[0] + "                          dddddddddd");
                        super.onProgressUpdate(values);
                        pgsexport.setProgress(values[0]);
                        if (values[0] == 92){
                            txtvcuent.setText("3");
                            txtvcuent.setVisibility(View.VISIBLE);
                        }
                        if (values[0] == 94) {
                            txtvcuent.setText("2");
                        }
                        if (values[0] == 96) {
                            txtvcuent.setText("1");
                        }
                        if (values[0] == 98) {
                            txtvcuent.setText("0");
                        }
                        if (values[0] == 100) {
                            txtvcuent.setText("...");
                        }
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        finish();
                        Toast.makeText(Export.this, "Puede continuar con su update", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
            } else {
                Toast.makeText(Export.this, "No hay datos para exportar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void bayNew(int aid, String datos){
        String url = host + "asistencia/save";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            img();
                            progreso++;
                        if (donde != null && donde.equals("import")) {
                            int pg = (progreso * 90) / count;
                            pgsexport.setProgress(pg);
                        } else {
                            int pg = (progreso * 100) / count;
                            pgsexport.setProgress(pg);
                        }
                        System.out.println("siuuuuuuuuuuuuuuuuuuuuuuu");
                        System.out.println(response);
                        actualizar(aid);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(Export.this, "Error inesperado intentar nuevamente", Toast.LENGTH_SHORT).show();

                    Drawable drawablef = getResources().getDrawable(R.drawable.reintentar_1);
                    imgexport.setImageDrawable(drawablef);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                byte[] jsonData = datos.getBytes();
                return jsonData;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue queue = Volley.newRequestQueue(Export.this);
        queue.add(request);
    }

    public void bayPut(int aid, String datos){
        String url = host + "asistencia/actualizar/" + aid;

        StringRequest request = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            img();
                            progreso++;
                        if (donde != null && donde.equals("import")) {
                            int pg = (progreso * 90) / count;
                            pgsexport.setProgress(pg);
                        } else {
                            int pg = (progreso * 100) / count;
                            pgsexport.setProgress(pg);
                        }

                        System.out.println("siuuuuuuuuuuuuuuuuuuuuuuu");
                        System.out.println(response);
                        actualizar(aid);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(Export.this, "Error inesperado intentar nuevamente", Toast.LENGTH_SHORT).show();

                    Drawable drawablef = getResources().getDrawable(R.drawable.reintentar_1);
                    imgexport.setImageDrawable(drawablef);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                byte[] jsonData = datos.getBytes();
                return jsonData;

            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue queue = Volley.newRequestQueue(Export.this);
        queue.add(request);
    }

    public void actualizar(int aid){
        DataBase conection = new DataBase(Export.this);
        SQLiteDatabase db = conection.getWritableDatabase();

        String sql = "UPDATE asistencia SET estadoSubida = ?, estadoActual = ? WHERE idAsistencia = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindLong(1, 1);
        statement.bindString(2, "Descargado");
        statement.bindLong(3, aid);
        long resultado = statement.executeUpdateDelete();
        System.out.println("PARTICIPANTE ALMACENADA CORRECTAMENTE....................................." + aid);
            btnexport.setEnabled(false);
            btnexport.setText("DATOS CARGADO EXITOSAMENTE");
            btnexport.setBackgroundColor(Color.GRAY);
            Drawable drawablef = getResources().getDrawable(R.drawable.finalizando);
            imgexport.setImageDrawable(drawablef);
        acaba++;
            if (acaba == count) {
                exportAll();
            }
    }

    public void img(){
        Drawable drawable1 = getResources().getDrawable(R.drawable.subir_1);
        Drawable drawable2 = getResources().getDrawable(R.drawable.subir_2);
            if (estimg == false) {
                estimg = true;
                imgexport.setImageDrawable(drawable1);
            } else {
                estimg = false;
                imgexport.setImageDrawable(drawable2);
            }
    }
}