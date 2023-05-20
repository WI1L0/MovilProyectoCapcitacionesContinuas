package com.example.complexivo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.complexivo.DB.DataBase;
import com.example.complexivo.DB.DataBaseTemporal;
import com.example.complexivo.DB.DataBaseTransaction;
import com.example.complexivo.DB.ImportData;
import com.example.complexivo.DB.OnImportListener;
import com.example.complexivo.Progresst_Bar.ManejoProgressBar;
import com.example.complexivo.Utilidades.Atributos;

import java.util.Timer;
import java.util.TimerTask;

public class Import extends AppCompatActivity {

    private ImportData importData;

    private ImageView imgimport;
    private ProgressBar pgsimport;
    private Button btnimport, btnfinalizar;
    private AsyncTask tarea;
    private Boolean estimg = false;

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

        Drawable drawable2 = getResources().getDrawable(R.drawable.descargar_2);
        imgimport.setImageDrawable(drawable2);

        if (limpiartable(Atributos.table_control) == true){
            conection.insercontrol();
        }

        //verificarAllAsistencia();
        verificarAll();

        btnimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verificarAllAsistencia() == false) {

                    btnimport.setEnabled(false);
                    manejoProgressBar = new ManejoProgressBar(pgsimport);
                    manejoProgressBar.execute();
                    estimg = false;

                    int ntables = controlbtn() - 1;
                    cargar(new OnImportListener() {
                        int count = 0;
                        Boolean esterror = false;

                        @Override
                        public void onImportExito(int leng) {
                            img();
                            verificarAll();
                            count++;
                            if (count == ntables && esterror == true) {
                                btnimport.setEnabled(true);
                                btnimport.setText("REINTENTAR");

                                Drawable drawablef = getResources().getDrawable(R.drawable.reintentar_1);
                                imgimport.setImageDrawable(drawablef);
                            }
                        }

                        @Override
                        public void onImportError() {
                            verificarAll();
                            esterror = true;
                            count++;
                            if (count == ntables) {
                                btnimport.setEnabled(true);
                                btnimport.setText("REINTENTAR");

                                Drawable drawablef = getResources().getDrawable(R.drawable.reintentar_1);
                                imgimport.setImageDrawable(drawablef);
                            }
                        }
                    });


                }
            }
        });

        btnfinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getApplicationContext().deleteDatabase("db_final");
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
                            System.out.println("Datos personas descargados");
                            updatecontrol(Atributos.table_persona);
                            progreso = progreso + 8;
                            pgsimport.setProgress(progreso);
                            listenerMein.onImportExito(0);
                        }
                    }

                    @Override
                    public void onImportError() {
                        System.out.println("Error en descargar personas");
                        limpiartable(Atributos.table_persona);
                        listenerMein.onImportError();
                    }
                });

            }

        if (control(Atributos.table_rol) == false) {

            //ROL
            final int[] cont = {0};
            importData.importarRol(new OnImportListener() {
                @Override
                public void onImportExito(int leng) {

                    cont[0]++;
                    if (cont[0] == leng) {
                        System.out.println("Datos roles descargados");
                        updatecontrol(Atributos.table_rol);
                        progreso = progreso + 8;
                        pgsimport.setProgress(progreso);
                        listenerMein.onImportExito(0);
                    }
                }

                @Override
                public void onImportError() {
                    System.out.println("Error en descargar roles");
                    limpiartable(Atributos.table_rol);
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
                        System.out.println("Datos usuarios descargados");
                        updatecontrol(Atributos.table_usuarios);
                        updatecontrol(Atributos.table_rol_usu);
                        progreso = progreso + 8;
                        pgsimport.setProgress(progreso);
                        listenerMein.onImportExito(0);
                    }
                }

                @Override
                public void onImportError() {
                    System.out.println("Error en descargar usuarios");
                    limpiartable(Atributos.table_usuarios);
                    limpiartable(Atributos.table_rol_usu);
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
                        System.out.println("Datos programas descargados");
                        updatecontrol(Atributos.table_programas);
                        progreso = progreso + 8;
                        pgsimport.setProgress(progreso);
                        listenerMein.onImportExito(0);
                    }
                }

                @Override
                public void onImportError() {
                    System.out.println("Error en descargar programas");
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
                        System.out.println("Datos capacitadores descargados");
                        updatecontrol(Atributos.table_capacitador);
                        progreso = progreso + 8;
                        pgsimport.setProgress(progreso);
                        listenerMein.onImportExito(0);
                    }
                }

                @Override
                public void onImportError() {
                    System.out.println("Error en descargar capacitadores");
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
                        System.out.println("Datos cursos descargados");
                        updatecontrol(Atributos.table_cursos);
                        progreso = progreso + 8;
                        pgsimport.setProgress(progreso);
                        listenerMein.onImportExito(0);
                    }
                }

                @Override
                public void onImportError() {
                    System.out.println("Error en descargar cursos");
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
                        System.out.println("Datos prerequisitos descargados");
                        updatecontrol(Atributos.table_prerequisitos);
                        progreso = progreso + 8;
                        pgsimport.setProgress(progreso);
                        listenerMein.onImportExito(0);
                    }
                }

                @Override
                public void onImportError() {
                    System.out.println("Error en descargar prerequisitos");
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
                        System.out.println("Datos inscritos descargados");
                        updatecontrol(Atributos.table_inscritos);
                        progreso = progreso + 8;
                        pgsimport.setProgress(progreso);
                        listenerMein.onImportExito(0);

                    }
                }

                @Override
                public void onImportError() {
                    System.out.println("Error en descargar inscritos");
                    limpiartable(Atributos.table_inscritos);
                    listenerMein.onImportError();
                }
            });

        }

        if (control(Atributos.table_participante) == false) {
            final double[] progresoInterno = {0};

            //PARTICIPANTE
            final int[] cont = {0};
            importData.importarParticipanteMatriculado(new OnImportListener() {
                @Override
                public void onImportExito(int leng) {

                    cont[0]++;
                    if (cont[0] == leng) {
                        System.out.println("Datos participante descargados");
                        updatecontrol(Atributos.table_participante);
                        progreso = progreso + 8;
                        pgsimport.setProgress(progreso);
                        listenerMein.onImportExito(0);
                    }
                }

                @Override
                public void onImportError() {
                    System.out.println("Error en descargar participante");
                    limpiartable(Atributos.table_participante);
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
                        System.out.println("Datos asistencias descargados");
                        updatecontrol(Atributos.table_asistencia);
                        progreso = progreso + 8;
                        pgsimport.setProgress(progreso);
                        listenerMein.onImportExito(0);
                    }
                }

                @Override
                public void onImportError() {
                    System.out.println("Error en descargar asistencias");
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
        System.out.println();
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

    public Boolean controlAsistencia(){
        SQLiteDatabase db = conection.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT idAsistencia FROM asistencia WHERE estadoSubida = '0';", null);
        if (cursor.moveToFirst()) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaeeeeeee1");
            return true;
        } else {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaeeeeeeeee2");
            return false;
        }
    }

    public void verificarAll(){
        if (control(Atributos.table_persona) == true && control(Atributos.table_usuarios) == true && control(Atributos.table_programas) == true && control(Atributos.table_capacitador) == true && control(Atributos.table_rol) == true &&
             control(Atributos.table_cursos) == true && control(Atributos.table_prerequisitos) == true && control(Atributos.table_inscritos) == true && control(Atributos.table_participante) == true && control(Atributos.table_asistencia) == true) {

        //(control(Atributos.table_persona) == true && control(Atributos.table_usuarios) == true && control(Atributos.table_programas) == true && control(Atributos.table_capacitador) == true) {

            progreso = barActualizar();
            pgsimport.setProgress(progreso);

            Toast.makeText(this, "Datos almacenados", Toast.LENGTH_SHORT).show();
            btnimport.setVisibility(View.INVISIBLE);
            btnfinalizar.setVisibility(View.VISIBLE);

            estimg = true;
            Drawable drawablef = getResources().getDrawable(R.drawable.finalizando);
            imgimport.setImageDrawable(drawablef);
        } else {

            progreso = barActualizar();
            pgsimport.setProgress(progreso);
        }
    }

    public Boolean verificarAllAsistencia(){
        DataBase conection1 = new DataBase(Import.this);
        SQLiteDatabase db = conection1.getReadableDatabase();

        String[] projection = {"idAsistencia", "fechaAsistencia", "estadoAsistencia", "observacionAsistencia", "idParticipanteMatriculado", "estadoActual"};
        String selection = "estadoSubida = ?";
        String[] selectionArgs = {"0"};

        Cursor cursor = db.query(Atributos.table_asistencia, projection, selection, selectionArgs, null, null, null);

        if (cursor.getCount() == 0) {
            System.out.println("ssssssssssssssssssssssssssssssssssssssssssss");
            return false;
        } else {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            Toast.makeText(this, "Tiene datos que exportar", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Export.class);
            intent.putExtra("donde", "import");
            startActivity(intent);
            return true;
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

        return count * 8;
    }

    public int controlbtn(){
        SQLiteDatabase db = conection.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + Atributos.table_control + " WHERE estado = '0'", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public void img(){
        Drawable drawable1 = getResources().getDrawable(R.drawable.descargar_1);
        Drawable drawable2 = getResources().getDrawable(R.drawable.descargar_2);
            if (estimg == false) {
                estimg = true;
                imgimport.setImageDrawable(drawable1);
            } else {
                estimg = false;
                imgimport.setImageDrawable(drawable2);
            }
    }
}