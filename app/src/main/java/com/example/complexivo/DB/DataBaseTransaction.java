package com.example.complexivo.DB;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.example.complexivo.Import;
import com.example.complexivo.MainActivity;
import com.example.complexivo.Programas;
import com.example.complexivo.Utilidades.Atributos;

import java.util.ArrayList;
import java.util.List;

public class DataBaseTransaction {

    private Context context;
    private ContentValues values;
    private DataBase conectionFinal;
    private DataBaseTemporal conectionTemporal;

    private Import anImport = new Import();

    private int progreso = 80;
    public DataBaseTransaction(Context mcontext) {
        context = mcontext;
    }

    public void newControl(final OnImportListener listenerMein) {

        conectionFinal = new DataBase(context);
        conectionTemporal = new DataBaseTemporal(context);

        if (limpiartable(Atributos.table_control) == true) {
            conectionFinal.insercontrol();
        }

        //context.deleteDatabase("db_final");

        if (verificarAll()){
            Toast.makeText(context, "Datos fin almacenados", Toast.LENGTH_SHORT).show();
            context.deleteDatabase("db_final_temp");
            progreso = progreso + 2;
            listenerMein.onImportExito(progreso);
        } else {
            if (control(Atributos.table_persona) == false) {

                //PERSONA
                final int[] cont = {0};
                newPersona(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos personas fin descargados");
                            updatecontrol(Atributos.table_persona);
                            progreso = progreso + 2;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin personas");
                        limpiartable(Atributos.table_persona);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_rol) == false) {

                //ROL
                final int[] cont = {0};
                newRol(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos roles fin descargados");
                            updatecontrol(Atributos.table_rol);
                            progreso = progreso + 1;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin roles");
                        limpiartable(Atributos.table_rol);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_rol_usu) == false) {

                //ROLUSUARIOS
                final int[] cont = {0};
                newRolUsuario(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos rolusuarios fin descargados");
                            updatecontrol(Atributos.table_rol_usu);
                            progreso = progreso + 1;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin rolusuarios");
                        limpiartable(Atributos.table_rol_usu);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_usuarios) == false) {

                //USUARIO
                final int[] cont = {0};
                newUsuario(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos usuarios fin descargados");
                            updatecontrol(Atributos.table_usuarios);
                            progreso = progreso + 2;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin usuarios");
                        limpiartable(Atributos.table_rol_usu);
                        limpiartable(Atributos.table_usuarios);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_programas) == false) {

                //PROGRAMA
                final int[] cont = {0};
                newProgramas(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos programas fin descargados");
                            updatecontrol(Atributos.table_programas);
                            progreso = progreso + 2;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin programas");
                        limpiartable(Atributos.table_programas);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_capacitador) == false) {

                //CAPACITADOR
                final int[] cont = {0};
                newCapacitador(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos capacitadores fin descargados");
                            updatecontrol(Atributos.table_capacitador);
                            progreso = progreso + 2;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin capacitadores");
                        limpiartable(Atributos.table_capacitador);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_cursos) == false) {

                //CURSO
                final int[] cont = {0};
                newCursos(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos cursos fin descargados");
                            updatecontrol(Atributos.table_cursos);
                            progreso = progreso + 2;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin cursos");
                        limpiartable(Atributos.table_cursos);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_prerequisitos) == false) {

                //PREREQUISITO
                final int[] cont = {0};
                newPrerequisitos(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos prerequisitos fin descargados");
                            updatecontrol(Atributos.table_prerequisitos);
                            progreso = progreso + 2;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin prerequisitos");
                        limpiartable(Atributos.table_prerequisitos);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_inscritos) == false) {

                //INSCRITO
                final int[] cont = {0};
                newInscritos(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos inscritos fin descargados");
                            updatecontrol(Atributos.table_inscritos);
                            progreso = progreso + 2;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin inscritos");
                        limpiartable(Atributos.table_inscritos);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_participante) == false) {

                //PARTICIPANTE
                final int[] cont = {0};
                newParticipante(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos participante fin descargados");
                            updatecontrol(Atributos.table_participante);
                            progreso = progreso + 2;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin participante");
                        limpiartable(Atributos.table_participante);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_asistencia) == false) {

                //ASISTENCIA
                final int[] cont = {0};
                newAsistencia(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            System.out.println("Datos asistencias fin descargados");
                            updatecontrol(Atributos.table_asistencia);
                            progreso = progreso + 2;
                            listenerMein.onImportExito(progreso);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar fin asistencias");
                        limpiartable(Atributos.table_asistencia);
                        listenerMein.onImportError();
                    }
                });

            }

