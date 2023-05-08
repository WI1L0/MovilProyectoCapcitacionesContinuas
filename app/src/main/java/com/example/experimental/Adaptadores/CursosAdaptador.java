package com.example.experimental.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.experimental.Modelos.MCursos;
import com.example.experimental.Progresst_Bar.ManejoProgressBar;
import com.example.experimental.R;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CursosAdaptador extends RecyclerView.Adapter<CursosAdaptador.ViewHolder> {

    private List<MCursos> mData;
    private LayoutInflater mInflater;
    private Context context;
    final CursosAdaptador.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(MCursos item);

        void onItemClickDetalle(int item);
    }

    public CursosAdaptador (List<MCursos> itemList, Context context, CursosAdaptador.OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public CursosAdaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_cursos, null);
        return new CursosAdaptador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CursosAdaptador.ViewHolder holder, final int position){
        holder.bindData(mData.get(position), position);
    }

    public void setItems(List<MCursos> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtnombre, txtduracion, txtmodalidad, txttipo, txtespecialidad, txtarea, txtfinicio, txtffin;

        Button btndetalles;

        ProgressBar pgcursos;

        ImageView imgcurso;

        ViewHolder(View itemView){
            super(itemView);
            txtnombre = itemView.findViewById(R.id.txtvnombrecurso);
            txtduracion = itemView.findViewById(R.id.txtvduracioncurso);
            txtmodalidad = itemView.findViewById(R.id.txtvmodalidadcurso);
            txttipo = itemView.findViewById(R.id.txtvtipocurso);
            txtespecialidad = itemView.findViewById(R.id.txtvespecialidadcurso);
            txtarea = itemView.findViewById(R.id.txtvareacurso);
            txtfinicio = itemView.findViewById(R.id.txtvfechainiciocurso);
            txtffin = itemView.findViewById(R.id.txtvfechafincurso);
            pgcursos = itemView.findViewById(R.id.progressBarCurso);
            imgcurso = itemView.findViewById(R.id.imgcurso);

            btndetalles = itemView.findViewById(R.id.btnDetallesCurso);
        }

        void bindData(final MCursos item, int position){

            txtnombre.setText(item.getNombreCurso());
            txtduracion.setText(String.valueOf(item.getDuracionCurso()));
            txtmodalidad.setText(item.getNombreModalidadCurso());
            txttipo.setText(item.getNombreTipoCurso());
            txtespecialidad.setText(item.getNombreEspecialidad());
            txtarea.setText(item.getNombreArea());
            txtfinicio.setText(String.valueOf(item.getFechaInicioCurso()));
            txtffin.setText(String.valueOf(item.getFechaFinalizacionCurso()));

            //imgcurso.setImageBitmap(ImgBitmap(item.getFotoCurso()));

            ManejoProgressBar manejoProgressBar = new ManejoProgressBar(pgcursos);
            pgcursos.setProgress(0);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    pgcursos.setProgress(porcentajeCurso(item.getFechaFinalizacionCurso(), item.getDuracionCurso()));
                }
            }).start();



            btndetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickDetalle(item.getIdCurso());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public int porcentajeCurso(String date2, int total){
        LocalDate fecha1 = LocalDate.now();
        LocalDate fecha2 = LocalDate.parse(date2);

        System.out.println(fecha1 + "sssssssssssssssssssssssssssssssss");
        System.out.println(fecha2 + "sssssssssssssssssssssssssssssssssdddd");
        long diasFaltantes = ChronoUnit.DAYS.between(fecha2, fecha1);

        int progreso = 0;
        if (diasFaltantes > 0) {
            System.out.println(diasFaltantes + "ddddddddddddddddd");

            progreso = (int) ((diasFaltantes * 100) / total);
            progreso = progreso - 100;
        } else {
            progreso = 100;
        }
        return progreso;
    }

    public Bitmap ImgBitmap(String img){
        byte[] bitmapBytes = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}