package com.example.experimental.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.experimental.Utilidades.Atributos;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImportData extends DataBaseTemporal {

    private RequestQueue requestQueue;
    private Context conection;
    private ContentValues values;
    private String host = "192.168.100.31";
    private int timeout = 10000;

    public ImportData(@NotNull Context context) {
        super(context);
        this.conection = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void importarDatos() {
        //importarInscrito();
        //importarPrerequisitos();
        //importarCursos();
        //importarCapacitador();
        //importarProgramas();
        //importarUsuarios();
        //importarPersonas();

        cargarDatosTemporales();
    }

    public boolean limpiartable(String table){
        SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
        db.execSQL("DELETE FROM " + table);

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + table, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean importarPersonas(){
        String uri = "http://" + host + ":8080/api/persona/listar";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO PERSONA.....................................");
                SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                for (int a = 0; a < response.length(); a++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(a).toString());

                        values = new ContentValues();
                        values.put("idPersona", jsonObject.getInt("idPersona"));
                        values.put("identificacion", jsonObject.getString("identificacion"));
                        values.put("nombre1", jsonObject.getString("nombre1"));
                        values.put("nombre2", jsonObject.getString("nombre2"));
                        values.put("apellido1", jsonObject.getString("apellido1"));
                        values.put("apellido2", jsonObject.getString("apellido2"));
                        values.put("correo", jsonObject.getString("correo"));
                        values.put("telefono", jsonObject.getString("telefono"));
                        values.put("celular", jsonObject.getString("celular"));
                        values.put("genero", jsonObject.getString("genero"));
                        values.put("etnia", jsonObject.getString("etnia"));

                        long resultado = db.insert(Atributos.table_persona, null, values);
                        resultados.add(resultado);

                        System.out.println(resultado + " PERSONA ALMACENADA CORRECTAMENTE");
                    } catch (JSONException e) {
                        System.out.println("ERROR CATH PERSONA............................");
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL PERSONA............................");
                System.out.println(error.getMessage());
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonArrayRequest);

        for (long resultado : resultados) {
            if (resultado == -1) {
                return false;
            }
        }

        return true;
    }

    public boolean importarUsuarios(){
        String uri = "http://" + host + ":8080/api/usuario/listar";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO USUARIO.....................................");
                SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                for (int a = 0; a < response.length(); a++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(a).toString());
                        JSONObject jsonObjectIdPersona = new JSONObject(jsonObject.get("persona").toString());

                        values = new ContentValues();
                        values.put("idUsuario", jsonObject.getInt("idUsuario"));
                        values.put("username", jsonObject.getString("username"));
                        values.put("password", jsonObject.getString("password"));
                        //values.put("fotoPerfil", jsonObject.getString("fotoPerfil"));
                        values.put("estadoUsuarioActivo", jsonObject.getBoolean("estadoUsuarioActivo"));
                        values.put("idPersona", jsonObjectIdPersona.getInt("idPersona"));

                        long resultado = db.insert(Atributos.table_usuarios, null, values);
                        resultados.add(resultado);

                        System.out.println(resultado + " USUARIO ALMACENADA CORRECTAMENTE");
                    } catch (JSONException e) {
                        System.out.println("ERROR CATH USUARIO............................");
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL USUARIO............................");
                System.out.println(error.getMessage());
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonArrayRequest);

        for (long resultado : resultados) {
            if (resultado == -1) {
                return false;
            }
        }

        return true;
    }

    public boolean importarCapacitador(){
        String uri = "http://" + host + ":8080/api/capacitador/list";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO CAPACITADOR.....................................");
                SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                for (int a = 0; a < response.length(); a++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(a).toString());
                        JSONObject jsonObjectIdUsuario = new JSONObject(jsonObject.get("usuario").toString());


                        values = new ContentValues();
                        values.put("idCapacitador", jsonObject.getInt("idCapacitador"));
                        values.put("estadoActivoCapacitador", jsonObject.getBoolean("estadoActivoCapacitador"));
                        values.put("tituloCapacitador", jsonObject.getString("tituloCapacitador"));
                        values.put("idUsuario", jsonObjectIdUsuario.getInt("idUsuario"));

                        long resultado = db.insert(Atributos.table_capacitador, null, values);
                        resultados.add(resultado);

                        System.out.println(resultado + " CAPACITADOR ALMACENADA CORRECTAMENTE");
                    } catch (JSONException e) {
                        System.out.println("ERROR CATH CAPACITADOR............................");
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL CAPACITADOR............................");
                System.out.println(error.getMessage());
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonArrayRequest);

        for (long resultado : resultados) {
            if (resultado == -1) {
                return false;
            }
        }

        return true;
    }

    public boolean importarProgramas(){
        String uri = "http://" + host + ":8080/api/programa/listar";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO PROGRAMA.....................................");
                SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                for (int a = 0; a < response.length(); a++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(a).toString());
                        JSONObject jsonObjectPeriodoProgramas = new JSONObject(jsonObject.get("periodoPrograma").toString());

                        values = new ContentValues();
                        values.put("idPrograma", jsonObject.getInt("idPrograma"));
                        values.put("nombrePrograma", jsonObject.getString("nombrePrograma"));
                        values.put("estadoProgramaActivo", jsonObject.getBoolean("estadoProgramaActivo"));
                        values.put("estadoPeriodoPrograma", jsonObjectPeriodoProgramas.getBoolean("estadoPeriodoPrograma"));
                        values.put("fechaInicioPeriodoPrograma", jsonObjectPeriodoProgramas.getString("fechaInicioPeriodoPrograma"));
                        values.put("fechaFinPeriodoPrograma", jsonObjectPeriodoProgramas.getString("fechaFinPeriodoPrograma"));
                        values.put("nombrePeriodoPrograma", jsonObjectPeriodoProgramas.getString("nombrePeriodoPrograma"));

                        long resultado = db.insert(Atributos.table_programas, null, values);
                                resultados.add(resultado);

                        System.out.println(resultado + " PROGRAMA ALMACENADA CORRECTAMENTE");
                    } catch (JSONException e) {
                        System.out.println("ERROR CATH PROGRAMA............................");
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL PROGRAMA............................");
                System.out.println(error.getMessage());
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonArrayRequest);

        for (long resultado : resultados) {
            if (resultado == -1) {
                return false;
            }
        }

        return true;
    }

    public boolean importarCursos(){
        String uri = "http://" + host + ":8080/api/curso/list";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO CURSO.....................................");
                SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                for (int a = 0; a < response.length(); a++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(a).toString());
                        JSONObject jsonObjectEspecialidadCursos = new JSONObject(jsonObject.get("especialidad").toString());
                        JSONObject jsonObjectAreaCursos = new JSONObject(jsonObjectEspecialidadCursos.get("area").toString());
                        JSONObject jsonObjectTipoCursos = new JSONObject(jsonObject.get("tipoCurso").toString());
                        JSONObject jsonObjectModalidadCursos = new JSONObject(jsonObject.get("modalidadCurso").toString());
                        JSONObject jsonObjectHorarioCursos = new JSONObject(jsonObject.get("horarioCurso").toString());


                        JSONObject jsonObjectIdCapacitador = new JSONObject(jsonObject.get("capacitador").toString());
                        JSONObject jsonObjectIdProgramas = new JSONObject(jsonObject.get("programas").toString());



                        values = new ContentValues();
                        values.put("idCurso", jsonObject.getInt("idCurso"));
                        values.put("nombreCurso", jsonObject.getString("nombreCurso"));
                        //values.put("fotoCurso", jsonObject.getString("fotoCurso"));
                        values.put("duracionCurso", jsonObject.getInt("duracionCurso"));
                        values.put("observacionCurso", jsonObject.getString("observacionCurso"));
                        values.put("estadoCurso", jsonObject.getBoolean("estadoCurso"));
                        values.put("estadoAprovacionCurso", jsonObject.getString("estadoAprovacionCurso"));
                        values.put("estadoPublicasionCurso", jsonObject.getBoolean("estadoPublicasionCurso"));
                        values.put("descripcionCurso", jsonObject.getString("descripcionCurso"));
                        values.put("objetivoGeneralesCurso", jsonObject.getString("objetivoGeneralesCurso"));
                        values.put("numeroCuposCurso", jsonObject.getInt("numeroCuposCurso"));
                        values.put("fechaInicioCurso", jsonObject.getString("fechaInicioCurso"));
                        values.put("fechaFinalizacionCurso", jsonObject.getString("fechaFinalizacionCurso"));

                        values.put("nombreEspecialidad", jsonObjectEspecialidadCursos.getString("nombreEspecialidad"));
                        values.put("nombreArea", jsonObjectAreaCursos.getString("nombreArea"));
                        values.put("nombreTipoCurso", jsonObjectTipoCursos.getString("nombreTipoCurso"));
                        values.put("nombreModalidadCurso", jsonObjectModalidadCursos.getString("nombreModalidadCurso"));
                        values.put("horaInicio", jsonObjectHorarioCursos.getString("horaInicio"));
                        values.put("horaFin", jsonObjectHorarioCursos.getString("horaFin"));

                        values.put("idCapacitador", jsonObjectIdCapacitador.getInt("idCapacitador"));
                        values.put("idPrograma", jsonObjectIdProgramas.getInt("idPrograma"));

                        long resultado = db.insert(Atributos.table_cursos, null, values);
                        resultados.add(resultado);

                        System.out.println(resultado + " CURSO ALMACENADA CORRECTAMENTE");
                    } catch (JSONException e) {
                        System.out.println("ERROR CATH CURSO............................");
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL CURSO............................");
                System.out.println(error.getMessage());
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonArrayRequest);

        for (long resultado : resultados) {
            if (resultado == -1) {
                return false;
            }
        }

        return true;
    }

    public boolean importarPrerequisitos(){
        String uri = "http://" + host + ":8080/api/prerequisitoCurso/list";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO PREREQUISITO.....................................");
                SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                for (int a = 0; a < response.length(); a++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(a).toString());
                        JSONObject jsonObjectIdCurso = new JSONObject(jsonObject.get("curso").toString());

                        values = new ContentValues();
                        values.put("idPrerequisitoCurso", jsonObject.getInt("idPrerequisitoCurso"));
                        values.put("nombrePrerequisitoCurso", jsonObject.getString("nombrePrerequisitoCurso"));
                        values.put("estadoPrerequisitoCurso", jsonObject.getBoolean("estadoPrerequisitoCurso"));
                        values.put("idCurso", jsonObjectIdCurso.getInt("idCurso"));

                        long resultado = db.insert(Atributos.table_prerequisitos, null, values);
                        resultados.add(resultado);

                        System.out.println(resultado + " PREREQUISITOS ALMACENADA CORRECTAMENTE");
                    } catch (JSONException e) {
                        System.out.println("ERROR CATH PREREQUISITO............................");
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL PREREQUISITO............................");
                error.printStackTrace();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonArrayRequest);

        for (long resultado : resultados) {
            if (resultado == -1) {
                return false;
            }
        }

        return true;
    }

    public boolean importarInscrito(){
        String uri = "http://" + host + ":8080/api/inscritocurso/listar";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO INSCRITOS.....................................");
                SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                for (int a = 0; a < response.length(); a++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(a).toString());

                        values = new ContentValues();
                        values.put("idInscrito", jsonObject.getInt("idInscrito"));
                        values.put("fechaInscrito", jsonObject.getString("fechaInscrito"));
                        values.put("estadoInscritoActivo", jsonObject.getBoolean("estadoInscritoActivo"));
                        values.put("estadoParticipanteAprobacion", "cur");
                        values.put("estadoParticipanteActivo", false);

                        values.put("idCurso", jsonObject.getInt("idCurso"));

                        long resultado = db.insert(Atributos.table_inscritos, null, values);
                        resultados.add(resultado);

                        System.out.println(resultado + " INSCRITOS ALMACENADA CORRECTAMENTE");
                    } catch (JSONException e) {
                        System.out.println("ERROR CATH INSCRITOS............................");
                        throw new RuntimeException(e);
                    }
                }
                importarParticipanteMatriculado();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL INSCRITOS............................");
                error.printStackTrace();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonArrayRequest);

        for (long resultado : resultados) {
            if (resultado == -1) {
                return false;
            }
        }

        return true;
    }

    public void importarParticipanteMatriculado(){
        String uri = "http://" + host + ":8080/api/participantesMatriculados/listar";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO PARTICIPANTE.....................................");
                SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                for (int a = 0; a < response.length(); a++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(a).toString());
                        JSONObject jsonObjectIdInscrito = new JSONObject(jsonObject.get("inscrito").toString());

                        Boolean est = jsonObject.getBoolean("estadoParticipanteActivo");
                        int estint = est ? 1 : 0;

                        String sql = "UPDATE inscritos SET estadoParticipanteAprobacion = ?, estadoParticipanteActivo = ? WHERE idInscrito = ?";
                        SQLiteStatement statement = db.compileStatement(sql);
                        statement.bindString(1, jsonObject.getString("estadoParticipanteAprobacion"));
                        statement.bindLong(2, estint);
                        statement.bindLong(3, jsonObjectIdInscrito.getInt("idInscrito"));
                        statement.execute();
                        System.out.println("PARTICIPANTE ALMACENADA CORRECTAMENTE.....................................");
                    } catch (JSONException e) {
                        System.out.println("ERROR CATH PARTICIPANTE............................");
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL PARTICIPANTE............................");
                System.out.println(error.getMessage());
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonArrayRequest);
    }

    public boolean importarAsistencia(){
        String uri = "http://" + host + ":8080/api/asistencia/list";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO ASISTENCIA.....................................");
                SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                for (int a = 0; a < response.length(); a++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(a).toString());
                        JSONObject jsonObjectIdInscrito = new JSONObject(jsonObject.get("inscrito").toString());

                        values = new ContentValues();
                        values.put("idAsistencia", jsonObject.getInt("idAsistencia"));
                        values.put("fechaInscrito", jsonObject.getString("fechaAsistencia"));
                        values.put("estadoAsistencia", jsonObject.getBoolean("estadoAsistencia"));
                        values.put("observacionAsistencia", jsonObject.getString("observacionAsistencia"));
                        values.put("idInscrito", jsonObjectIdInscrito.getInt("idInscrito"));

                        long resultado = db.insert(Atributos.table_asistencia, null, values);
                        resultados.add(resultado);

                        System.out.println(resultado + " ASISTENCIA ALMACENADA CORRECTAMENTE");
                    } catch (JSONException e) {
                        System.out.println("ERROR CATH ASISTENCIA............................");
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL ASISTENCIA............................");
                error.printStackTrace();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonArrayRequest);

        for (long resultado : resultados) {
            if (resultado == -1) {
                return false;
            }
        }

        return true;
    }

    public void cargarDatosTemporales(){
        cargarDatosTemporalesPersona();
        cargarDatosTemporalesUsuarios();
        cargarDatosTemporalesProgramas();
        cargarDatosTemporalesCapacitadores();
        cargarDatosTemporalesCursos();
        cargarDatosTemporalesPrerequisitos();
        cargarDatosTemporalesInscritos();
    }

    public void cargarDatosTemporalesPersona(){
        SQLiteDatabase db = (new DataBase(conection)).getWritableDatabase();
        for (int a = 1; a < 10000; a++) {


            values = new ContentValues();
            values.put("idPersona", a);
            values.put("identificacion", "identificacion " + a);
            values.put("nombre1", "nombre1 " + a);
            values.put("nombre2", "nombre2 " + a);
            values.put("apellido1", "apellido1 " + a);
            values.put("apellido2", "apellido2 " + a);
            values.put("correo", "correo " + a);
            values.put("telefono", "telefono " + a);
            values.put("celular", "celular " + a);
            values.put("genero", "genero " + a);
            values.put("etnia", "etnia " + a);

            System.out.println(String.valueOf(db.insert(Atributos.table_persona, null, values)) + " PERSONA TEMPORAL ALMACENADA CORRECTAMENTE");
        }
    }

    public void cargarDatosTemporalesUsuarios(){
        SQLiteDatabase db = (new DataBase(conection)).getWritableDatabase();
        for (int a = 1; a < 10000; a++) {


            values = new ContentValues();
            values.put("idUsuario", a);
            values.put("username", "admin" + a);
            values.put("password", "root" + a);
            //values.put("fotoPerfil", "fotoPerfil " + a);
            if(a > 9500) {
                values.put("estadoUsuarioActivo", false);
            } else {
                values.put("estadoUsuarioActivo", true);
            }
            values.put("idPersona", a);

            System.out.println(String.valueOf(db.insert(Atributos.table_usuarios, null, values)) + " USUARIO TEMPORAL ALMACENADA CORRECTAMENTE");
        }
    }

    public void cargarDatosTemporalesProgramas(){
        SQLiteDatabase db = (new DataBase(conection)).getWritableDatabase();
        Random random = new Random();
        for (int a = 1; a < 10; a++) {


            values = new ContentValues();
            values.put("idPrograma", a);
            values.put("nombrePrograma", "nombrePrograma " + a);
            values.put("nombrePeriodoPrograma", "nombrePeriodoPrograma " + a);
            values.put("fechaInicioPeriodoPrograma", "fechaInicioPeriodoPrograma " + a);
            values.put("fechaFinPeriodoPrograma", "fechaFinPeriodoPrograma " + a);
            values.put("estadoProgramaActivo",  true );//(random.nextInt(2) + 1) == 1 ? true : false);
            values.put("estadoPeriodoPrograma", true);

            System.out.println(String.valueOf(db.insert(Atributos.table_programas, null, values)) + " PROGRAMA TEMPORAL ALMACENADA CORRECTAMENTE");
        }
    }

    public void cargarDatosTemporalesCapacitadores(){
        SQLiteDatabase db = (new DataBase(conection)).getWritableDatabase();
        Random random = new Random();
        for (int a = 1; a < 200; a++) {


            values = new ContentValues();
            values.put("idCapacitador", a);
            values.put("idUsuario", a);
            values.put("estadoActivoCapacitador", true);
            values.put("tituloCapacitador", "fechaInicioPeriodoPrograma " + a);

            System.out.println(String.valueOf(db.insert(Atributos.table_capacitador, null, values)) + " CAPACITADORES TEMPORAL ALMACENADA CORRECTAMENTE");
        }
    }

    public void cargarDatosTemporalesCursos(){
        SQLiteDatabase db = (new DataBase(conection)).getWritableDatabase();
        Random random = new Random();
        for (int a = 1; a < 1000; a++) {


            values = new ContentValues();
            values.put("idCurso", a);
            values.put("nombreCurso", "nombreCurso " + a);
            values.put("fotoCurso", "fotoCurso " + a);
            values.put("duracionCurso", a);
            values.put("observacionCurso", "observacionCurso " + a);
            values.put("estadoCurso", true);
            values.put("estadoAprovacionCurso", true);
            values.put("estadoPublicasionCurso", true);
            values.put("descripcionCurso", "descripcionCurso " + a);
            values.put("objetivoGeneralesCurso", "objetivoGeneralesCurso " + a);
            values.put("numeroCuposCurso", a);
            values.put("fechaInicioCurso", "fechaInicioCurso " + a);
            values.put("fechaFinalizacionCurso", "fechaFinalizacionCurso " + a);

            values.put("nombreEspecialidad", "nombreEspecialidad " + a);
            values.put("nombreArea", "nombreArea " + a);
            values.put("nombreTipoCurso", "nombreTipoCurso " + a);
            values.put("nombreModalidadCurso", "nombreModalidadCurso " + a);
            values.put("horaInicio", "horaInicio " + a);
            values.put("horaFin", "horaFin " + a);


            values.put("idPrograma", random.nextInt(10) + 1);
            values.put("idCapacitador", random.nextInt(200) + 1);
            System.out.println(String.valueOf(db.insert(Atributos.table_cursos, null, values)) + " CURSO ALMACENADA CORRECTAMENTE");
        }
    }


    public void cargarDatosTemporalesPrerequisitos(){
        SQLiteDatabase db = (new DataBase(conection)).getWritableDatabase();
        Random random = new Random();
        for (int a = 1; a < 3000; a++) {


            values = new ContentValues();
            values.put("idPrerequisitoCurso", a);
            values.put("estadoPrerequisitoCurso", true);
            values.put("nombrePrerequisitoCurso", "nombrePrerequisitoCurso " + a);

            values.put("idCurso", random.nextInt(1000) + 1);
            System.out.println(String.valueOf(db.insert(Atributos.table_prerequisitos, null, values)) + " PREREQUISITOS ALMACENADA CORRECTAMENTE");
        }
    }

    public void cargarDatosTemporalesInscritos(){
        SQLiteDatabase db = (new DataBase(conection)).getWritableDatabase();
        Random random = new Random();

        int cont = 1;

        for (int a = 1; a < 1000; a++) {

            int[] numeros = random.ints(200, 10000) // Genera números aleatorios entre 200 (inclusive) y 301 (exclusivo)
                    .distinct()
                    .limit(20)
                    .toArray();

            for (int e = 1; e < 20; e++) {
                if (cont <= 1800) {
                    values = new ContentValues();
                    values.put("idInscrito", cont);
                    values.put("fechaInscrito", "fechaInscrito " + a);
                    values.put("estadoInscritoActivo", true);
                    values.put("estadoParticipanteAprobacion", "cur");
                    values.put("estadoParticipanteActivo", true); //(random.nextInt(2) + 1) == 1 ? true : false));

                    values.put("idUsuario", numeros[e]);
                    values.put("idCurso", a);
                    System.out.println(String.valueOf(db.insert(Atributos.table_inscritos, null, values)) + " INSCRITOS ALMACENADA CORRECTAMENTE");
                    cont++;
                }
                e++;
            }
        }
    }
}
