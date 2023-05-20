package com.example.complexivo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.complexivo.Adaptadores.CursosAdaptador;
import com.example.complexivo.DB.DataBase;
import com.example.complexivo.Modelos.MCursos;
import com.example.complexivo.Modelos.MProgramas;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cursos extends AppCompatActivity {

    MCursos mCursos;
    ArrayList<MCursos> listaCursos;

    //vista
    private RecyclerView recycleViewCursos;
    private SearchView svcursos;

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
        Boolean cursosAll = (Boolean) getIntent().getSerializableExtra("cursosAll");


        //vista
        recycleViewCursos = (RecyclerView) findViewById(R.id.recicleCursos);
        svcursos = (SearchView) findViewById(R.id.svcursos);


        consultarListaCursos(idProgramas, id, rol, cursosAll);

        svcursos.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String sa) {
                filder(sa, id, rol);
                return false;
            }
        });

    }

    public void filder(String sa, int id, String rol){
        ArrayList<MCursos> filtered = new ArrayList<>();
        for (MCursos item : listaCursos){
            if (item.getNombreCurso().toLowerCase().contains(sa.toLowerCase())) {
                filtered.add(item);
            }
        }

        CursosAdaptador cursosAdaptador = new CursosAdaptador(filtered, this, new CursosAdaptador.OnItemClickListener() {
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

    private void consultarListaCursos(int idProgramas, int id, String rol, Boolean est) {
        listaCursos = new ArrayList<>();
        SQLiteDatabase db = conection.getReadableDatabase();
        Cursor cursor;

        if (rol.equals("alumno")) {
            if (est == false){

                cursor = db.rawQuery("SELECT c.idCurso, c.nombreCurso, c.duracionCurso, c.nombreModalidadCurso, c.nombreTipoCurso, c.nombreEspecialidad, c.nombreArea, c.fechaInicioCurso, c.fechaFinalizacionCurso, c.fotoCurso " +
                                "FROM cursos c INNER JOIN inscritos i ON i.idCurso = c.idCurso " +
                                //"WHERE i.idUsuario = ? AND c.idPrograma = ? AND c.estadoAprovacionCurso != 'N' AND c.estadoCurso = '1' AND c.estadoPublicasionCurso = 'V' ORDER BY c.nombreCurso DESC;",
                                "WHERE i.idUsuario = ? AND c.idPrograma = ? AND c.estadoAprovacionCurso = 'A' AND c.estadoCurso = '1' AND c.estadoPublicasionCurso in ('I','F')   ORDER BY c.nombreCurso DESC;",
                        new String[]{String.valueOf(id), String.valueOf(idProgramas)});

            } else {

                cursor = db.rawQuery("SELECT idCurso, nombreCurso, duracionCurso, nombreModalidadCurso, nombreTipoCurso, nombreEspecialidad, nombreArea, fechaInicioCurso, fechaFinalizacionCurso, fotoCurso " +
                                //"FROM cursos WHERE idPrograma = ? AND estadoAprovacionCurso != 'N' AND estadoCurso = '1' AND estadoPublicasionCurso = 'V' ORDER BY nombreCurso DESC;",
                                "FROM cursos WHERE idPrograma = ? AND estadoAprovacionCurso = 'A' AND estadoCurso = '1' AND estadoPublicasionCurso = 'V' ORDER BY nombreCurso DESC;",
                        new String[]{String.valueOf(idProgramas)});

            }
        } else {

            cursor = db.rawQuery("SELECT idCurso, nombreCurso, duracionCurso, nombreModalidadCurso, nombreTipoCurso, nombreEspecialidad, nombreArea, fechaInicioCurso, fechaFinalizacionCurso, fotoCurso " +
                            //"FROM cursos WHERE idCapacitador = ? AND idPrograma = ? AND estadoAprovacionCurso != 'N' AND estadoCurso = '1' AND estadoPublicasionCurso = 'V' ORDER BY nombreCurso DESC;",
                            "FROM cursos WHERE idCapacitador = ? AND idPrograma = ? AND estadoAprovacionCurso = 'A' AND estadoCurso = '1' AND estadoPublicasionCurso = 'I' ORDER BY nombreCurso DESC;",
                    new String[]{String.valueOf(id), String.valueOf(idProgramas)});

        }

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
            mCursos.setFotoCurso(cursor.getString(9));
            listaCursos.add(mCursos);
        }
        init(rol);

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