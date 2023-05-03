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
import com.example.experimental.Progresst_Bar.ManejoProgressBar;
import com.example.experimental.Utilidades.Atributos;

import java.util.Timer;
import java.util.TimerTask;

public class Import extends AppCompatActivity {

    private ImportData importData;

    private ImageView imgimport;
    private ProgressBar pgsimport;
    private Button btnimport, btnfinalizar;
    private AsyncTask tarea;

    private ManejoProgressBar manejoProgressBar;

    //use database
    private DataBaseTemporal conection;
    private ContentValues values;

    private int progreso = 0;

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

        verificarAll();

        btnimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnimport.setEnabled(false);
                manejoProgressBar = new ManejoProgressBar(pgsimport);
                manejoProgressBar.execute();

                final int[] a = {0};
                cargar(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        a[0]++;
                        if (a[0] == 8){
                            verificarAll();
                            btnimport.setEnabled(true);
                        }
                    }

                    @Override
                    public void onImportError() {
                        a[0]++;
                        if (a[0] == 8){
                            verificarAll();
                            btnimport.setEnabled(true);
                            btnimport.setText("REINTENTAR");
                        }
                    }
                });

            }
        });

        btnfinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //importBase();
                DataBaseTransaction dataBaseTransaction = new DataBaseTransaction(getApplicationContext());
                dataBaseTransaction.newControl(new OnImportListener() {
                    @Override
                    public void onImportExito(int leng) {
                        pgsimport.setProgress(leng);
                    }

                    @Override
                    public void onImportError() {
                        btnfinalizar.setText("REINTENTAR");
                    }
                });
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
                            progreso = progreso + 10;
                            pgsimport.setProgress(progreso);
                            listenerMein.onImportExito(0);
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
                            progreso = progreso + 10;
                            pgsimport.setProgress(progreso);
                            listenerMein.onImportExito(0);
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
                            progreso = progreso + 10;
                            pgsimport.setProgress(progreso);
                            listenerMein.onImportExito(0);
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
                            progreso = progreso + 10;
                            pgsimport.setProgress(progreso);
                            listenerMein.onImportExito(0);
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
                            progreso = progreso + 10;
                            pgsimport.setProgress(progreso);
                            listenerMein.onImportExito(0);
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
                            progreso = progreso + 10;
                            pgsimport.setProgress(progreso);
                            listenerMein.onImportExito(0);
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
                final double[] progresoInterno = {0};

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
                                        progreso = progreso + 10;
                                        pgsimport.setProgress(progreso);
                                        listenerMein.onImportExito(0);
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
                            progreso = progreso + 10;
                            pgsimport.setProgress(progreso);
                            listenerMein.onImportExito(0);
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
    }

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

    public void verificarAll(){
        if //(control(Atributos.table_persona) == true && control(Atributos.table_usuarios) == true && control(Atributos.table_programas) == true && control(Atributos.table_capacitador) == true &&
            // control(Atributos.table_cursos) == true && control(Atributos.table_prerequisitos) == true && control(Atributos.table_inscritos) == true && control(Atributos.table_asistencia) == true) {

        (control(Atributos.table_persona) == true && control(Atributos.table_usuarios) == true && control(Atributos.table_programas) == true && control(Atributos.table_capacitador) == true) {

            progreso = barActualizar();
            pgsimport.setProgress(progreso);

            Toast.makeText(this, "Datos almacenados", Toast.LENGTH_SHORT).show();
            btnimport.setVisibility(View.INVISIBLE);
            btnfinalizar.setVisibility(View.VISIBLE);
        } else {

            progreso = barActualizar();
            pgsimport.setProgress(progreso);
        }
    }

    public int barActualizar(){
        SQLiteDatabase db = conection.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + Atributos.table_control + " WHERE estado = '1'", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count * 10;
    }
}