package com.example.complexivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.complexivo.Adaptadores.ProgramasAdaptador;
import com.example.complexivo.DB.DataBase;
import com.example.complexivo.Modelos.MProgramas;
import com.example.complexivo.Utilidades.Atributos;
import com.google.android.material.navigation.NavigationView;

import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class Programas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Navegation Drawer
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ImageView imgheader;
    private TextView txtvheader;

    MProgramas mProgramas;
    ArrayList<MProgramas> listaProgramas;

    private int idda;
    private Boolean cursosAll = false;
    private String rol;

    //vista
    private RecyclerView recycleViewProgramas;
    private SearchView svprogramas;


    //use database
    private DataBase conection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programas);

        //use database
        conection = new DataBase(getApplicationContext());


        //datos de main
        idda = (int) getIntent().getSerializableExtra("id");
        rol = (String) getIntent().getSerializableExtra("rol");

        //vista
        recycleViewProgramas = (RecyclerView) findViewById(R.id.recicleProgramas);
        svprogramas = (SearchView) findViewById(R.id.svprogramas);

        //Navegation Drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);


        consultarListaProgramas(idda, rol, false);

        svprogramas.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String sa) {
                filder(sa, idda, rol);
                return false;
            }
        });

        //Navegation Drawer
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        navigationView = drawerLayout.findViewById(R.id.navi);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View naheaderview = navigationView.getHeaderView(0);
        imgheader = (ImageView) naheaderview.findViewById(R.id.imgusuariolateral);
        txtvheader = (TextView) naheaderview.findViewById(R.id.txtvusuLateral);

        Menu navigationMenu = navigationView.getMenu();
        MenuItem menuItem1 = navigationMenu.findItem(R.id.activity1);
        MenuItem menuItem2 = navigationMenu.findItem(R.id.activity2);
        MenuItem menuItem3 = navigationMenu.findItem(R.id.activity3);
        MenuItem menuItem4 = navigationMenu.findItem(R.id.activity4);
        MenuItem menuItem5 = navigationMenu.findItem(R.id.activity5);
        MenuItem menuItem6 = navigationMenu.findItem(R.id.activity6);

        if (rol.equals("alumno")){
            menuItem1.setTitle("MIS CURSOS");
            menuItem2.setTitle("CURSOS");
            menuItem3.setTitle("PERFIL");
            menuItem1.setChecked(true);
            menuItem4.setVisible(false);

            SQLiteDatabase db = conection.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT fotoPerfil, username FROM usuarios WHERE idUsuario = ?;",
                    new String[]{String.valueOf(idda)});

            if (cursor.moveToFirst()) {
                imgheader.setImageBitmap(ImgBitmap(cursor.getString(0)));
                txtvheader.setText(cursor.getString(1));
            }

        } else {
            menuItem1.setTitle("ASISTENCIA");
            menuItem3.setTitle("PERFIL");
            menuItem1.setChecked(true);
            menuItem2.setVisible(false);

            SQLiteDatabase db = conection.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT u.fotoPerfil, u.username FROM usuarios u INNER JOIN capacitador c ON c.idUsuario = u.idUsuario WHERE c.idCapacitador = ?;",
                    new String[]{String.valueOf(idda)});

            if (cursor.moveToFirst()) {
                imgheader.setImageBitmap(ImgBitmap(cursor.getString(0)));
                txtvheader.setText(cursor.getString(1));
            }
        }
    }

    //Navegation Drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.activity1: {
                consultarListaProgramas(idda, rol, false);
                cursosAll = false;
                break;
            }
            case R.id.activity2: {
                consultarListaProgramas(idda, rol, true);
               cursosAll = true;
                break;
            }
            case R.id.activity3: {
                Intent intent = new Intent(Programas.this, Perfil.class);
                intent.putExtra("idPerfil", idda);
                intent.putExtra("rol", rol);
                startActivity(intent);
                break;
            }
            case R.id.activity4: {
                Intent galleryIntent = new Intent(Programas.this, Export.class);
                startActivity(galleryIntent);
                break;
            }
            case R.id.activity5: {
                Intent intent = new Intent(Programas.this, Import.class);
                startActivity(intent);
                break;
            }
            case R.id.activity6: {
                Intent intent = new Intent(Programas.this, MainActivity.class);
                startActivity(intent);
                break;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void filder(String sa, int id, String rol){
        ArrayList<MProgramas> filtered = new ArrayList<>();
        for (MProgramas item : listaProgramas){
            if (item.getNombrePrograma().toLowerCase().contains(sa.toLowerCase())) {
                filtered.add(item);
            }
        }

        ProgramasAdaptador programasAdaptador = new ProgramasAdaptador(filtered, this, new ProgramasAdaptador.OnItemClickListener() {
            @Override
            public void onItemClick(MProgramas item) {
                moveToDescription(item, id, rol);
            }
        });

        recycleViewProgramas.setHasFixedSize(true);
        recycleViewProgramas.setLayoutManager(new LinearLayoutManager(this));
        recycleViewProgramas.setAdapter(programasAdaptador);
    }

    private void consultarListaProgramas(int id, String rol, Boolean est) {
        SQLiteDatabase db = conection.getReadableDatabase();

        if (rol.equals("alumno")){
            if (est == false) {

                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                System.out.println(id + "dddddddddddddddddddddddddddddddddddddddddddddddd");
                listaProgramas = new ArrayList<>();

                Cursor cursor = db.rawQuery("SELECT p.idPrograma, p.nombrePrograma, p.nombrePeriodoPrograma, p.fechaInicioPeriodoPrograma, p.fechaFinPeriodoPrograma " +
                                "FROM programas p INNER JOIN cursos c ON c.idPrograma = p.idPrograma INNER JOIN inscritos i ON idUsuario = ? AND i.idCurso = c.idCurso " +
                                "WHERE p.estadoProgramaActivo = '1' AND p.estadoPeriodoPrograma = '1' " +
                                "GROUP BY p.idPrograma ORDER BY p.fechaInicioPeriodoPrograma DESC;",
                        new String[]{String.valueOf(id)});
                System.out.println(cursor.getCount() + "ssssssssssssaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                while (cursor.moveToNext()){
                    mProgramas = new MProgramas();
                    mProgramas.setIdPrograma(cursor.getInt(0));
                    mProgramas.setNombrePrograma(cursor.getString(1));
                    mProgramas.setNombrePeriodoPrograma(cursor.getString(2));
                    mProgramas.setFechaInicioPeriodoPrograma(cursor.getString(3));
                    mProgramas.setFechaFinPeriodoPrograma(cursor.getString(4));

                    listaProgramas.add(mProgramas);
                }
                init(id, rol);

            } else {
                System.out.println("ssssssssssssssssssssssssssssssssssssss");
                listaProgramas = new ArrayList<>();

                String[] projection = { "idPrograma","nombrePrograma", "nombrePeriodoPrograma", "fechaInicioPeriodoPrograma", "fechaFinPeriodoPrograma"};
                String selection = "estadoProgramaActivo = ? AND estadoPeriodoPrograma = ?";
                String[] selectionArgs = { "1", "1" };

                String groupBy = "idPrograma";
                String orderBy = "fechaInicioPeriodoPrograma DESC";

                Cursor cursor = db.query(Atributos.table_programas, projection, selection, selectionArgs, groupBy, null, orderBy );
                System.out.println(cursor.getCount() + "ssssssssssssaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                while (cursor.moveToNext()){
                    mProgramas = new MProgramas();
                    mProgramas.setIdPrograma(cursor.getInt(0));
                    mProgramas.setNombrePrograma(cursor.getString(1));
                    mProgramas.setNombrePeriodoPrograma(cursor.getString(2));
                    mProgramas.setFechaInicioPeriodoPrograma(cursor.getString(3));
                    mProgramas.setFechaFinPeriodoPrograma(cursor.getString(4));

                    listaProgramas.add(mProgramas);
                }
                init(id, rol);
            }
        } else {
            listaProgramas = new ArrayList<>();

            Cursor cursor = db.rawQuery("SELECT p.idPrograma, p.nombrePrograma, p.nombrePeriodoPrograma, p.fechaInicioPeriodoPrograma, p.fechaFinPeriodoPrograma " +
                            "FROM programas p INNER JOIN cursos c ON c.idCapacitador = ? AND c.idPrograma = p.idPrograma " +
                            "WHERE p.estadoProgramaActivo = '1' AND p.estadoPeriodoPrograma = '1' GROUP BY p.idPrograma " +
                            "ORDER BY p.fechaInicioPeriodoPrograma DESC;",
                    new String[]{String.valueOf(id)});

            while (cursor.moveToNext()){
                mProgramas = new MProgramas();
                mProgramas.setIdPrograma(cursor.getInt(0));
                mProgramas.setNombrePrograma(cursor.getString(1));
                mProgramas.setNombrePeriodoPrograma(cursor.getString(2));
                mProgramas.setFechaInicioPeriodoPrograma(cursor.getString(3));
                mProgramas.setFechaFinPeriodoPrograma(cursor.getString(4));

                listaProgramas.add(mProgramas);
            }
            init(id, rol);
        }
    }

    public void init(int id, String rol) {
        ProgramasAdaptador programasAdaptador = new ProgramasAdaptador(listaProgramas, this, new ProgramasAdaptador.OnItemClickListener() {
            @Override
            public void onItemClick(MProgramas item) {
                moveToDescription(item, id, rol);
            }
        });

        recycleViewProgramas.setHasFixedSize(true);
        recycleViewProgramas.setLayoutManager(new LinearLayoutManager(this));
        recycleViewProgramas.setAdapter(programasAdaptador);
    }

    public void moveToDescription(MProgramas item, int id, String rol) {
        Intent intent = new Intent(this, Cursos.class);
        intent.putExtra("idPrograma", item.getIdPrograma());
        intent.putExtra("cursosAll", cursosAll);
        intent.putExtra("id", id);
        intent.putExtra("rol", rol);
        startActivity(intent);
    }

    public Bitmap ImgBitmap(String img){
        byte[] bitmapBytes = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}