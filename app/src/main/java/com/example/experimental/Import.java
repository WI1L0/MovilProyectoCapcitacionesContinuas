package com.example.experimental;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.experimental.DB.DataBase;
import com.example.experimental.DB.DataBaseTemporal;
import com.example.experimental.DB.DataBaseTransaction;
import com.example.experimental.DB.ImportData;
import com.example.experimental.DB.OnImportListener;
import com.example.experimental.Utilidades.Atributos;

import java.util.Timer;
import java.util.TimerTask;

public class Import extends AppCompatActivity {

    ImportData importData;

    ImageView imgimport;
    ProgressBar pgsimport;
    Button btnimport, btnfinalizar;
    AsyncTask tarea;

    //use database
    private DataBaseTemporal conection;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        //use database
        conection = new DataBaseTemporal(getApplicationContext());

        imgimport = (ImageView) findViewById(R.id.imgimportdata);
        pgsimport = (ProgressBar) findViewById(R.id.pgbimportdata);
        btnimport = (Button) findViewById(R.id.btnimportdata);
        btnfinalizar = (Button) findViewById(R.id.btnfinalizarimportdata);

        if (limpiartable(Atributos.table_control) == true){
            conection.insercontrol();
        }

        if (verificarAll()) {


            btnimport.setVisibility(View.INVISIBLE);
            btnfinalizar.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Datos almacenados", Toast.LENGTH_SHORT).show();
        }

        btnimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnimport.setEnabled(false);

                cargar(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                    }

