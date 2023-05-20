package com.example.complexivo.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.complexivo.Asistencia;
import com.example.complexivo.Modelos.MAsistencia;
import com.example.complexivo.Modelos.MInscritos;
import com.example.complexivo.Modelos.MParticipante;
import com.example.complexivo.R;

import java.util.List;

public class AsistenciaAdaptador extends RecyclerView.Adapter<AsistenciaAdaptador.ViewHolder> {

    MAsistencia mAsistencia;
    MParticipante mParticipante;

    private List<MParticipante> mData;
    private LayoutInflater mInflater;
    private Context context;
    final AsistenciaAdaptador.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(MParticipante item);

        void obtenList(MAsistencia item);
    }

    public AsistenciaAdaptador(List<MParticipante> itemList, Context context, AsistenciaAdaptador.OnItemClickListener listener){
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
    public AsistenciaAdaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_asistencia, null);
        return new AsistenciaAdaptador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AsistenciaAdaptador.ViewHolder holder, final int position){
        holder.bindData(mData.get(position), position);
    }

    public void setItems(List<MParticipante> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtnombres, txtapellidos;
        Button  btnobservacione,btnasiste, btnnoasiste;
        EditText edtobservaciones;
        ImageView imgasistencia;

        ViewHolder(View itemView){
            super(itemView);
            txtnombres = itemView.findViewById(R.id.txtvnombresasistencia);
            txtapellidos = itemView.findViewById(R.id.txtvapellidosasistencia);
            btnasiste = itemView.findViewById(R.id.btnasisteasistencia);
            btnnoasiste = itemView.findViewById(R.id.btnnoasisteasistencia);
            btnobservacione = itemView.findViewById(R.id.btnobservacionesasistencia);
            edtobservaciones = itemView.findViewById(R.id.editTextobservaciones);
            imgasistencia = itemView.findViewById(R.id.imgasistencia);
        }

        void bindData(final MParticipante item, int position){

            txtnombres.setText(item.getmInscritos().getmUsuario().getmPersona().getNombre1() + ", " + item.getmInscritos().getmUsuario().getmPersona().getNombre2());
            txtapellidos.setText(item.getmInscritos().getmUsuario().getmPersona().getApellido1() + ", " + item.getmInscritos().getmUsuario().getmPersona().getApellido2());

            imgasistencia.setImageBitmap(ImgBitmap(item.getmInscritos().getmUsuario().getFotoPerfil()));

            if (item.getmAsistenciaList() != null && !item.getmAsistenciaList().isEmpty()) {
                edtobservaciones.setText(item.getmAsistenciaList().get(0).getObservacionAsistencia());
                if (item.getmAsistenciaList().get(0).getEstadoAsistencia() == true) {
                    btnasiste.setEnabled(false);
                    btnasiste.setBackgroundColor(Color.GREEN);
                    btnnoasiste.setEnabled(true);
                    btnnoasiste.setBackgroundColor(Color.GRAY);
                    btnobservacione.setEnabled(true);
                    btnobservacione.setBackgroundColor(Color.parseColor("#fcc700"));
                } else {
                    btnnoasiste.setEnabled(false);
                    btnnoasiste.setBackgroundColor(Color.RED);
                    btnasiste.setEnabled(true);
                    btnasiste.setBackgroundColor(Color.GRAY);
                    btnobservacione.setEnabled(true);
                    btnobservacione.setBackgroundColor(Color.parseColor("#fcc700"));
                }
            } else {
                btnasiste.setEnabled(true);
                btnnoasiste.setEnabled(true);
            }

            btnasiste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "ASISTE " + item.getIdPersona() + " id " + item.getNombre1() + ", " + item.getNombre2() + " nombres " + item.getApellido1() + ", " + item.getApellido2()  + " apellido", Toast.LENGTH_SHORT).show();

                    String contenido1 = edtobservaciones.getText().toString();
                    mAsistencia = new MAsistencia();
                    mParticipante = new MParticipante();

                    mParticipante.setIdParticipanteMatriculado(item.getIdParticipanteMatriculado());

                    mAsistencia.setEstadoAsistencia(true);
                    System.out.println(contenido1);
                    mAsistencia.setObservacionAsistencia(contenido1);
                    mAsistencia.setmParticipante(mParticipante);

                    if (item.getmAsistenciaList() != null && !item.getmAsistenciaList().isEmpty()) {
                        mAsistencia.setIdAsistencia(item.getmAsistenciaList().get(0).getIdAsistencia());
                        mAsistencia.setFechaAsistencia(item.getmAsistenciaList().get(0).getFechaAsistencia());
                        mAsistencia.setEstadoActual(item.getmAsistenciaList().get(0).getEstadoActual());
                    }

                    listener.obtenList(mAsistencia);

                    btnasiste.setEnabled(false);
                    btnasiste.setBackgroundColor(Color.GREEN);
                    btnnoasiste.setEnabled(true);
                    btnnoasiste.setBackgroundColor(Color.GRAY);
                    btnobservacione.setEnabled(false);
                    btnobservacione.setBackgroundColor(Color.GRAY);
                }
            });

            btnnoasiste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "NO ASISTE " + item.getIdPersona() + " id " + item.getNombre1() + ", " + item.getNombre2() + " nombres " + item.getApellido1() + ", " + item.getApellido2()  + " apellido", Toast.LENGTH_SHORT).show();

                    String contenido2 = edtobservaciones.getText().toString();
                    mAsistencia = new MAsistencia();
                    mParticipante = new MParticipante();

                    mParticipante.setIdParticipanteMatriculado(item.getIdParticipanteMatriculado());

                    mAsistencia.setEstadoAsistencia(false);
                    System.out.println(contenido2);
                    mAsistencia.setObservacionAsistencia(contenido2);
                    mAsistencia.setmParticipante(mParticipante);

                    if (item.getmAsistenciaList() != null && !item.getmAsistenciaList().isEmpty()) {
                        mAsistencia.setIdAsistencia(item.getmAsistenciaList().get(0).getIdAsistencia());
                        mAsistencia.setFechaAsistencia(item.getmAsistenciaList().get(0).getFechaAsistencia());
                        mAsistencia.setEstadoActual(item.getmAsistenciaList().get(0).getEstadoActual());
                    }

                    listener.obtenList(mAsistencia);

                    btnnoasiste.setEnabled(false);
                    btnnoasiste.setBackgroundColor(Color.RED);
                    btnasiste.setEnabled(true);
                    btnasiste.setBackgroundColor(Color.GRAY);
                    btnobservacione.setEnabled(false);
                    btnobservacione.setBackgroundColor(Color.GRAY);
                }
            });

            btnobservacione.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edtobservaciones.getVisibility() == View.VISIBLE) {
                        edtobservaciones.setVisibility(View.GONE);
                    } else {
                        edtobservaciones.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    public Bitmap ImgBitmap(String img){
        byte[] bitmapBytes = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}
