package com.example.experimental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import com.example.experimental.Adaptadores.CursosAdaptador;
import com.example.experimental.DB.DataBase;
import com.example.experimental.Modelos.MCursos;
import com.example.experimental.Modelos.MProgramas;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cursos extends AppCompatActivity {

    MCursos mCursos;
    ArrayList<MCursos> listaCursos;


    //vista
    private RecyclerView recycleViewCursos;


    //use database
    private DataBase conection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        //use database
        conection = new DataBase(getApplicationContext());


        //datos de programas
        int idProgramas = (int) getIntent().getSerializableExtra("idPrograma");
        int id = (int) getIntent().getSerializableExtra("id");
        String rol = (String) getIntent().getSerializableExtra("rol");


        //vista
        recycleViewCursos = (RecyclerView) findViewById(R.id.recicleCursos);


        consultarListaCursos(idProgramas, id, rol);
    }

    private void consultarListaCursos(int idProgramas, int id, String rol) {
        SQLiteDatabase db = conection.getReadableDatabase();

        if (rol.equals("alumno")) {
            listaCursos = new ArrayList<>();

            Cursor cursor = db.rawQuery("SELECT c.idCurso, c.nombreCurso, c.duracionCurso, c.nombreModalidadCurso, c.nombreTipoCurso, c.nombreEspecialidad, c.nombreArea, c.fechaInicioCurso, c.fechaFinalizacionCurso " +
                            "FROM cursos c INNER JOIN inscritos i ON i.idCurso = c.idCurso " +
                            "WHERE i.idUsuario = ? AND c.idPrograma = ? AND c.estadoAprovacionCurso = '1' AND c.estadoCurso = '1' ORDER BY c.nombreCurso DESC;",
                    new String[]{String.valueOf(id), String.valueOf(idProgramas)});

            while (cursor.moveToNext()) {
                mCursos = new MCursos();
                mCursos.setIdCurso(cursor.getInt(0));
                mCursos.setNombreCurso(cursor.getString(1));
                mCursos.setDuracionCurso(cursor.getInt(2));
                mCursos.setNombreModalidadCurso(cursor.getString(3));
                mCursos.setNombreTipoCurso(cursor.getString(4));
                mCursos.setNombreEspecialidad(cursor.getString(5));
                mCursos.setNombreArea(cursor.getString(6));
                mCursos.setFechaInicioCurso(cursor.getString(7));
                mCursos.setFechaFinalizacionCurso(cursor.getString(8));

                listaCursos.add(mCursos);
            }
            init(rol);
        } else {
            listaCursos = new ArrayList<>();

            Cursor cursor = db.rawQuery("SELECT idCurso, nombreCurso, duracionCurso, nombreModalidadCurso, nombreTipoCurso, nombreEspecialidad, nombreArea, fechaInicioCurso, fechaFinalizacionCurso " +
                            "FROM cursos WHERE idCapacitador = ? AND idPrograma = ? AND estadoAprovacionCurso = 1 AND estadoCurso = 1 ORDER BY nombreCurso DESC;",
                    new String[]{String.valueOf(id), String.valueOf(idProgramas)});

            while (cursor.moveToNext()) {
                mCursos = new MCursos();
                mCursos.setIdCurso(cursor.getInt(0));
                mCursos.setNombreCurso(cursor.getString(1));
                mCursos.setDuracionCurso(cursor.getInt(2));
                mCursos.setNombreModalidadCurso(cursor.getString(3));
                mCursos.setNombreTipoCurso(cursor.getString(4));
                mCursos.setNombreEspecialidad(cursor.getString(5));
                mCursos.setNombreArea(cursor.getString(6));
                mCursos.setFechaInicioCurso(cursor.getString(7));
                mCursos.setFechaFinalizacionCurso(cursor.getString(8));

                listaCursos.add(mCursos);
            }
            init(rol);
        }
    }

    public void init(String rol) {
        CursosAdaptador cursosAdaptador = new CursosAdaptador(listaCursos, this, new CursosAdaptador.OnItemClickListener() {
            @Override
            public void onItemClick(MCursos item) {
                moveToDescription(item, rol);
            }

            @Override
            public void onItemClickDetalle(int id) {
                moveToDescriptionDetalle(id);
            }
        });

        recycleViewCursos.setHasFixedSize(true);
        recycleViewCursos.setLayoutManager(new LinearLayoutManager(this));
        recycleViewCursos.setAdapter(cursosAdaptador);
    }

    public void moveToDescription(MCursos item, String rol) {
        if (rol.equals("capacitador")) {
            Intent intent = new Intent(this, Asistencia.class);
            intent.putExtra("idCurso", item.getIdCurso());
            //intent.putExtra("id", id);
            intent.putExtra("rol", rol);
            startActivity(intent);
        }
    }

    public void moveToDescriptionDetalle(int id) {
        Intent intent = new Intent(this, Detalle_Curso.class);
        intent.putExtra("idCurso", id);
        startActivity(intent);
    }
}