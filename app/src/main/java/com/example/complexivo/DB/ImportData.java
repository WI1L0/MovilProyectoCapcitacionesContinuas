package com.example.complexivo.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.complexivo.Import;
import com.example.complexivo.Utilidades.Atributos;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImportData extends DataBaseTemporal {

    private Context conection;
    private ContentValues values;
    private String host = "http://capacitaciones-continuas-ista.us-east-1.elasticbeanstalk.com/api/";
    private int timeout = 10000;

    public ImportData(@NotNull Context context) {
        super(context);
        this.conection = context;
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

    public void importarPersonas(final OnImportListener listener){
        String uri = host + "persona/listar";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO PERSONA.....................................");
                if (response.length() != 0) {
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

                            System.out.println(resultado + " PERSONA ALMACENADA CORRECTAMENTE");

                            resultados.add(resultado);

                            boolean exito = true;
                            for (long result : resultados) {
                                if (result == -1) {
                                    exito = false;
                                    break;
                                }
                            }

                            if (exito) {
                                listener.onImportExito(response.length());
                            } else {
                                listener.onImportError();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onImportError();
                        }
                    }
                } else {
                    listener.onImportError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL PERSONA............................");
                System.out.println(error.getMessage());
                listener.onImportError();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestunit = Volley.newRequestQueue(conection);
        requestunit.add(jsonArrayRequest);
        requestunit.start();
    }

    public void importarRol(final OnImportListener listener){
        String uri = host + "rol/listar";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO ROL.....................................");
                if (response.length() != 0) {
                    SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                    for (int a = 0; a < response.length(); a++) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(a).toString());

                            values = new ContentValues();
                            values.put("idRol", jsonObject.getInt("idRol"));
                            values.put("nombreRol", jsonObject.getString("nombreRol"));
                            values.put("estadoRolActivo", jsonObject.getBoolean("estadoRolActivo"));

                            long resultado = db.insert(Atributos.table_rol, null, values);

                            System.out.println(resultado + " ROL ALMACENADA CORRECTAMENTE");

                            resultados.add(resultado);

                            boolean exito = true;
                            for (long result : resultados) {
                                if (result == -1) {
                                    exito = false;
                                    break;
                                }
                            }

                            if (exito) {
                                listener.onImportExito(response.length());
                            } else {
                                listener.onImportError();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onImportError();
                        }
                    }
                } else {
                    listener.onImportError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL ROL............................");
                System.out.println(error.getMessage());
                listener.onImportError();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestunit = Volley.newRequestQueue(conection);
        requestunit.add(jsonArrayRequest);
        requestunit.start();
    }

    public void importarUsuarios(final OnImportListener listener){
        String uri = host + "usuario/listar";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO USUARIO.....................................");
                if (response.length() != 0) {
                    SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                    for (int a = 0; a < response.length(); a++) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(a).toString());
                            JSONObject jsonObjectIdPersona = new JSONObject(jsonObject.get("persona").toString());

                            values = new ContentValues();
                            int idu = jsonObject.getInt("idUsuario");
                            values.put("idUsuario", idu);
                            values.put("username", jsonObject.getString("username"));
                            values.put("password", jsonObject.getString("password"));
                            values.put("fotoPerfil", jsonObject.getString("fotoPerfil"));
                            values.put("estadoUsuarioActivo", jsonObject.getBoolean("estadoUsuarioActivo"));
                            values.put("idPersona", jsonObjectIdPersona.getInt("idPersona"));

                            Boolean est = false;
                            JSONArray jsonArrayRoles = jsonObject.getJSONArray("roles");
                            for (int i = 0; i < jsonArrayRoles.length(); i++) {
                                System.out.println("----------------------------------------------" + i);
                                JSONObject jsonObjectRol = jsonArrayRoles.getJSONObject(i);
                                ContentValues values1 = new ContentValues();
                                values1.put("idRol", jsonObjectRol.getInt("idRol"));
                                values1.put("idUsuario", idu);

                                long resul = db.insert(Atributos.table_rol_usu, null, values1);

                                if (resul == -1){
                                    System.out.println("00000000000000000000000000000000000000000000000 ");
                                    est = true;
                                } else {
                                    System.out.println(resul + " RELACION ALMACENADA CORRECTAMENTE");
                                }
                            }

                            if (est == false){

                                long resultado = db.insert(Atributos.table_usuarios, null, values);

                                System.out.println(resultado + " USUARIO ALMACENADA CORRECTAMENTE");

                                resultados.add(resultado);

                                boolean exito = true;
                                for (long result : resultados) {
                                    if (result == -1) {
                                        exito = false;
                                        break;
                                    }
                                }

                                if (exito) {
                                    listener.onImportExito(response.length());
                                } else {
                                    listener.onImportError();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onImportError();
                        }
                    }
                } else {
                    listener.onImportError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL USUARIO............................");
                System.out.println(error.getMessage());
                listener.onImportError();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestunit = Volley.newRequestQueue(conection);
        requestunit.add(jsonArrayRequest);
        requestunit.start();
    }

    public void importarCapacitador(final OnImportListener listener){
        String uri = host + "capacitador/list";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO CAPACITADOR.....................................");
                if (response.length() != 0) {
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

                            System.out.println(resultado + " CAPACITADOR ALMACENADA CORRECTAMENTE");

                            resultados.add(resultado);

                            boolean exito = true;
                            for (long result : resultados) {
                                if (result == -1) {
                                    exito = false;
                                    break;
                                }
                            }

                            if (exito) {
                                listener.onImportExito(response.length());
                            } else {
                                listener.onImportError();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onImportError();
                        }
                    }
                } else {
                    listener.onImportError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL CAPACITADOR............................");
                System.out.println(error.getMessage());
                listener.onImportError();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestunit = Volley.newRequestQueue(conection);
        requestunit.add(jsonArrayRequest);
        requestunit.start();
    }

    public void importarProgramas(final OnImportListener listener){
        String uri = host + "programa/listar";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO PROGRAMA.....................................");
                if (response.length() != 0) {
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

                            System.out.println(resultado + " PROGRAMA ALMACENADA CORRECTAMENTE");

                            resultados.add(resultado);

                            boolean exito = true;
                            for (long result : resultados) {
                                if (result == -1) {
                                    exito = false;
                                    break;
                                }
                            }

                            if (exito) {
                                listener.onImportExito(response.length());
                            } else {
                                listener.onImportError();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onImportError();
                        }
                    }
                } else {
                    listener.onImportError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL PROGRAMA............................");
                System.out.println(error.getMessage());
                listener.onImportError();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestunit = Volley.newRequestQueue(conection);
        requestunit.add(jsonArrayRequest);
        requestunit.start();
    }

    public void importarCursos(final OnImportListener listener){
        String uri = host + "curso/list";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO CURSO.....................................");
                if (response.length() != 0) {
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
                            values.put("fotoCurso", jsonObject.getString("fotoCurso"));
                            values.put("duracionCurso", jsonObject.getInt("duracionCurso"));
                            values.put("observacionCurso", jsonObject.getString("observacionCurso"));
                            values.put("estadoCurso", jsonObject.getBoolean("estadoCurso"));
                            values.put("estadoAprovacionCurso", jsonObject.getString("estadoAprovacionCurso"));
                            values.put("estadoPublicasionCurso", jsonObject.getString("estadoPublicasionCurso"));
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

                            System.out.println(resultado + " CURSO ALMACENADA CORRECTAMENTE");

                            resultados.add(resultado);

                            boolean exito = true;
                            for (long result : resultados) {
                                if (result == -1) {
                                    exito = false;
                                    break;
                                }
                            }

                            if (exito) {
                                listener.onImportExito(response.length());
                            } else {
                                listener.onImportError();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onImportError();
                        }
                    }
                } else {
                    listener.onImportError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL CURSO............................");
                System.out.println(error.getMessage());
                listener.onImportError();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestunit = Volley.newRequestQueue(conection);
        requestunit.add(jsonArrayRequest);
        requestunit.start();
    }

    public void importarPrerequisitos(final OnImportListener listener){
        String uri = host + "prerequisitoCurso/list";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO PREREQUISITO.....................................");
                if (response.length() != 0) {
                    SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                    for (int a = 0; a < response.length(); a++) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(a).toString());
                            JSONObject jsonObjectIdCurso = new JSONObject(jsonObject.get("curso").toString());

                            values = new ContentValues();
                            values.put("idPrerequisitoCurso", jsonObject.getInt("idPrerequisitoCurso"));
                            values.put("estadoPrerequisitoCurso", jsonObject.getBoolean("estadoPrerequisitoCurso"));
                            values.put("nombrePrerequisitoCurso", jsonObject.getString("nombrePrerequisitoCurso"));
                            values.put("idCurso", jsonObjectIdCurso.getInt("idCurso"));

                            long resultado = db.insert(Atributos.table_prerequisitos, null, values);

                            System.out.println(resultado + " PREREQUISITOS ALMACENADA CORRECTAMENTE");

                            resultados.add(resultado);

                            boolean exito = true;
                            for (long result : resultados) {
                                if (result == -1) {
                                    exito = false;
                                    break;
                                }
                            }

                            if (exito) {
                                listener.onImportExito(response.length());
                            } else {
                                listener.onImportError();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onImportError();
                        }
                    }
                } else {
                    listener.onImportError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL PREREQUISITO............................");
                error.printStackTrace();
                listener.onImportError();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestunit = Volley.newRequestQueue(conection);
        requestunit.add(jsonArrayRequest);
        requestunit.start();
    }

    public void importarInscrito(final OnImportListener listener){
        String uri = host + "inscritocurso/listar";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO INSCRITOS.....................................");
                if (response.length() != 0) {
                    SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                    for (int a = 0; a < response.length(); a++) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(a).toString());
                            JSONObject jsonObjectIdUsuario = new JSONObject(jsonObject.get("usuario").toString());
                            JSONObject jsonObjectIdCursos = new JSONObject(jsonObject.get("curso").toString());

                            values = new ContentValues();
                            values.put("idInscrito", jsonObject.getInt("idInscrito"));
                            values.put("fechaInscrito", jsonObject.getString("fechaInscrito"));
                            values.put("estadoInscrito", jsonObject.getBoolean("estadoInscrito"));
                            values.put("estadoInscritoActivo", jsonObject.getBoolean("estadoInscritoActivo"));

                            values.put("idUsuario", jsonObjectIdUsuario.getInt("idUsuario"));
                            values.put("idCurso", jsonObjectIdCursos.getInt("idCurso"));

                            long resultado = db.insert(Atributos.table_inscritos, null, values);

                            System.out.println(resultado + " INSCRITOS ALMACENADA CORRECTAMENTE");

                            resultados.add(resultado);

                            boolean exito = true;
                            for (long result : resultados) {
                                if (result == -1) {
                                    exito = false;
                                    break;
                                }
                            }

                            if (exito) {
                                listener.onImportExito(response.length());
                            } else {
                                listener.onImportError();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onImportError();
                        }
                    }
                } else {
                    listener.onImportError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL INSCRITOS............................");
                error.printStackTrace();
                listener.onImportError();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestunit = Volley.newRequestQueue(conection);
        requestunit.add(jsonArrayRequest);
        requestunit.start();
    }

    public void importarParticipanteMatriculado(final OnImportListener listener){
        String uri = host + "participantesMatriculados/listar";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO PARTICIPANTE.....................................");
                if (response.length() != 0) {
                    SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                    for (int a = 0; a < response.length(); a++) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(a).toString());
                            JSONObject jsonObjectIdUsuario = new JSONObject(jsonObject.get("inscrito").toString());

                            values = new ContentValues();
                            values.put("idParticipanteMatriculado", jsonObject.getInt("idParticipanteMatriculado"));
                            values.put("estadoParticipanteAprobacion", jsonObject.getString("estadoParticipanteAprobacion"));
                            values.put("estadoParticipanteActivo", jsonObject.getBoolean("estadoParticipanteActivo"));

                            values.put("idInscrito", jsonObjectIdUsuario.getInt("idInscrito"));

                            long resultado = db.insert(Atributos.table_participante, null, values);

                            System.out.println(resultado + " PARTICIPANTE ALMACENADA CORRECTAMENTE");

                            resultados.add(resultado);

                            boolean exito = true;
                            for (long result : resultados) {
                                if (result == -1) {
                                    exito = false;
                                    break;
                                }
                            }

                            if (exito) {
                                listener.onImportExito(response.length());
                            } else {
                                listener.onImportError();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onImportError();
                        }
                    }
                } else {
                    listener.onImportError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL PARTICIPANTE............................");
                System.out.println(error.getMessage());
                listener.onImportError();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestunit = Volley.newRequestQueue(conection);
        requestunit.add(jsonArrayRequest);
        requestunit.start();
    }

    public void importarAsistencia(final OnImportListener listener){
        String uri = host + "asistencia/list";
        final List<Long> resultados = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("CARGANDO ASISTENCIA.....................................");
                if (response.length() != 0) {
                    SQLiteDatabase db = (new DataBaseTemporal(conection)).getWritableDatabase();
                    for (int a = 0; a < response.length(); a++) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(a).toString());
                            JSONObject jsonObjectIdParticipante = new JSONObject(jsonObject.get("partipantesMatriculados").toString());

                            values = new ContentValues();
                            values.put("idAsistencia", jsonObject.getInt("idAsistencia"));
                            values.put("fechaAsistencia", jsonObject.getString("fechaAsistencia"));
                            values.put("estadoAsistencia", jsonObject.getBoolean("estadoAsistencia"));
                            values.put("observacionAsistencia", jsonObject.getString("observacionAsistencia"));
                            values.put("estadoSubida", true);
                            values.put("estadoActual", "Descargado");
                            values.put("idParticipanteMatriculado", jsonObjectIdParticipante.getInt("idParticipanteMatriculado"));

                            long resultado = db.insert(Atributos.table_asistencia, null, values);

                            System.out.println(resultado + " ASISTENCIA ALMACENADA CORRECTAMENTE");

                            resultados.add(resultado);

                            boolean exito = true;
                            for (long result : resultados) {
                                if (result == -1) {
                                    exito = false;
                                    break;
                                }
                            }

                            if (exito) {
                                listener.onImportExito(response.length());
                            } else {
                                listener.onImportError();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onImportError();
                        }
                    }
                } else {
                    listener.onImportError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FINAL ASISTENCIA............................");
                error.printStackTrace();
                listener.onImportError();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestunit = Volley.newRequestQueue(conection);
        requestunit.add(jsonArrayRequest);
        requestunit.start();
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
        Random random = new Random();
        SQLiteDatabase db = (new DataBase(conection)).getWritableDatabase();
        for (int a = 1; a < 10000; a++) {


            values = new ContentValues();
            values.put("idUsuario", a);
            values.put("username", "admin" + a);
            values.put("password", "root" + a);
            //values.put("fotoPerfil", "imgusuarios" + random.nextInt(5) + 1);
            values.put("nombreRol", "fotoPerfil " + a);
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
        for (int a = 1; a < 5; a++) {


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
        for (int a = 1; a < 50; a++) {


            values = new ContentValues();
            String fec[] = fechasAleatorias();
            values.put("idCurso", a);
            values.put("nombreCurso", "nombreCurso " + a);
            values.put("fotoCurso", "imgcurso" + random.nextInt(5) + 1);
            values.put("fotoCurso", "fotoCurso " + a);
            values.put("duracionCurso", Integer.parseInt(fec[2]));
            values.put("observacionCurso", "");
            values.put("estadoCurso", true);
            values.put("estadoAprovacionCurso", "A");
            values.put("estadoPublicasionCurso", "V");
            values.put("descripcionCurso", "descripcionCurso " + a);
            values.put("objetivoGeneralesCurso", "objetivoGeneralesCurso " + a);
            values.put("numeroCuposCurso", a);
            values.put("fechaInicioCurso", fec[0]);
            values.put("fechaFinalizacionCurso", fec[1]);

            values.put("nombreEspecialidad", "nombreEspecialidad " + a);
            values.put("nombreArea", "nombreArea " + a);
            values.put("nombreTipoCurso", "nombreTipoCurso " + a);
            values.put("nombreModalidadCurso", "nombreModalidadCurso " + a);
            values.put("horaInicio", "horaInicio " + a);
            values.put("horaFin", "horaFin " + a);


            values.put("idPrograma", random.nextInt(10) + 1);
            values.put("idCapacitador", random.nextInt(5) + 1);
            System.out.println(String.valueOf(db.insert(Atributos.table_cursos, null, values)) + " CURSO ALMACENADA CORRECTAMENTE");
        }
    }

    public String[] fechasAleatorias(){
        String fech[] = new String[3];
        Random random = new Random();
        LocalDate fecha1, fecha2;
        do {
            int year1 = random.nextInt(3) + 2021;
            int year2 = random.nextInt(3) + 2022;
            int month1 = random.nextInt(12) + 1;
            int month2 = random.nextInt(12) + 1;
            int maxDay1 = YearMonth.of(year1, month1).lengthOfMonth();
            int maxDay2 = YearMonth.of(year2, month2).lengthOfMonth();
            int day1 = random.nextInt(maxDay1) + 1;
            int day2 = random.nextInt(maxDay2) + 1;
            fecha1 = LocalDate.of(year1, month1, day1);
            fecha2 = LocalDate.of(year2, month2, day2);
        } while (fecha1.compareTo(fecha2) <= 0);

        long diasFaltantes = ChronoUnit.DAYS.between(fecha2, fecha1);

        fech[0] = String.valueOf(fecha1);
        fech[1] = String.valueOf(fecha2);
        fech[2] = String.valueOf(diasFaltantes);

        System.out.println(fech[0] + "  fffffffffffffffffffffffffff");
        System.out.println(fech[1] + "  fffffffffffffffffffffffffff");
        System.out.println(fech[2] + "  fffffffffffffffffffffffffff");

        return fech;
    }

    public void cargarDatosTemporalesPrerequisitos(){
        SQLiteDatabase db = (new DataBase(conection)).getWritableDatabase();
        Random random = new Random();
        for (int a = 1; a < 1000; a++) {


            values = new ContentValues();
            values.put("idPrerequisitoCurso", a);
            values.put("estadoPrerequisitoCurso", true);
            values.put("nombrePrerequisitoCurso", "nombrePrerequisitoCurso " + a);

            values.put("idCurso", random.nextInt(50) + 1);
            System.out.println(String.valueOf(db.insert(Atributos.table_prerequisitos, null, values)) + " PREREQUISITOS ALMACENADA CORRECTAMENTE");
        }
    }

    public void cargarDatosTemporalesInscritos(){
        SQLiteDatabase db = (new DataBase(conection)).getWritableDatabase();
        Random random = new Random();

        int cont = 6;

        for (int a = 1; a < 50; a++) {

            int[] numeros = random.ints(6, 10000)
                    .distinct()
                    .limit(20)
                    .toArray();

            for (int e = 1; e < 20; e++) {
                if (cont <= 10000) {
                    values = new ContentValues();
                    values.put("idInscrito", cont);
                    values.put("fechaInscrito", "fechaInscrito " + a);
                    values.put("estadoInscrito", true);
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
