package com.example.complexivo;

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
import com.bumptech.glide.Glide;


import com.example.complexivo.DB.DataBase;

public class Perfil extends AppCompatActivity {

    //Vista
    TextView txtvrol, txtvcedula, txtvtelefono, txtvcelular, txtvcorreo, txtvnombre; //, txtvetnia, txtvtitulo, txtv5;
    ImageView imgperfil;

    //use database
    private DataBase conection;

    private Cursor cursor;
    private Boolean usu;
    private String rolfinal;

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
        txtvnombre = (TextView) findViewById(R.id.txtnombreperfil);
//        txtvetnia = (TextView) findViewById(R.id.txtvetniaperfil);
//        txtvtitulo = (TextView) findViewById(R.id.txtvtituloperfil);
//        txtv5 = (TextView) findViewById(R.id.txtv5);
        imgperfil = (ImageView) findViewById(R.id.imgperfil);

        SQLiteDatabase db = conection.getReadableDatabase();

        if (rol.equals("alumno")){
            cursor = db.rawQuery("SELECT u.username,p.nombre1, p.nombre2,p.apellido1, p.apellido2,  p.identificacion, p.telefono, p.celular, p.correo, p.etnia, u.fotoPerfil FROM personas p INNER JOIN usuarios u ON u.idPersona = p.idPersona WHERE u.idUsuario = ?;",
                    new String[]{String.valueOf(id)});
            System.out.println(cursor.getCount() + "ddddddddddddddddddddddddddddddddddd1");
            //txtv5.setVisibility(View.INVISIBLE);
//            txtvtitulo.setVisibility(View.INVISIBLE);
            usu = true;
            rolfinal = "Participante";
        } else {
            cursor = db.rawQuery("SELECT u.username, p.nombre1, p.nombre2,p.apellido1, p.apellido2, p.identificacion, p.telefono, p.celular, p.correo, p.etnia, u.fotoPerfil, c.tituloCapacitador FROM personas p INNER JOIN usuarios u ON u.idPersona = p.idPersona INNER JOIN capacitador c ON c.idUsuario = u.idUsuario WHERE c.idCapacitador = ?;",
                    new String[]{String.valueOf(id)});
            System.out.println(cursor.getCount() + "ddddddddddddddddddddddddddddddddddd2");
            //txtv5.setVisibility(View.VISIBLE);
            //txtvtitulo.setVisibility(View.VISIBLE);
            usu = false;
            rolfinal = "DocenteCapacitador";
        }

        while (cursor.moveToNext()) {
            txtvrol.setText(rolfinal);
            txtvnombre.setText(cursor.getString(1) + ' ' + cursor.getString(2) + ' ' + cursor.getString(3) + ' ' + cursor.getString(4));
            txtvcedula.setText(cursor.getString(5));
            txtvtelefono.setText(cursor.getString(6));
            txtvcelular.setText(cursor.getString(7));
            txtvcorreo.setText(cursor.getString(8));
            //txtvetnia.setText(cursor.getString(5));

            if (usu == false) {
                //txtvtitulo.setText(cursor.getString(7));
            }
            //imgperfil.setImageBitmap(ImgBitmap(cursor.getString(6)));
            Glide.with(this)
                    .load(ImgBitmap(cursor.getString(10)))
                    .circleCrop()
                    .into(imgperfil);
        }
    }

    public Bitmap ImgBitmap(String img){
        byte[] bitmapBytes = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}