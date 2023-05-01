package com.example.experimental;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.experimental.DB.ImportData;
import com.example.experimental.Utilidades.Atributos;

import java.util.Timer;
import java.util.TimerTask;

public class Import extends AppCompatActivity {

    ImportData importData;

    ImageView imgimport;
    ProgressBar pgsimport;
    Button btnimport;
    AsyncTask tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        imgimport = (ImageView) findViewById(R.id.imgimportdata);
        pgsimport = (ProgressBar) findViewById(R.id.pgbimportdata);
        btnimport = (Button) findViewById(R.id.btnimportdata);

        btnimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //importBase();
                cargar();
            }
        });
    }

    public void cargar(){
        importData = new ImportData(getApplicationContext());

        //PERSONA
        if (importData.importarPersonas() == true){
            Toast.makeText(Import.this, "Datos personas descargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Import.this, "Error en descargar personas", Toast.LENGTH_SHORT).show();
        }

        //USUARIO
        if (importData.importarUsuarios() == true){
            Toast.makeText(Import.this, "Datos usuarios descargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Import.this, "Error en descargar usuarios", Toast.LENGTH_SHORT).show();
        }

        //PROGRAMA
        if (importData.importarProgramas() == true){
            Toast.makeText(Import.this, "Datos programas descargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Import.this, "Error en descargar programas", Toast.LENGTH_SHORT).show();
        }

        //CAPACITADOR
        if (importData.importarCapacitador() == true){
            Toast.makeText(Import.this, "Datos capacitadores descargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Import.this, "Error en descargar capacitadores", Toast.LENGTH_SHORT).show();
        }

        /*//CURSO
        if (importData.importarCursos() == true){
            Toast.makeText(Import.this, "Datos cursos descargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Import.this, "Error en descargar cursos", Toast.LENGTH_SHORT).show();
        }

        //PREREQUISITO
        if (importData.importarPrerequisitos() == true){
            Toast.makeText(Import.this, "Datos prerequisitos descargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Import.this, "Error en descargar prerequisitos", Toast.LENGTH_SHORT).show();
        }

        //INSCRITO
        if (importData.importarInscrito() == true){
            Toast.makeText(Import.this, "Datos inscritos descargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Import.this, "Error en descargar inscritos", Toast.LENGTH_SHORT).show();
        }

        //ASISTENCIA
        if (importData.importarAsistencia() == true){
            Toast.makeText(Import.this, "Datos asistencias descargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Import.this, "Error en descargar asistencias", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void importBase(){

        final Timer t = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int progreso = 0;
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");
                importData = new ImportData(getApplicationContext());

                //PERSONA
                if (importData.importarPersonas() == true){
                    progreso = progreso + 12;
                    pgsimport.setProgress(progreso);
                    Toast.makeText(Import.this, "Datos personas descargados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Import.this, "Error en descargar personas", Toast.LENGTH_SHORT).show();
                    /*importData.limpiartable(Atributos.table_persona);
                    if (importData.importarPersonas() == true){
                        progreso = progreso + 12;
                        pgsimport.setProgress(progreso);
                        Toast.makeText(Import.this, "Error solucionado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Import.this, "Intentelo nuevamente mas tarde", Toast.LENGTH_SHORT).show();
                        importData.limpiartable(Atributos.table_persona);
                    }*/
                }

                if (progreso == 12){
                    t.cancel();
                }
            }
        };
        t.schedule(timerTask, 0, 100);
    }
}