package com.example.experimental.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.experimental.Modelos.MCursos;
import com.example.experimental.R;

import java.util.List;

public class CursosAdaptador extends RecyclerView.Adapter<CursosAdaptador.ViewHolder> {

    private List<MCursos> mData;
    private LayoutInflater mInflater;
    private Context context;
    final CursosAdaptador.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(MCursos item);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


}