package com.example.complexivo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.complexivo.Adaptadores.AsistenciaAdaptador;
import com.example.complexivo.DB.DataBase;
import com.example.complexivo.Modelos.MAsistencia;
import com.example.complexivo.Modelos.MCursos;
import com.example.complexivo.Modelos.MInscritos;
import com.example.complexivo.Modelos.MParticipante;
import com.example.complexivo.Modelos.MPersona;
import com.example.complexivo.Modelos.MUsuario;
import com.example.complexivo.Utilidades.Atributos;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Asistencia extends AppCompatActivity {

    MAsistencia mAsistencia;
    MParticipante mParticipante;
    MInscritos mInscritos;
    MUsuario mUsuario;
    MPersona mPersona;
    ArrayList<MParticipante> listParticipantes;
    ArrayList<MAsistencia> listaasistencia = new ArrayList<>();

    private Boolean actualisarOCrear = false;

    //vista
    private RecyclerView recycleViewListado;
    private Button btnguardar;
    private ImageButton btnfecha;
    private TextView txtfecha;
    private SearchView svasistencia;


    //use database
    private DataBase conection;

    //dato de curso
    private int idCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);

        //use database
        conection = new DataBase(getApplicationContext());


        //datos de programas
        idCurso = (int) getIntent().getSerializableExtra("idCurso");
        //int id = (int) getIntent().getSerializableExtra("id");
        //String rol = (String) getIntent().getSerializableExtra("rol");


        //vista
        recycleViewListado = (RecyclerView) findViewById(R.id.recicleasistencia);
        btnguardar = (Button) findViewById(R.id.btnguardarasistencia);
        btnfecha = (ImageButton) findViewById(R.id.btnfecha);
        txtfecha = (TextView) findViewById(R.id.txtvfecha);
        svasistencia = (SearchView) findViewById(R.id.svasistencia);


        //fecha seleccionar
        //MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

        obtenerFechaActual();

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = conection.getReadableDatabase();

                if (listaasistencia.size() != 0){

                    Cursor cursor = db.rawQuery("SELECT a.idAsistencia FROM asistencia a INNER JOIN participante pa ON pa.idParticipanteMatriculado = a.idParticipanteMatriculado INNER JOIN inscritos i ON i.idInscrito = pa.idInscrito WHERE a.fechaAsistencia = ? AND i.idCurso = ?;",
                            new String[]{String.valueOf(txtfecha.getText()), String.valueOf(idCurso)});

                    if (cursor.moveToNext()) {
                        for (int i = 0; i < listaasistencia.size(); i++) {

                                Cursor cursor1 = db.rawQuery("SELECT estadoActual FROM asistencia WHERE idAsistencia = ?;",
                                        new String[]{String.valueOf(listaasistencia.get(i).getIdAsistencia())});

                                ContentValues values = new ContentValues();
                                values.put("fechaAsistencia", listaasistencia.get(i).getFechaAsistencia());
                                values.put("estadoAsistencia", listaasistencia.get(i).getEstadoAsistencia());
                                values.put("observacionAsistencia", listaasistencia.get(i).getObservacionAsistencia());
                                values.put("estadoSubida", false);
                            if (listaasistencia.get(i).getEstadoActual() != null && listaasistencia.get(i).getEstadoActual().equals("Descargado")) {
                                values.put("estadoActual", "Actualizado");
                            }

                                db.update(Atributos.table_asistencia, values, Atributos.atr_asi_id + " = ?", new String[]{String.valueOf(listaasistencia.get(i).getIdAsistencia())});

                        }
                        finish();
                        // (id, rol);
                    } else {
                        if (listParticipantes.size() == listaasistencia.size()) {
                            for (int i = 0; i < listaasistencia.size(); i++) {
                                ContentValues values = new ContentValues();
                                values.put("fechaAsistencia", String.valueOf(txtfecha.getText()));
                                values.put("estadoAsistencia", listaasistencia.get(i).getEstadoAsistencia());
                                values.put("observacionAsistencia", listaasistencia.get(i).getObservacionAsistencia());
                                values.put("estadoSubida", false);
                                values.put("estadoActual", "Creado");
                                values.put("idParticipanteMatriculado", listaasistencia.get(i).getmParticipante().getIdParticipanteMatriculado());
                                db.insert(Atributos.table_asistencia, null, values);
                            }
                            finish();
                            //moveToProgramas(id, rol);
                        } else {
                            Toast.makeText(Asistencia.this, "Faltan datos por llenar", Toast.LENGTH_SHORT).show();
                            // alerta de asi q quiera guardar con datos vacios pueda aserlo
                        }
                    }
                }
                    db.close();
            }
        });

        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Asistencia.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        LocalDate selectedDate = LocalDate.of(year, month + 1, day);
                        txtfecha.setText(selectedDate.toString());
                        recargarListaAsistencia(idCurso);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        consultarListaInscritos(idCurso);

        svasistencia.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String sa) {
                filder(sa);
                return false;
            }
        });

    }

    public void filder(String sa){
        ArrayList<MParticipante> filtered = new ArrayList<>();
        for (MParticipante item : listParticipantes){
            if (item.getmInscritos().getmUsuario().getmPersona().getApellido1().toLowerCase().contains(sa.toLowerCase())) {
                filtered.add(item);
            }
        }

        AsistenciaAdaptador asistenciaAdaptador = new AsistenciaAdaptador(filtered, this, new AsistenciaAdaptador.OnItemClickListener() {
            @Override
            public void onItemClick(MParticipante item) {

            }

            @Override
            public void obtenList(MAsistencia item) {

                crearArrayAsistencia(item);
            }
        });

        recycleViewListado.setHasFixedSize(true);
        recycleViewListado.setLayoutManager(new LinearLayoutManager(this));
        recycleViewListado.setAdapter(asistenciaAdaptador);
    }

    public void obtenerFechaActual(){
        Calendar cal = Calendar.getInstance();
        Date fechaActual = cal.getTime();

        Instant instant = fechaActual.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate fechaLocal = instant.atZone(zoneId).toLocalDate();

        txtfecha.setText(String.valueOf(fechaLocal));
    }

    public void consultarListaInscritos(int id){
        SQLiteDatabase db = conection.getReadableDatabase();
        listParticipantes = new ArrayList<>();

        Cursor cursor2 = db.rawQuery("SELECT a.idAsistencia FROM asistencia a INNER JOIN participante pa ON pa.idParticipanteMatriculado = a.idParticipanteMatriculado INNER JOIN inscritos i ON i.idInscrito = pa.idInscrito WHERE a.fechaAsistencia = ? AND i.idCurso = ?;",
                new String[]{String.valueOf(txtfecha.getText()), String.valueOf(id)});

        Cursor cursor;

        if (cursor2.moveToFirst()) {
            cursor = db.rawQuery("SELECT pa.idParticipanteMatriculado, p.nombre1, p.nombre2, p.apellido1, p.apellido2, i.estadoInscritoActivo,  pa.estadoParticipanteActivo, u.fotoPerfil, a.idAsistencia, a.fechaAsistencia, a.estadoAsistencia, a.observacionAsistencia, a.estadoActual " +
                            "FROM personas p INNER JOIN usuarios u ON u.idPersona = p.idPersona INNER JOIN inscritos i ON i.idUsuario = u.idUsuario INNER JOIN participante pa ON pa.idInscrito = i.idInscrito INNER JOIN asistencia a ON a.idParticipanteMatriculado = pa.idParticipanteMatriculado " +
                            "wHERE i.idCurso = ? AND a.fechaAsistencia = ? AND i.estadoInscrito = '1' AND pa.estadoParticipanteActivo = '1' ORDER BY apellido1 DESC;",
                    new String[]{String.valueOf(id), String.valueOf(txtfecha.getText())});
            actualisarOCrear = true;
        } else {
            cursor = db.rawQuery("SELECT pa.idParticipanteMatriculado, p.nombre1, p.nombre2, p.apellido1, p.apellido2, i.estadoInscritoActivo,  pa.estadoParticipanteActivo, u.fotoPerfil " +
                            "FROM personas p INNER JOIN usuarios u ON u.idPersona = p.idPersona INNER JOIN inscritos i ON i.idUsuario = u.idUsuario INNER JOIN participante pa ON pa.idInscrito = i.idInscrito " +
                            "wHERE i.idCurso = ? AND i.estadoInscrito = '1' AND pa.estadoParticipanteActivo = '1' ORDER BY apellido1 DESC;",
                    new String[]{String.valueOf(id)});
            actualisarOCrear = false;
        }

        while (cursor.moveToNext()) {
            mParticipante = new MParticipante();
            mInscritos = new MInscritos();
            mUsuario = new MUsuario();
            mPersona = new MPersona();
            mAsistencia = new MAsistencia();

            if (actualisarOCrear == true){
                ArrayList<MAsistencia> arrayList = new ArrayList<>();

                mAsistencia.setIdAsistencia(cursor.getInt(8));
                mAsistencia.setFechaAsistencia(cursor.getString(9));
                mAsistencia.setEstadoAsistencia(cursor.getInt(10) == 1 ? true : false);
                mAsistencia.setObservacionAsistencia(cursor.getString(11));
                mAsistencia.setEstadoActual(cursor.getString(12));

                arrayList.add(mAsistencia);
                mParticipante.setmAsistenciaList(arrayList);
            }



            mPersona.setNombre1(cursor.getString(1));
            mPersona.setNombre2(cursor.getString(2));
            mPersona.setApellido1(cursor.getString(3));
            mPersona.setApellido2(cursor.getString(4));


            mUsuario.setmPersona(mPersona);
            mUsuario.setFotoPerfil(cursor.getString(7));


            mInscritos.setmUsuario(mUsuario);
            mInscritos.setEstadoInscritoActivo(cursor.getInt(5) == 1 ? true : false);

            mParticipante.setmInscritos(mInscritos);
            mParticipante.setIdParticipanteMatriculado(cursor.getInt(0));
            mParticipante.setEstadoParticipanteAprobacion(cursor.getString(6));


            listParticipantes.add(mParticipante);
        }

        init();
    }

    public void init() {
            AsistenciaAdaptador asistenciaAdaptador = new AsistenciaAdaptador(listParticipantes, this, new AsistenciaAdaptador.OnItemClickListener() {
            @Override
            public void onItemClick(MParticipante item) {

            }

            @Override
            public void obtenList(MAsistencia item) {

                crearArrayAsistencia(item);
            }
        });

        recycleViewListado.setHasFixedSize(true);
        recycleViewListado.setLayoutManager(new LinearLayoutManager(this));
        recycleViewListado.setAdapter(asistenciaAdaptador);
    }


    public void crearArrayAsistencia(MAsistencia mAsistencia){

        if (actualisarOCrear == false) {
            System.out.println("vacio");
            Boolean ingreso = false;

            if (!listaasistencia.isEmpty()){
                System.out.println("aaaaaaaaa");
                for (int a = 0 ; a < listaasistencia.size() ; a++){
                    System.out.println("bbbbbbbbbbbbbbb");
                    if (listaasistencia.get(a).getmParticipante().getIdParticipanteMatriculado() == mAsistencia.getmParticipante().getIdParticipanteMatriculado()){
                        System.out.println("ccccccccccccccccc");
                        ingreso = true;
                        listaasistencia.get(a).setEstadoAsistencia(mAsistencia.getEstadoAsistencia());
                        listaasistencia.get(a).setObservacionAsistencia(mAsistencia.getObservacionAsistencia());
                    }
                }

                if(ingreso == false){
                    System.out.println("dddddddddddddddddddddddddddddddddddddd");
                    listaasistencia.add(mAsistencia);
                }

            } else {
                System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeee");
                listaasistencia.add(mAsistencia);
            }
        }

        if (actualisarOCrear == true){

            Boolean ingreso = false;

            for (int a = 0 ; a < listaasistencia.size() ; a++){
                System.out.println("bbbbbbbbbbbbbbb");
                if (listaasistencia.get(a).getmParticipante().getIdParticipanteMatriculado() == mAsistencia.getmParticipante().getIdParticipanteMatriculado()){
                    System.out.println("ccccccccccccccccc");
                    ingreso = true;
                    listaasistencia.get(a).setEstadoAsistencia(mAsistencia.getEstadoAsistencia());
                    listaasistencia.get(a).setObservacionAsistencia(mAsistencia.getObservacionAsistencia());
                }
            }

            if(ingreso == false){
                System.out.println("dddddddddddddddddddddddddddddddddddddd");
                listaasistencia.add(mAsistencia);
            }

        }
    }

    public void recargarListaAsistencia(int id) {
        consultarListaInscritos(id);
        AsistenciaAdaptador adaptador = (AsistenciaAdaptador)recycleViewListado.getAdapter();
        adaptador.notifyDataSetChanged();
    }

    /*public void moveToProgramas(int id, String rol) {
            Intent intent = new Intent(this, Programas.class);
            intent.putExtra("id", id);
            intent.putExtra("rol", rol);
            startActivity(intent);
    }*/

    /*public void moveToDescription(MListado item) {
        Intent intent = new Intent(this, Cursos.class);
        intent.putExtra("MProgramas", item);
        startActivity(intent);
    }*/
}