            if (verificarAll()){
                Toast.makeText(context, "Datos fin almacenados", Toast.LENGTH_SHORT).show();
                context.deleteDatabase("db_final_temp");
                progreso = progreso + 2;
                listenerMein.onImportExito(progreso);
            }
        }
    }

    public void newPersona(final OnImportListener listener) {
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idPersona, identificacion, nombre1, nombre2, apellido1, apellido2, correo, telefono, celular, genero, etnia FROM " + Atributos.table_persona, null);

        if (cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idPersona", cursor.getInt(0));
                values.put("identificacion", cursor.getString(1));
                values.put("nombre1", cursor.getString(2));
                values.put("nombre2", cursor.getString(3));
                values.put("apellido1", cursor.getString(4));
                values.put("apellido2", cursor.getString(5));
                values.put("correo", cursor.getString(6));
                values.put("telefono", cursor.getString(7));
                values.put("celular", cursor.getString(8));
                values.put("genero", cursor.getString(9));
                values.put("etnia", cursor.getString(10));

                long resultado = sdbFinal.insert(Atributos.table_persona, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }

    public void newRol(final OnImportListener listener){
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idRol, estadoRolActivo, nombreRol FROM " + Atributos.table_rol, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idRol", cursor.getInt(0));
                values.put("estadoRolActivo", cursor.getInt(1));
                values.put("nombreRol", cursor.getString(2));

                long resultado = sdbFinal.insert(Atributos.table_rol, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }

    public void newRolUsuario(final OnImportListener listener){
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idUsuario, idRol FROM " + Atributos.table_rol_usu, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idUsuario", cursor.getInt(0));
                values.put("idRol", cursor.getInt(1));

                long resultado = sdbFinal.insert(Atributos.table_rol_usu, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }

    public void newUsuario(final OnImportListener listener){
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idUsuario, username, password, fotoPerfil, estadoUsuarioActivo, idPersona FROM " + Atributos.table_usuarios, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idUsuario", cursor.getInt(0));
                values.put("username", cursor.getString(1));
                values.put("password", cursor.getString(2));
                values.put("fotoPerfil", cursor.getString(3));
                values.put("estadoUsuarioActivo", cursor.getInt(4));
                values.put("idPersona", cursor.getInt(5));

                long resultado = sdbFinal.insert(Atributos.table_usuarios, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }

    public void newCapacitador(final OnImportListener listener){
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idCapacitador, estadoActivoCapacitador, tituloCapacitador, idUsuario FROM " + Atributos.table_capacitador, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idCapacitador", cursor.getInt(0));
                values.put("estadoActivoCapacitador", cursor.getInt(1));
                values.put("tituloCapacitador", cursor.getString(2));
                values.put("idUsuario", cursor.getInt(3));

                long resultado = sdbFinal.insert(Atributos.table_capacitador, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }

    public void newProgramas(final OnImportListener listener){
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idPrograma, nombrePrograma, estadoProgramaActivo, estadoPeriodoPrograma, fechaInicioPeriodoPrograma, fechaFinPeriodoPrograma, nombrePeriodoPrograma FROM " + Atributos.table_programas, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idPrograma", cursor.getInt(0));
                values.put("nombrePrograma", cursor.getString(1));
                values.put("estadoProgramaActivo", cursor.getInt(2));
                values.put("estadoPeriodoPrograma", cursor.getInt(3));
                values.put("fechaInicioPeriodoPrograma", cursor.getString(4));
                values.put("fechaFinPeriodoPrograma", cursor.getString(5));
                values.put("nombrePeriodoPrograma", cursor.getString(6));

                long resultado = sdbFinal.insert(Atributos.table_programas, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }

    public void newCursos(final OnImportListener listener){
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idCurso, nombreCurso, fotoCurso, duracionCurso, observacionCurso, " +
                "estadoCurso, estadoAprovacionCurso, estadoPublicasionCurso, descripcionCurso, objetivoGeneralesCurso, " +
                "numeroCuposCurso, fechaInicioCurso, fechaFinalizacionCurso, nombreEspecialidad, nombreArea, " +
                "nombreTipoCurso, nombreModalidadCurso, horaInicio, horaFin, idCapacitador, idPrograma " +
                "FROM " + Atributos.table_cursos, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idCurso", cursor.getInt(0));
                values.put("nombreCurso", cursor.getString(1));
                values.put("fotoCurso", cursor.getString(2));
                values.put("duracionCurso", cursor.getInt(3));
                values.put("observacionCurso", cursor.getString(4));
                values.put("estadoCurso", cursor.getInt(5));
                values.put("estadoAprovacionCurso", cursor.getString(6));
                values.put("estadoPublicasionCurso", cursor.getString(7));
                values.put("descripcionCurso", cursor.getString(8));
                values.put("objetivoGeneralesCurso", cursor.getString(9));
                values.put("numeroCuposCurso", cursor.getInt(10));
                values.put("fechaInicioCurso", cursor.getString(11));
                values.put("fechaFinalizacionCurso", cursor.getString(12));
                values.put("nombreEspecialidad", cursor.getString(13));
                values.put("nombreArea", cursor.getString(14));
                values.put("nombreTipoCurso", cursor.getString(15));
                values.put("nombreModalidadCurso", cursor.getString(16));
                values.put("horaInicio", cursor.getString(17));
                values.put("horaFin", cursor.getString(18));
                values.put("idCapacitador", cursor.getInt(19));
                values.put("idPrograma", cursor.getInt(20));

                long resultado = sdbFinal.insert(Atributos.table_cursos, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }

    public void newPrerequisitos(final OnImportListener listener){
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idPrerequisitoCurso, estadoPrerequisitoCurso, nombrePrerequisitoCurso, idCurso FROM " + Atributos.table_prerequisitos, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idPrerequisitoCurso", cursor.getInt(0));
                values.put("estadoPrerequisitoCurso", cursor.getInt(1));
                values.put("nombrePrerequisitoCurso", cursor.getString(2));
                values.put("idCurso", cursor.getInt(3));

                long resultado = sdbFinal.insert(Atributos.table_prerequisitos, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }

    public void newInscritos(final OnImportListener listener){
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idInscrito, fechaInscrito, estadoInscrito, estadoInscritoActivo, idUsuario, idCurso FROM " + Atributos.table_inscritos, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idInscrito", cursor.getInt(0));
                values.put("fechaInscrito", cursor.getString(1));
                values.put("estadoInscrito", cursor.getInt(2));
                values.put("estadoInscritoActivo", cursor.getInt(3));

                values.put("idUsuario", cursor.getInt(4));
                values.put("idCurso", cursor.getInt(5));

                long resultado = sdbFinal.insert(Atributos.table_inscritos, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }

    public void newParticipante(final OnImportListener listener){
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idParticipanteMatriculado, estadoParticipanteAprobacion, estadoParticipanteActivo, idInscrito FROM " + Atributos.table_participante, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idParticipanteMatriculado", cursor.getInt(0));
                values.put("estadoParticipanteAprobacion", cursor.getString(1));
                values.put("estadoParticipanteActivo", cursor.getInt(2));

                values.put("idInscrito", cursor.getInt(3));

                long resultado = sdbFinal.insert(Atributos.table_participante, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }

    public void newAsistencia(final OnImportListener listener){
        SQLiteDatabase sdbFinal = conectionFinal.getWritableDatabase();
        SQLiteDatabase sdbTemporal = conectionTemporal.getReadableDatabase();
        final List<Long> resultados = new ArrayList<>();

        Cursor cursor = sdbTemporal.rawQuery("SELECT idAsistencia, fechaAsistencia, estadoAsistencia, observacionAsistencia, estadoSubida, estadoActual, idParticipanteMatriculado FROM " + Atributos.table_asistencia, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                values = new ContentValues();
                values.put("idAsistencia", cursor.getInt(0));
                values.put("fechaAsistencia", cursor.getString(1));
                values.put("estadoAsistencia", cursor.getInt(2));
                values.put("observacionAsistencia", cursor.getString(3));
                values.put("estadoSubida", cursor.getInt(4));
                values.put("estadoActual", cursor.getString(5));
                values.put("idParticipanteMatriculado", cursor.getInt(6));

                long resultado = sdbFinal.insert(Atributos.table_asistencia, null, values);

                resultados.add(resultado);

                boolean exito = true;
                for (long result : resultados) {
                    if (result == -1) {
                        exito = false;
                        break;
                    }
                }

                if (exito) {
                    listener.onImportExito(cursor.getCount());
                } else {
                    listener.onImportError();
                }

            }
        } else {
            listener.onImportError();
        }
    }


    public boolean limpiartable(String table){
        SQLiteDatabase db = conectionFinal.getWritableDatabase();

        if (!table.equals(Atributos.table_control)) {
            db.execSQL("DELETE FROM " + table);
        }

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

    public void updatecontrol(String table){
        SQLiteDatabase db = conectionFinal.getWritableDatabase();

        String sql = "UPDATE " + Atributos.table_control + " SET " + Atributos.atr_con_estado + " = ? WHERE " + Atributos.atr_con_table + " = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, "1");
        statement.bindString(2, table);
        statement.executeUpdateDelete();
    }

    public Boolean control(String tab){
        SQLiteDatabase db = conectionFinal.getWritableDatabase();
        Boolean estado = false;
        Cursor cursor = db.query(Atributos.table_control, new String[]{"estado"}, "nametable = ?", new String[]{tab}, null, null, null);

        while (cursor.moveToNext()) {
            estado = cursor.getInt(0) == 1 ? true : false;
        }
        return estado;
    }

    public boolean verificarAll(){
        if (control(Atributos.table_persona) == true && control(Atributos.table_usuarios) == true && control(Atributos.table_programas) == true && control(Atributos.table_capacitador) == true && control(Atributos.table_rol) == true &&
             control(Atributos.table_cursos) == true && control(Atributos.table_prerequisitos) == true && control(Atributos.table_inscritos) == true && control(Atributos.table_participante) == true && control(Atributos.table_asistencia) == true) {

        //(control(Atributos.table_persona) == true && control(Atributos.table_usuarios) == true && control(Atributos.table_programas) == true && control(Atributos.table_capacitador) == true) {

            progreso = progreso + barActualizar();

            context.deleteDatabase("db_final_temp");
            try {
                Thread.sleep(10*1000);
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (InterruptedException e) {
                Log.d(TAG, "onCreate: +++++++++++++++++++++++++++++++++++++++++");
            }
            return true;
        } else {

            progreso = progreso + barActualizar();
            return false;
        }
    }

    public int barActualizar(){
        SQLiteDatabase db = conectionFinal.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + Atributos.table_control + " WHERE estado = '1'", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        int resu = count * 2;
        if (resu > 10){
            resu = 10;
        }

        return resu;
    }

}
