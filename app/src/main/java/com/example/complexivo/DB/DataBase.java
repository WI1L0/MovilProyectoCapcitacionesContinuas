package com.example.complexivo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.complexivo.Utilidades.Atributos;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context) {
        super(context, "db_final", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Atributos.CREAR_TABLA_CONTROL);
        db.execSQL(Atributos.CREAR_TABLA_PERSONAS);
        db.execSQL(Atributos.CREAR_TABLA_PROGRAMAS);
        db.execSQL(Atributos.CREAR_TABLA_ROLES);
        db.execSQL(Atributos.CREAR_TABLA_USUARIOS);
        db.execSQL(Atributos.CREAR_TABLA_ROLES_USUARIOS);
        db.execSQL(Atributos.CREAR_TABLA_CAPACITADOR);
        db.execSQL(Atributos.CREAR_TABLA_CURSOS);
        db.execSQL(Atributos.CREAR_TABLA_PREREQUISITOS);
        db.execSQL(Atributos.CREAR_TABLA_INSCRITOS);
        db.execSQL(Atributos.CREAR_TABLA_PARTICIPANTES);
        db.execSQL(Atributos.CREAR_TABLA_ASISTENCIAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {

        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_asistencia);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_participante);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_inscritos);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_prerequisitos);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_cursos);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_capacitador);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_rol_usu);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_usuarios);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_rol);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_programas);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_persona);
        db.execSQL("DROP TABLE IF EXISTS " + Atributos.table_control);
        onCreate(db);
    }

    public void insercontrol(){


        SQLiteDatabase db = getWritableDatabase();
        try {

            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_persona + "', " + false + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_programas + "', " +false + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_rol + "', " + false + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_rol_usu + "', " + false + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_usuarios + "', " + false + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_capacitador + "', " + false + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_cursos + "', " + false + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_prerequisitos + "', " + false + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_inscritos + "', " + false + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_participante + "', " + false + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_asistencia + "', " + false + ")");

        }catch (Exception e){
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_persona + "', " + 0 + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_programas + "', " +0 + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_rol + "', " + 0 + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_rol_usu + "', " + 0 + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_usuarios + "', " + 0 + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_capacitador + "', " + 0 + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_cursos + "', " + 0 + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_prerequisitos + "', " + 0 + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_inscritos + "', " + 0 + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_participante + "', " + 0 + ")");
            db.execSQL("INSERT INTO " + Atributos.table_control + " VALUES ('" + Atributos.table_asistencia + "', " + 0 + ")");

            System.out.println("La eee-> "+e.getMessage());
            System.out.println("La eee-> "+e.getCause());
        }
    }
}
