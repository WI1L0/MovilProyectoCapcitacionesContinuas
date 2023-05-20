package com.example.complexivo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.complexivo.DB.DataBase;
import com.example.complexivo.DB.DataBaseTemporal;
import com.example.complexivo.DB.ImportData;
import com.example.complexivo.Utilidades.Atributos;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;



//Another importy dialog


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity{

    //use database
    private DataBase conection1;
    private DataBaseTemporal conection2;


    //vista
    private Button btninicio, btndatos, btnbase, btnparticipantee, btncapacitador;
    private EditText edtusername, edtpassword;

    private int id_usu;


    //Another variabe
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView3);

        ObjectAnimator jumpAnimator = ObjectAnimator.ofFloat(imageView, "translationY", 0, 100, -100, 0);
        jumpAnimator.setDuration(2000);
        jumpAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        jumpAnimator.setRepeatCount(ValueAnimator.INFINITE);
        jumpAnimator.start();

        //use database
        conection1 = new DataBase(getApplicationContext());
        conection2 = new DataBaseTemporal(getApplicationContext());


        //crear database
        SQLiteDatabase sdb = (new DataBase(MainActivity.this).getWritableDatabase());
        SQLiteDatabase sdb2 = (new DataBaseTemporal(MainActivity.this).getWritableDatabase());
        if (sdb != null){
            //Toast.makeText(this, "BASE DE DATOS CREADA", Toast.LENGTH_SHORT).show();
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
                //pasa();  solo le comento este metodo..
                validaDatos();
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

    public void validaDatos(){
        try {
            if(edtusername.getText().toString().isEmpty() || edtpassword.getText().toString().isEmpty()){
               this.validatorGeneric("CAMPOS VACIOS", "Debe ingresar usuario y contraseña.", 1);
            }else{
                pasa();
            }
        }catch (Exception e){
            System.out.println("The error-> "+e.getMessage());
            pasa();
        }


    }

    public void validatorGeneric(String title, String message, int id){
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title); // set Title
        builder.setMessage(message);  // set message
        builder.setCancelable(true); //  Sets whether the dialog is cancelable or not
        if(id == 1){
            builder.setIcon(R.drawable.err);
        }else{
            builder.setIcon(R.drawable.notfound);
        }

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.cancel();

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void pasa() {
   
        SQLiteDatabase db = conection1.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("SELECT u.idUsuario, r.nombreRol, u.password FROM usuarios u INNER JOIN rolusuario ru ON ru.idUsuario = u.idUsuario INNER JOIN roles r ON r.idRol = ru.idRol " +
                        "WHERE u.username = ? AND u.estadoUsuarioActivo = '1' AND r.estadoRolActivo = '1';",
                new String[]{edtusername.getText().toString()});


            if (cursor1.moveToFirst()) {

                Boolean vrf = verificar(cursor1.getString(2));

                if (vrf == true) {

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
                    Toast.makeText(this, "Pass incorrecto", Toast.LENGTH_SHORT).show();
                }
            } else {
                System.out.println("wwwwwwwwwwwwwwww10");
                try {
                    this.validatorGeneric("CREDENCIALES ERRONEAS", "Verifique su usuario y contraseña.",2);

                }catch (Exception e){
                    Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();

                }
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
                    toastGreen("Bienvenido Capacitador");
//                    Toast.makeText(this, "Bienvenido Capacitador", Toast.LENGTH_SHORT).show();

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

            toastGreen("Bienvenido Alumno");
            //Toast.makeText(this, "Bienvenido Alumno", Toast.LENGTH_SHORT).show();

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

    public void toastGreen(String msg){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.toast_custom_ok));
        TextView camposmsg = view.findViewById(R.id.txtmensajetoas);
        camposmsg.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


    private Boolean verificar(String pass){

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String pc = encoder.encode(edtpassword.getText().toString());
        System.out.println(pc + " ssssssssssssssssssssiuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        System.out.println(pass + " siuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        System.out.println(edtpassword.getText().toString() + " siuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        if (BCrypt.checkpw(edtpassword.getText().toString(), pass)) {
            System.out.println(" true --------------------------------------------------------");
            return true;
        } else {
            System.out.println("false ------------------------------------------------------");
            return false;
        }
    }

//    private String encriptar(String pass){
//
//        String passEncriptadaString;
//
//        try{
//            SecretKeySpec secretKeySpec = generateKey(pass);
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
//            byte[] passEncriptadaBits = cipher.doFinal(pass.getBytes());
//            passEncriptadaString = Base64.encodeToString(passEncriptadaBits, Base64.DEFAULT);
//
//        } catch ( BadPaddingException e) {
//            System.out.println("encriptar error 1  +++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            throw new RuntimeException(e);
//        } catch ( IllegalBlockSizeException e) {
//            System.out.println("encriptar error 2 +++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            throw new RuntimeException(e);
//        } catch (NoSuchPaddingException e) {
//            System.out.println("encriptar error 3  +++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            throw new RuntimeException(e);
//        } catch (NoSuchAlgorithmException e) {
//            System.out.println("encriptar error 4  +++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            throw new RuntimeException(e);
//        } catch (InvalidKeyException e) {
//            System.out.println("encriptar error 5  +++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            throw new RuntimeException(e);
//        }
//        return passEncriptadaString;
//    }
//
//    private SecretKeySpec generateKey(String pass){
//
//        SecretKeySpec secretKeySpec;
//
//        try{
//            MessageDigest sha = MessageDigest.getInstance("SHA-256");
//            byte[] key = pass.getBytes("UTF-8");
//            key = sha.digest(key);
//            secretKeySpec = new SecretKeySpec(key, "AES");
//        } catch ( NoSuchAlgorithmException e) {
//            System.out.println("encriptar error 1  --------------------------------------------------");
//            throw new RuntimeException(e);
//        } catch (UnsupportedEncodingException e) {
//            System.out.println("encriptar error 2  --------------------------------------------------");
//            throw new RuntimeException(e);
//        }
//        return secretKeySpec;
//    }

}