package com.example.experimental;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.experimental.DB.DataBase;

public class Perfil extends AppCompatActivity {

    //Vista
    TextView txtvrol, txtvcedula, txtvtelefono, txtvcelular, txtvcorreo, txtvetnia, txtvtitulo;

    //use database
    private DataBase conection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //use database
        conection = new DataBase(getApplicationContext());


        //datos de programas
        int id = (int) getIntent().getSerializableExtra("idPerfil");


        //vista
        txtvrol = (TextView) findViewById(R.id.txtvroluserperfil);
        txtvcedula = (TextView) findViewById(R.id.txtvcedulaperfil);
        txtvtelefono = (TextView) findViewById(R.id.txtvtelefono);
        txtvcelular = (TextView) findViewById(R.id.txtvcelularperfil);
        txtvcorreo = (TextView) findViewById(R.id.txtvcorreoperfil);
        txtvetnia = (TextView) findViewById(R.id.txtvetniaperfil);
        txtvtitulo = (TextView) findViewById(R.id.txtvtituloperfil);

        SQLiteDatabase db = conection.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT u.username, p.identificacion, p.telefono, p.celular, p.correo, p.etnia, c.tituloCapacitador, u.nombreRol FROM personas p INNER JOIN usuarios u ON u.idPersona = p.idPersona INNER JOIN capacitador c ON c.idUsuario = u.idUsuario WHERE u.idUsuario = ?;",
                new String[]{String.valueOf(id)});

        while (cursor.moveToNext()) {
            txtvrol.setText(cursor.getString(0) + " : " + cursor.getString(7));
            txtvcedula.setText(cursor.getString(1));
            txtvtelefono.setText(cursor.getString(2));
            txtvcelular.setText(cursor.getString(3));
            txtvcorreo.setText(cursor.getString(4));
            txtvetnia.setText(cursor.getString(5));
            txtvtitulo.setText(cursor.getString(6));
        }
    }
}