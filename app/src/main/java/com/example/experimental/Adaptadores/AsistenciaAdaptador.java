package com.example.experimental.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.experimental.Asistencia;
import com.example.experimental.Modelos.MAsistencia;
import com.example.experimental.Modelos.MInscritos;
import com.example.experimental.Modelos.MParticipante;
import com.example.experimental.R;

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
        Button btnasiste, btnnoasiste, btnobservacione;
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
                } else {
                    btnnoasiste.setEnabled(false);
                }
            } else {
                btnasiste.setEnabled(true);
                btnnoasiste.setEnabled(true);
            }

            btnasiste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "ASISTE " + item.getIdPersona() + " id " + item.getNombre1() + ", " + item.getNombre2() + " nombres " + item.getApellido1() + ", " + item.getApellido2()  + " apellido", Toast.LENGTH_SHORT).show();

                    String contenido = edtobservaciones.getText().toString();
                    mAsistencia = new MAsistencia();
                    mParticipante = new MParticipante();

                    mParticipante.setIdParticipanteMatriculado(item.getIdParticipanteMatriculado());

                    mAsistencia.setEstadoAsistencia(true);
                    System.out.println(contenido);
                    mAsistencia.setObservacionAsistencia(contenido);
                    mAsistencia.setmParticipante(mParticipante);

                    if (item.getmAsistenciaList() != null && !item.getmAsistenciaList().isEmpty()) {
                        mAsistencia.setIdAsistencia(item.getmAsistenciaList().get(0).getIdAsistencia());
                        mAsistencia.setFechaAsistencia(item.getmAsistenciaList().get(0).getFechaAsistencia());
                    }

                    listener.obtenList(mAsistencia);

                    btnasiste.setEnabled(false);
                    btnnoasiste.setEnabled(true);
                    btnobservacione.setEnabled(false);
                }
            });

            btnnoasiste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "NO ASISTE " + item.getIdPersona() + " id " + item.getNombre1() + ", " + item.getNombre2() + " nombres " + item.getApellido1() + ", " + item.getApellido2()  + " apellido", Toast.LENGTH_SHORT).show();

                    String contenido = edtobservaciones.getText().toString();
                    mAsistencia = new MAsistencia();
                    mParticipante = new MParticipante();

                    mParticipante.setIdParticipanteMatriculado(item.getIdParticipanteMatriculado());

                    mAsistencia.setEstadoAsistencia(false);
                    System.out.println(contenido);
                    mAsistencia.setObservacionAsistencia(contenido);
                    mAsistencia.setmParticipante(mParticipante);

                    if (item.getmAsistenciaList() != null && !item.getmAsistenciaList().isEmpty()) {
                        mAsistencia.setIdAsistencia(item.getmAsistenciaList().get(0).getIdAsistencia());
                        mAsistencia.setFechaAsistencia(item.getmAsistenciaList().get(0).getFechaAsistencia());
                    }

                    listener.obtenList(mAsistencia);

                    btnnoasiste.setEnabled(false);
                    btnasiste.setEnabled(true);
                    btnobservacione.setEnabled(false);
                }
            });

            btnobservacione.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edtobservaciones.getVisibility() == View.VISIBLE) {
                        edtobservaciones.setVisibility(View.INVISIBLE);
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
