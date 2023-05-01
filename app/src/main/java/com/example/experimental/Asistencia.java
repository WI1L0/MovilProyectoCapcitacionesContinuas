package com.example.experimental;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.experimental.Adaptadores.AsistenciaAdaptador;
import com.example.experimental.DB.DataBase;
import com.example.experimental.Modelos.MAsistencia;
import com.example.experimental.Modelos.MCursos;
import com.example.experimental.Modelos.MInscritos;
import com.example.experimental.Modelos.MPersona;
import com.example.experimental.Modelos.MUsuario;
import com.example.experimental.Utilidades.Atributos;
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
    MInscritos mInscritos;
    MUsuario mUsuario;
    MPersona mPersona;
    ArrayList<MInscritos> listInscritos;
    ArrayList<MAsistencia> listaasistencia = new ArrayList<>();


    //vista
    private RecyclerView recycleViewListado;
    private Button btnguardar, btnfecha;
    private TextView txtfecha;


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
        btnfecha = (Button) findViewById(R.id.btnfecha);
        txtfecha = (TextView) findViewById(R.id.txtvfecha);


        //fecha seleccionar
        //MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

        obtenerFechaActual();

        consultarListaInscritos(idCurso);

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SQLiteDatabase db = conection.getReadableDatabase();

                if (listaasistencia.size() != 0){

                    Cursor cursor = db.rawQuery("SELECT a.idAsistencia FROM asistencia a INNER JOIN inscritos i ON i.idInscrito = a.idInscrito WHERE a.fechaAsistencia = ? AND i.idCurso = ?;",
                            new String[]{String.valueOf(txtfecha.getText()), String.valueOf(idCurso)});

                    if (cursor.moveToFirst()) {
                        for (int i = 0; i < listaasistencia.size(); i++) {
                            ContentValues values = new ContentValues();
                            values.put("fechaAsistencia", listaasistencia.get(i).getFechaAsistencia());
                            values.put("estadoAsistencia", listaasistencia.get(i).getEstadoAsistencia());
                            values.put("observacionAsistencia", listaasistencia.get(i).getObservacionAsistencia());
                            db.update(Atributos.table_asistencia, values, Atributos.atr_asi_id + " = ?", new String[]{String.valueOf(listaasistencia.get(i).getIdAsistencia())});
                        }
                        finish();
                        //moveToProgramas(id, rol);
                    } else {
                        if (listInscritos.size() == listaasistencia.size()) {
                            for (int i = 0; i < listaasistencia.size(); i++) {
                                ContentValues values = new ContentValues();
                                values.put("fechaAsistencia", String.valueOf(txtfecha.getText()));
                                values.put("estadoAsistencia", listaasistencia.get(i).getEstadoAsistencia());
                                values.put("observacionAsistencia", listaasistencia.get(i).getObservacionAsistencia());
                                values.put("idInscrito", listaasistencia.get(i).getmInscritos().getIdInscrito());
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
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                consultarListaInscritos(idCurso);
            }
        });



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
        listInscritos = new ArrayList<>();

        Cursor cursor2 = db.rawQuery("SELECT a.idAsistencia FROM asistencia a INNER JOIN inscritos i ON i.idInscrito = a.idInscrito WHERE a.fechaAsistencia = ? AND i.idCurso = ?;",
                new String[]{String.valueOf(txtfecha.getText()), String.valueOf(id)});

        Cursor cursor;
        Boolean actualisarOCrear = false;

        if (cursor2.moveToFirst()) {
            cursor = db.rawQuery("SELECT i.idInscrito, i.estadoParticipanteActivo, p.nombre1, p.nombre2, p.apellido1, p.apellido2, idAsistencia, fechaAsistencia, estadoAsistencia, observacionAsistencia " +
                            "FROM personas p INNER JOIN usuarios u ON u.idPersona = p.idPersona INNER JOIN inscritos i ON i.idUsuario = u.idUsuario INNER JOIN asistencia a ON a.idInscrito = i.idInscrito " +
                            "WHERE i.idCurso = ? AND a.fechaAsistencia = ? AND estadoInscritoActivo = '1' ORDER BY apellido1 DESC;",
                    new String[]{String.valueOf(id), String.valueOf(txtfecha.getText())});
            actualisarOCrear = true;
        } else {
            cursor = db.rawQuery("SELECT i.idInscrito, i.estadoParticipanteActivo, p.nombre1, p.nombre2, p.apellido1, p.apellido2 " +
                            "FROM personas p INNER JOIN usuarios u ON u.idPersona = p.idPersona INNER JOIN inscritos i ON i.idUsuario = u.idUsuario " +
                            "WHERE i.idCurso = ? AND estadoInscritoActivo = '1' ORDER BY apellido1 DESC;",
                    new String[]{String.valueOf(id)});
        }

        while (cursor.moveToNext()) {
            mInscritos = new MInscritos();
            mUsuario = new MUsuario();
            mPersona = new MPersona();
            mAsistencia = new MAsistencia();

            if (actualisarOCrear == true){
                ArrayList<MAsistencia> arrayList = new ArrayList<>();

                mAsistencia.setIdAsistencia(cursor.getInt(6));
                mAsistencia.setFechaAsistencia(cursor.getString(7));
                mAsistencia.setEstadoAsistencia(cursor.getInt(8) == 1 ? true : false);
                mAsistencia.setObservacionAsistencia(cursor.getString(9));

                arrayList.add(mAsistencia);
                mInscritos.setmAsistenciaList(arrayList);
            }

            mInscritos.setIdInscrito(cursor.getInt(0));
            mInscritos.setEstadoParticipanteActivo(cursor.getInt(1) == 1 ? true : false);

            mPersona.setNombre1(cursor.getString(2));
            mPersona.setNombre2(cursor.getString(3));
            mPersona.setApellido1(cursor.getString(4));
            mPersona.setApellido2(cursor.getString(5));

            mUsuario.setmPersona(mPersona);
            mInscritos.setmUsuario(mUsuario);

            listInscritos.add(mInscritos);
        }

        init();
    }

    public void init() {
        AsistenciaAdaptador asistenciaAdaptador = new AsistenciaAdaptador(listInscritos, this, new AsistenciaAdaptador.OnItemClickListener() {
            @Override
            public void onItemClick(MInscritos item) {

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

        Boolean ingreso = false;

        if (!listaasistencia.isEmpty()){
            System.out.println("aaaaaaaaa");
            for (int a = 0 ; a < listaasistencia.size() ; a++){
                System.out.println("bbbbbbbbbbbbbbb");
                if (listaasistencia.get(a).getmInscritos().getIdInscrito() == mAsistencia.getmInscritos().getIdInscrito()){
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