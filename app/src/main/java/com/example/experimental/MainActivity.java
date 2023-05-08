package com.example.experimental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.experimental.DB.DataBase;
import com.example.experimental.DB.DataBaseTemporal;
import com.example.experimental.DB.ImportData;
import com.example.experimental.Utilidades.Atributos;

public class MainActivity extends AppCompatActivity{

    //use database
    private DataBase conection1;
    private DataBaseTemporal conection2;


    //vista
    private Button btninicio, btndatos, btnbase;
    private EditText edtusername, edtpassword;


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

        edtusername = (EditText) findViewById(R.id.editTextUserName);
        edtpassword = (EditText) findViewById(R.id.editTextPassword);

        if (control(Atributos.table_persona) == false || control(Atributos.table_usuarios) == false || control(Atributos.table_programas) == false || control(Atributos.table_capacitador) == false ||
            control(Atributos.table_cursos) == false || control(Atributos.table_prerequisitos) == false || control(Atributos.table_inscritos) == false || control(Atributos.table_participante) == false || control(Atributos.table_asistencia) == false) {

        //(control(Atributos.table_persona) == false && control(Atributos.table_usuarios) == false && control(Atributos.table_programas) == false && control(Atributos.table_capacitador) == false) {
            //t();
        }

        btninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasa();
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

        Cursor cursor1 = db.rawQuery("SELECT " + Atributos.atr_usu_id + ", " + Atributos.atr_usu_rol +
                        " FROM " + Atributos.table_usuarios +
                        " WHERE " + Atributos.atr_usu_user + " = ? AND " + Atributos.atr_usu_paswork + " = ? AND " + Atributos.atr_usu_estado_activo + " = '1';",
                new String[]{edtusername.getText().toString(), edtpassword.getText().toString()});


            if (cursor1.moveToFirst()) {
                int id_usu = 0;
                id_usu = cursor1.getInt(0);

                if (cursor1.getString(1).equals("DocenteCapacitador")){
                    Cursor cursor2 = db.rawQuery("SELECT " + Atributos.atr_cap_id + ", " + Atributos.atr_cap_estado_capacitador + " FROM " + Atributos.table_capacitador +
                                    " WHERE " + Atributos.atr_usu_id + " = ?;",
                            new String[]{String.valueOf(id_usu)});

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
                if (cursor1.getString(1).equals("Participante")) {


                    Toast.makeText(this, "Bienvenido Alumno", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, Programas.class);
                    intent.putExtra("id", id_usu);
                    intent.putExtra("rol", "alumno");
                    startActivity(intent);
                }

                if (cursor1.getString(1).equals("Administrador")) {
                    Toast.makeText(this, "Administrador sin acceso", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            }
        cursor1.close();
        db.close();
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