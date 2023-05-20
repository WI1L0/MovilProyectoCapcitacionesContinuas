package com.example.complexivo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.complexivo.DB.DataBase;
import com.example.complexivo.Modelos.MCursos;
import com.example.complexivo.Modelos.MPrerequisitos;

import java.util.ArrayList;

public class Detalle_Curso extends AppCompatActivity {

    //vista
    private TextView txtnombre, txtduracion, txthorario, txtmodalidad, txttipo, txtespecialidad, txtarea, txtfinicio, txtffin, txtdescripcion, txtobjetivos, txtrequisitos;
    private ImageView imgdtcurso;

    //use database
    private DataBase conection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_curso);

        //use database
        conection = new DataBase(getApplicationContext());


        //datos de programas
        int id = (int) getIntent().getSerializableExtra("idCurso");


        //vista
        txtnombre = (TextView) findViewById(R.id.txtvnombredtcurso);
        txtduracion = (TextView) findViewById(R.id.txtvduraciondtcurso);
        txthorario = (TextView) findViewById(R.id.txtvhorariodtcurso);
        txtmodalidad = (TextView) findViewById(R.id.txtvmodalidaddtcurso);
        txttipo = (TextView) findViewById(R.id.txtvtipodtcurso);
        txtespecialidad = (TextView) findViewById(R.id.txtvespecialidaddtcurso);
        txtarea = (TextView) findViewById(R.id.txtvareadtcurso);
        txtfinicio = (TextView) findViewById(R.id.txtvfechainiciodtcurso);
        txtffin = (TextView) findViewById(R.id.txtvfechafindtcurso);
        txtdescripcion = (TextView) findViewById(R.id.txtvdescripciondtcurso);
        txtobjetivos = (TextView) findViewById(R.id.txtvobjetivosdtcurso);
        txtrequisitos = (TextView) findViewById(R.id.txtvrequisitosdtcurso);
        imgdtcurso = (ImageView) findViewById(R.id.imgdtcurso);


        SQLiteDatabase db = conection.getReadableDatabase();

        String requisitos = null;

        Cursor cursor = db.rawQuery("SELECT nombreCurso, duracionCurso, fechaInicioCurso, fechaFinalizacionCurso, descripcionCurso, objetivoGeneralesCurso,  nombreModalidadCurso, nombreTipoCurso, nombreEspecialidad, nombreArea, horaInicio, horaFin, fotoCurso " +
                        "FROM cursos WHERE idCurso = ? AND estadoCurso = '1' ORDER BY nombreCurso DESC;",
                new String[]{String.valueOf(id)});

        Cursor cursor2 = db.rawQuery("SELECT nombrePrerequisitoCurso FROM prerequisitos WHERE idCurso = ? AND estadoPrerequisitoCurso = '1';",
                new String[]{String.valueOf(id)});

        if (cursor2.moveToFirst()) {
                requisitos += cursor2.getString(0) + ", \n";
        }

        while (cursor.moveToNext()) {
            txtnombre.setText(cursor.getString(0));
            txtduracion.setText(String.valueOf(cursor.getInt(1)));
            txtfinicio.setText(cursor.getString(2));
            txtffin.setText(cursor.getString(3));
            txtdescripcion.setText(cursor.getString(4));
            txtobjetivos.setText(cursor.getString(5));
            txtmodalidad.setText(cursor.getString(6));
            txttipo.setText(cursor.getString(7));
            txtespecialidad.setText(cursor.getString(8));
            txtarea.setText(cursor.getString(9));
            txthorario.setText(cursor.getString(10) + ", " + cursor.getString(11));
            imgdtcurso.setImageBitmap(ImgBitmap(cursor.getString(12)));


        }

        txtrequisitos.setText(requisitos);
    }

    public Bitmap ImgBitmap(String img){
        byte[] bitmapBytes = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}