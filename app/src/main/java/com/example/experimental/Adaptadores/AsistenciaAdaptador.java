package com.example.experimental.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.experimental.Modelos.MAsistencia;
import com.example.experimental.Modelos.MInscritos;
import com.example.experimental.R;

import java.util.List;

public class AsistenciaAdaptador extends RecyclerView.Adapter<AsistenciaAdaptador.ViewHolder> {

    MAsistencia mAsistencia;
    MInscritos mInscritos;

    private List<MInscritos> mData;
    private LayoutInflater mInflater;
    private Context context;
    final AsistenciaAdaptador.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(MInscritos item);

        void obtenList(MAsistencia item);
    }

    public AsistenciaAdaptador(List<MInscritos> itemList, Context context, AsistenciaAdaptador.OnItemClickListener listener){
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

    public void setItems(List<MInscritos> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtnombres, txtapellidos;
        Button btnasiste, btnnoasiste, btnobservacione;
        EditText edtobservaciones;

        ViewHolder(View itemView){
            super(itemView);
            txtnombres = itemView.findViewById(R.id.txtvnombresasistencia);
            txtapellidos = itemView.findViewById(R.id.txtvapellidosasistencia);
            btnasiste = itemView.findViewById(R.id.btnasisteasistencia);
            btnnoasiste = itemView.findViewById(R.id.btnnoasisteasistencia);
            btnobservacione = itemView.findViewById(R.id.btnobservacionesasistencia);
            edtobservaciones = itemView.findViewById(R.id.editTextobservaciones);
        }

        void bindData(final MInscritos item, int position){

            txtnombres.setText(item.getmUsuario().getmPersona().getNombre1() + ", " + item.getmUsuario().getmPersona().getNombre2());
            txtapellidos.setText(item.getmUsuario().getmPersona().getApellido1() + ", " + item.getmUsuario().getmPersona().getApellido2());

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

                    mAsistencia = new MAsistencia();
                    mInscritos = new MInscritos();

                    mInscritos.setIdInscrito(item.getIdInscrito());

                    mAsistencia.setEstadoAsistencia(true);
                    String observ = edtobservaciones.getText().toString();
                    mAsistencia.setObservacionAsistencia(observ);
                    mAsistencia.setmInscritos(mInscritos);

                    if (item.getmAsistenciaList() != null && !item.getmAsistenciaList().isEmpty()) {
                        mAsistencia.setIdAsistencia(item.getmAsistenciaList().get(0).getIdAsistencia());
                        mAsistencia.setFechaAsistencia(item.getmAsistenciaList().get(0).getFechaAsistencia());
                    }

                    listener.obtenList(mAsistencia);

                    btnasiste.setEnabled(false);
                    btnnoasiste.setEnabled(true);
                }
            });

            btnnoasiste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "NO ASISTE " + item.getIdPersona() + " id " + item.getNombre1() + ", " + item.getNombre2() + " nombres " + item.getApellido1() + ", " + item.getApellido2()  + " apellido", Toast.LENGTH_SHORT).show();

                    mAsistencia = new MAsistencia();
                    mInscritos = new MInscritos();

                    mInscritos.setIdInscrito(item.getIdInscrito());

                    mAsistencia.setEstadoAsistencia(false);
                    String observ = edtobservaciones.getText().toString();
                    mAsistencia.setObservacionAsistencia(observ);
                    mAsistencia.setmInscritos(mInscritos);

                    if (item.getmAsistenciaList() != null && !item.getmAsistenciaList().isEmpty()) {
                        mAsistencia.setIdAsistencia(item.getmAsistenciaList().get(0).getIdAsistencia());
                        mAsistencia.setFechaAsistencia(item.getmAsistenciaList().get(0).getFechaAsistencia());
                    }

                    listener.obtenList(mAsistencia);

                    btnnoasiste.setEnabled(false);
                    btnasiste.setEnabled(true);
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
}
