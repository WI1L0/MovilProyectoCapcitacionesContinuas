package com.example.experimental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.experimental.DB.DataBase;
import com.example.experimental.Progresst_Bar.ManejoProgressBar;
import com.example.experimental.Utilidades.Atributos;

import org.json.JSONException;
import org.json.JSONObject;

public class Export extends AppCompatActivity {

    private JSONObject postData;
    private JSONObject participante;

    private int idasi;

    private int progreso = 0, count = 0;
    private String host = "192.168.18.4";

    private ManejoProgressBar manejoProgressBar;

    //VISTA
    private ImageView imgexport;
    private ProgressBar pgsexport;
    private Button btnexport;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        //VISTA
        imgexport = (ImageView) findViewById(R.id.imgexporttdata);
        pgsexport = (ProgressBar) findViewById(R.id.pgbexportdata);
        btnexport = (Button) findViewById(R.id.btnexportdata);

        btnexport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                manejoProgressBar = new ManejoProgressBar(pgsexport);
                manejoProgressBar.execute();

                exportAll(true, Export.this);
            }
        });
    }

    public void exportAll(Boolean est, Context context) {
        System.out.println("--------------------------------------------------------------------------------------------------");
        mContext = context;
        DataBase conection = new DataBase(mContext);
        SQLiteDatabase db = conection.getReadableDatabase();

        String[] projection = {"idAsistencia", "fechaAsistencia", "estadoAsistencia", "observacionAsistencia", "idParticipanteMatriculado", "estadoActual"};
        String selection = "estadoSubida = ?";
        String[] selectionArgs = {"0"};

        Cursor cursor = db.query(Atributos.table_asistencia, projection, selection, selectionArgs, null, null, null);


        if (est == true) {
            count = cursor.getCount();
        }

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

                if (cursor.getString(5).equals("Actualizado")) {
                    bayPut(cursor.getInt(0), est, jsonString);
                }

                if (cursor.getString(5).equals("Creado")) {
                    bayNew(cursor.getInt(0), est, jsonString);
                }
            }
        } else {
            Toast.makeText(mContext, "No hay datos para exportar", Toast.LENGTH_SHORT).show();
        }
    }

    public void bayNew(int aid, Boolean est, String datos){
        String url = "http://" + host + ":8080/api/asistencia/save";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (est == true) {
                            progreso++;
                            int pg = (progreso * 100) / count;
                            pgsexport.setProgress(pg);
                        }
                        System.out.println("siuuuuuuuuuuuuuuuuuuuuuuu");
                        System.out.println(response);
                        actualizar(aid, est);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(mContext, "Error inesperado intentar nuevamente", Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(request);
    }

    public void bayPut(int aid, Boolean est, String datos){
        String url = "http://" + host + ":8080/api/asistencia/actualizar/" + aid;

        StringRequest request = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (est == true) {
                            progreso++;
                            int pg = (progreso * 100) / count;
                            pgsexport.setProgress(pg);
                        }
                        System.out.println("siuuuuuuuuuuuuuuuuuuuuuuu");
                        System.out.println(response);
                        actualizar(aid, est);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(mContext, "Error inesperado intentar nuevamente", Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(request);
    }

    public void actualizar(int aid, Boolean est){
        DataBase conection = new DataBase(mContext);
        SQLiteDatabase db = conection.getWritableDatabase();

        String sql = "UPDATE asistencia SET estadoSubida = ?, estadoActual = ? WHERE idAsistencia = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindLong(1, 1);
        statement.bindString(2, "Descargado");
        statement.bindLong(3, aid);
        long resultado = statement.executeUpdateDelete();
        System.out.println("PARTICIPANTE ALMACENADA CORRECTAMENTE....................................." + aid);
        if (controlAsistencia() == false && est == true){
            btnexport.setEnabled(false);
            btnexport.setText("DATOS CARGADO EXITOSAMENTE");
        }
    }

    public Boolean controlAsistencia(){
        DataBase conection = new DataBase(mContext);
        SQLiteDatabase db = conection.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT idAsistencia FROM asistencia WHERE estadoSubida = '0';", null);
        if (cursor.moveToFirst()) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaeeeeeee1");
            return true;
        } else {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaeeeeeeeee2");
            return false;
        }
    }
}