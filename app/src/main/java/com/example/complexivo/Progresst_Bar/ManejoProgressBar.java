package com.example.complexivo.Progresst_Bar;

import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ManejoProgressBar extends AsyncTask<Void, Integer, Void> {

    private ProgressBar pgsimport;
    private int progreso;
    //private Context context;

    public ManejoProgressBar(ProgressBar pgsimport) {
        this.pgsimport = pgsimport;
    }

    @Override
    protected Void doInBackground(Void... voids) {
            publishProgress(progreso);
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                System.out.println( "onCreate: +++++++++++++++++++++++++++++++++++++++++");
            }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // Actualiza el progreso de la ProgressBar
        progreso = values[0];
        pgsimport.setProgress(progreso);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // La tarea se ha completado
        //Toast.makeText(, "Los Datos han sido cargados exitosamente", Toast.LENGTH_SHORT).show();
    }

    public int getProgreso() {
        return progreso;
    }
}