                    @Override
                    public void onImportError() {
                        btnimport.setText("REINTENTAR");
                    }
                });
            }
        });

        btnfinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //importBase();
                DataBaseTransaction dataBaseTransaction = new DataBaseTransaction(getApplicationContext());
                dataBaseTransaction.newControl();
                //cargar base final
            }
        });
    }

    public void cargar(final OnImportListener listenerMein){
        importData = new ImportData(getApplicationContext());


            if (control(Atributos.table_persona) == false) {

                //PERSONA
                final int[] cont = {0};
                importData.importarPersonas(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            Toast.makeText(Import.this, "Datos personas descargados", Toast.LENGTH_SHORT).show();
                            updatecontrol(Atributos.table_persona);
                        }
                    }

                    @Override
                    public void onImportError() {
                        Toast.makeText(Import.this, "Error en descargar personas", Toast.LENGTH_SHORT).show();
                        limpiartable(Atributos.table_persona);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_usuarios) == false) {

                //USUARIO
                final int[] cont = {0};
                importData.importarUsuarios(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            Toast.makeText(Import.this, "Datos usuarios descargados", Toast.LENGTH_SHORT).show();
                            updatecontrol(Atributos.table_usuarios);
                        }
                    }

                    @Override
                    public void onImportError() {
                        Toast.makeText(Import.this, "Error en descargar usuarios", Toast.LENGTH_SHORT).show();
                        limpiartable(Atributos.table_usuarios);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_programas) == false) {

                //PROGRAMA
                final int[] cont = {0};
                importData.importarProgramas(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            Toast.makeText(Import.this, "Datos programas descargados", Toast.LENGTH_SHORT).show();
                            updatecontrol(Atributos.table_programas);
                        }
                    }

                    @Override
                    public void onImportError() {
                        Toast.makeText(Import.this, "Error en descargar programas", Toast.LENGTH_SHORT).show();
                        limpiartable(Atributos.table_programas);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_capacitador) == false) {

                //CAPACITADOR
                final int[] cont = {0};
                importData.importarCapacitador(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            Toast.makeText(Import.this, "Datos capacitadores descargados", Toast.LENGTH_SHORT).show();
                            updatecontrol(Atributos.table_capacitador);
                        }
                    }

                    @Override
                    public void onImportError() {
                        Toast.makeText(Import.this, "Error en descargar capacitadores", Toast.LENGTH_SHORT).show();
                        limpiartable(Atributos.table_capacitador);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_cursos) == false) {

                //CURSO
                final int[] cont = {0};
                importData.importarCursos(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            Toast.makeText(Import.this, "Datos cursos descargados", Toast.LENGTH_SHORT).show();
                            updatecontrol(Atributos.table_cursos);
                        }
                    }

                    @Override
                    public void onImportError() {
                        Toast.makeText(Import.this, "Error en descargar cursos", Toast.LENGTH_SHORT).show();
                        limpiartable(Atributos.table_cursos);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_prerequisitos) == false) {

                //PREREQUISITO
                final int[] cont = {0};
                importData.importarPrerequisitos(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            Toast.makeText(Import.this, "Datos prerequisitos descargados", Toast.LENGTH_SHORT).show();
                            updatecontrol(Atributos.table_prerequisitos);
                        }
                    }

                    @Override
                    public void onImportError() {
                        Toast.makeText(Import.this, "Error en descargar prerequisitos", Toast.LENGTH_SHORT).show();
                        limpiartable(Atributos.table_prerequisitos);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_inscritos) == false) {

                //INSCRITO
                final int[] cont = {0};
                importData.importarInscrito(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            Toast.makeText(Import.this, "Datos inscritos descargados", Toast.LENGTH_SHORT).show();

                            //MATRICULADO
                            final int[] cont1 = {0};
                            importData.importarParticipanteMatriculado(new OnImportListener() {
                                @Override
                                public void onImportExito(int leng) {
                                    cont1[0]++;
                                    if (cont1[0] == leng) {
                                        Toast.makeText(Import.this, "Datos matriculados descargados", Toast.LENGTH_SHORT).show();
                                        updatecontrol(Atributos.table_inscritos);
                                    }
                                }

                                @Override
                                public void onImportError() {
                                    Toast.makeText(Import.this, "Error en descargar matriculados", Toast.LENGTH_SHORT).show();
                                    limpiartable(Atributos.table_inscritos);
                                    listenerMein.onImportError();
                                }
                            });

                        }
                    }

                    @Override
                    public void onImportError() {
                        Toast.makeText(Import.this, "Error en descargar inscritos", Toast.LENGTH_SHORT).show();
                        limpiartable(Atributos.table_inscritos);
                        listenerMein.onImportError();
                    }
                });

            }

            if (control(Atributos.table_asistencia) == false) {

                //ASISTENCIA
                final int[] cont = {0};
                importData.importarAsistencia(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        cont[0]++;
                        if (cont[0] == leng) {
                            Toast.makeText(Import.this, "Datos asistencias descargados", Toast.LENGTH_SHORT).show();
                            updatecontrol(Atributos.table_asistencia);
                        }
                    }

                    @Override
                    public void onImportError() {
                        Toast.makeText(Import.this, "Error en descargar asistencias", Toast.LENGTH_SHORT).show();
                        limpiartable(Atributos.table_asistencia);
                        listenerMein.onImportError();
                    }
                });

            }

            if (verificarAll()) {

                Toast.makeText(this, "Datos almacenados", Toast.LENGTH_SHORT).show();
                btnimport.setVisibility(View.INVISIBLE);
                btnfinalizar.setVisibility(View.VISIBLE);

            }

            btnimport.setEnabled(true);

    }

    /*public void importBase(){

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
                    importData.limpiartable(Atributos.table_persona);
                    if (importData.importarPersonas() == true){
                        progreso = progreso + 12;
                        pgsimport.setProgress(progreso);
                        Toast.makeText(Import.this, "Error solucionado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Import.this, "Intentelo nuevamente mas tarde", Toast.LENGTH_SHORT).show();
                        importData.limpiartable(Atributos.table_persona);
                    }
                }

                if (progreso == 12){
                    t.cancel();
                }
            }
        };
        t.schedule(timerTask, 0, 100);
    }*/

    public boolean limpiartable(String table){
        SQLiteDatabase db = conection.getWritableDatabase();

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
        SQLiteDatabase db = conection.getWritableDatabase();

        String sql = "UPDATE " + Atributos.table_control + " SET " + Atributos.atr_con_estado + " = ? WHERE " + Atributos.atr_con_table + " = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, "1");
        statement.bindString(2, table);
        statement.executeUpdateDelete();
    }

    public Boolean control(String tab){
        SQLiteDatabase db = conection.getWritableDatabase();
        Boolean estado = false;
        Cursor cursor = db.query(Atributos.table_control, new String[]{"estado"}, "nametable = ?", new String[]{tab}, null, null, null);

        while (cursor.moveToNext()) {
            estado = cursor.getInt(0) == 1 ? true : false;
        }
        return estado;
    }

    public boolean verificarAll(){
        if //(control(Atributos.table_persona) == true && control(Atributos.table_usuarios) == true && control(Atributos.table_programas) == true && control(Atributos.table_capacitador) == true &&
            // control(Atributos.table_cursos) == true && control(Atributos.table_prerequisitos) == true && control(Atributos.table_inscritos) == true && control(Atributos.table_asistencia) == true) {

        (control(Atributos.table_persona) == true && control(Atributos.table_usuarios) == true && control(Atributos.table_programas) == true && control(Atributos.table_capacitador) == true && control(Atributos.table_prerequisitos) == true) {


            return true;
        } else {
            return false;
        }
    }
}