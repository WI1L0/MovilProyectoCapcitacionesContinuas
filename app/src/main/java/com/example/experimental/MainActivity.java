package com.example.experimental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.experimental.DB.DataBase;
import com.example.experimental.DB.DataBaseTemporal;
import com.example.experimental.DB.ImportData;
import com.example.experimental.Utilidades.Atributos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    //use database
    private DataBase conection1;
    private DataBaseTemporal conection2;


    //vista
    private Button btninicio, btndatos, btnbase, btnparticipantee, btncapacitador;
    private EditText edtusername, edtpassword;

    private int id_usu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //use database
        conection1 = new DataBase(getApplicationContext());
        conection2 = new DataBaseTemporal(getApplicationContext());


        //crear database
        SQLiteDatabase sdb = (new DataBase(MainActivity.this).getWritableDatabase());
        SQLiteDatabase sdb2 = (new DataBaseTemporal(MainActivity.this).getWritableDatabase());
        if (sdb != null){
            Toast.makeText(this, "BASE DE DATOS CREADA", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ERROR EN CREAR BASE DE DATOS ", Toast.LENGTH_SHORT).show();
        }


        //vista
        btninicio = (Button) findViewById(R.id.btninicio);
        btndatos = (Button) findViewById(R.id.btndatos);
        btnbase = (Button) findViewById(R.id.btndatabasetemporal);
        btnparticipantee = (Button) findViewById(R.id.btnparticipante);
        btncapacitador = (Button) findViewById(R.id.btncapacitador);

        edtusername = (EditText) findViewById(R.id.editTextUserName);
        edtpassword = (EditText) findViewById(R.id.editTextPassword);

        if (control(Atributos.table_persona) == false || control(Atributos.table_usuarios) == false || control(Atributos.table_programas) == false || control(Atributos.table_capacitador) == false || control(Atributos.table_rol) == false ||
            control(Atributos.table_cursos) == false || control(Atributos.table_prerequisitos) == false || control(Atributos.table_inscritos) == false || control(Atributos.table_participante) == false || control(Atributos.table_asistencia) == false) {

        //(control(Atributos.table_persona) == false && control(Atributos.table_usuarios) == false && control(Atributos.table_programas) == false && control(Atributos.table_capacitador) == false) {
            t();
        }


        edtusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btncapacitador.setVisibility(View.GONE);
                btnparticipantee.setVisibility(View.GONE);
                btninicio.setVisibility(View.GONE);
                btninicio.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btncapacitador.setVisibility(View.GONE);
                btnparticipantee.setVisibility(View.GONE);
                btninicio.setVisibility(View.GONE);
                btninicio.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        btninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasa();
            }
        });

        btnparticipantee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uno("Participante", id_usu);
            }
        });

        btncapacitador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uno("DocenteCapacitador", id_usu);
            }
        });




        btndatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImportData importData = new ImportData(getApplicationContext());
                importData.importarDatos();
            }
        });

        btnbase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t();
            }
        });
    }

    public void t(){
        Intent intent = new Intent(this, Import.class);
        startActivity(intent);
    }

    public void pasa() {
        SQLiteDatabase db = conection1.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("SELECT u.idUsuario, r.nombreRol FROM usuarios u INNER JOIN rolusuario ru ON ru.idUsuario = u.idUsuario INNER JOIN roles r ON r.idRol = ru.idRol " +
                        "WHERE u.username = ? AND u.password = ? AND u.estadoUsuarioActivo = '1' AND r.estadoRolActivo = '1';",
                new String[]{edtusername.getText().toString(), edtpassword.getText().toString()});


            if (cursor1.moveToFirst()) {
                System.out.println("wwwwwwwwwwwwwwww1");
                ArrayList<String> arrayrol = new ArrayList<>();
                if (cursor1.getCount() > 1) {
                    System.out.println("wwwwwwwwwwwwwwww2");
                    while (cursor1.moveToNext()) {
                        id_usu = cursor1.getInt(0);
                        arrayrol.add(cursor1.getString(1));
                    }


                    Boolean estadmin = false;

                    if (arrayrol.size() == 2) {
                        System.out.println("wwwwwwwwwwwwwwww3");
                        for (int a = 0; a < arrayrol.size(); a++) {
                            if (arrayrol.get(a).equals("Administrador")) {
                                System.out.println("wwwwwwwwwwwwwwww4");
                                estadmin = true;
                            }
                        }
                    }

                    if (estadmin == false) {
                        System.out.println("wwwwwwwwwwwwwwww5");
                        btncapacitador.setVisibility(View.VISIBLE);
                        btnparticipantee.setVisibility(View.VISIBLE);
                        btninicio.setVisibility(View.GONE);
                    } else {
                        System.out.println("wwwwwwwwwwwwwwww6");

                        String rol = null;
                        for (int a = 0; a < arrayrol.size(); a++) {
                            if (!arrayrol.get(a).equals("Administrador")) {
                                System.out.println("wwwwwwwwwwwwwwww8");
                                rol = arrayrol.get(a);
                            }
                        }

                        uno(rol, cursor1.getInt(0));

                    }
                } else {
                    System.out.println("wwwwwwwwwwwwwwww9");

                    uno(cursor1.getString(1), cursor1.getInt(0));

                }
            } else {
                System.out.println("wwwwwwwwwwwwwwww10");
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            }

        cursor1.close();
        db.close();
    }

    public void uno(String rol, int id){
        SQLiteDatabase db = conection1.getReadableDatabase();

        if (rol.equals("DocenteCapacitador")){
            Cursor cursor2 = db.rawQuery("SELECT " + Atributos.atr_cap_id + ", " + Atributos.atr_cap_estado_capacitador + " FROM " + Atributos.table_capacitador +
                            " WHERE " + Atributos.atr_usu_id + " = ?;",
                    new String[]{String.valueOf(id)});

            if (cursor2.moveToFirst()) {

                if (cursor2.getInt(1) == 1) {
                    Toast.makeText(this, "Bienvenido Capacitador", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, Programas.class);

                    intent.putExtra("id", cursor2.getInt(0));
                    intent.putExtra("rol", "capacitador");
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Usted esta bloqueado", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Acceso denegado", Toast.LENGTH_SHORT).show();
            }

            cursor2.close();
        }
        if (rol.equals("Participante")) {


            Toast.makeText(this, "Bienvenido Alumno", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Programas.class);
            intent.putExtra("id", id);
            intent.putExtra("rol", "alumno");
            startActivity(intent);
        }

        if (rol.equals("Administrador")) {
            Toast.makeText(this, "Administrador sin acceso", Toast.LENGTH_SHORT).show();
        }
    }



    public Boolean control(String tab){
        SQLiteDatabase db = conection1.getWritableDatabase();
        Boolean estado = false;
        Cursor cursor = db.query(Atributos.table_control, new String[]{"estado"}, "nametable = ?", new String[]{tab}, null, null, null);

        while (cursor.moveToNext()) {
            estado = cursor.getInt(0) == 1 ? true : false;
        }
        return estado;
    }
}