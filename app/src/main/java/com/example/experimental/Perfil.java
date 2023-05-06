package com.example.experimental;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.experimental.DB.DataBase;

public class Perfil extends AppCompatActivity {

    //Vista
    TextView txtvrol, txtvcedula, txtvtelefono, txtvcelular, txtvcorreo, txtvetnia, txtvtitulo, txtv5;
    ImageView imgperfil;

    //use database
    private DataBase conection;

    private Cursor cursor;
    private Boolean usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //use database
        conection = new DataBase(getApplicationContext());


        //datos de programas
        int id = (int) getIntent().getSerializableExtra("idPerfil");
        String rol = (String) getIntent().getSerializableExtra("rol");

        System.out.println(id + "esssssssssssss" + rol);


        //vista
        txtvrol = (TextView) findViewById(R.id.txtvroluserperfil);
        txtvcedula = (TextView) findViewById(R.id.txtvcedulaperfil);
        txtvtelefono = (TextView) findViewById(R.id.txtvtelefono);
        txtvcelular = (TextView) findViewById(R.id.txtvcelularperfil);
        txtvcorreo = (TextView) findViewById(R.id.txtvcorreoperfil);
        txtvetnia = (TextView) findViewById(R.id.txtvetniaperfil);
        txtvtitulo = (TextView) findViewById(R.id.txtvtituloperfil);
        txtv5 = (TextView) findViewById(R.id.txtv5);
        imgperfil = (ImageView) findViewById(R.id.imgperfil);

        SQLiteDatabase db = conection.getReadableDatabase();

        if (rol.equals("alumno")){
            cursor = db.rawQuery("SELECT u.username, p.identificacion, p.telefono, p.celular, p.correo, p.etnia, u.nombreRol, u.fotoPerfil FROM personas p INNER JOIN usuarios u ON u.idPersona = p.idPersona WHERE u.idUsuario = ?;",
                    new String[]{String.valueOf(id)});
            System.out.println(cursor.getCount() + "ddddddddddddddddddddddddddddddddddd1");
            txtv5.setVisibility(View.INVISIBLE);
            txtvtitulo.setVisibility(View.INVISIBLE);
            usu = true;
        } else {
            cursor = db.rawQuery("SELECT u.username, p.identificacion, p.telefono, p.celular, p.correo, p.etnia, u.nombreRol, u.fotoPerfil, c.tituloCapacitador FROM personas p INNER JOIN usuarios u ON u.idPersona = p.idPersona INNER JOIN capacitador c ON c.idUsuario = u.idUsuario WHERE c.idCapacitador = ?;",
                    new String[]{String.valueOf(id)});
            System.out.println(cursor.getCount() + "ddddddddddddddddddddddddddddddddddd2");
            txtv5.setVisibility(View.VISIBLE);
            txtvtitulo.setVisibility(View.VISIBLE);
            usu = false;
        }

        while (cursor.moveToNext()) {
            txtvrol.setText(cursor.getString(0) + " : " + cursor.getString(6));
            txtvcedula.setText(cursor.getString(1));
            txtvtelefono.setText(cursor.getString(2));
            txtvcelular.setText(cursor.getString(3));
            txtvcorreo.setText(cursor.getString(4));
            txtvetnia.setText(cursor.getString(5));

            if (usu == false) {
                txtvtitulo.setText(cursor.getString(8));
            }
            imgperfil.setImageBitmap(ImgBitmap(cursor.getString(7)));
        }
    }

    public Bitmap ImgBitmap(String img){
        byte[] bitmapBytes = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}