package com.example.complexivo.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.complexivo.Modelos.MProgramas;
import com.example.complexivo.R;

import java.util.List;

public class ProgramasAdaptador extends RecyclerView.Adapter<ProgramasAdaptador.ViewHolder> {

    private List<MProgramas> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ProgramasAdaptador.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(MProgramas item);
    }

    public ProgramasAdaptador (List<MProgramas> itemList, Context context, ProgramasAdaptador.OnItemClickListener listener){
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
    public ProgramasAdaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_programas, null);
        return new ProgramasAdaptador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProgramasAdaptador.ViewHolder holder, final int position){
        holder.bindData(mData.get(position), position);
    }

    public void setItems(List<MProgramas> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtnombre, txtperiodo, txtfecha;

        ViewHolder(View itemView){
            super(itemView);
            txtnombre = itemView.findViewById(R.id.txtvnombreprograma);
            txtperiodo = itemView.findViewById(R.id.txtvperiodoprograma);
            txtfecha = itemView.findViewById(R.id.txtvfechaprograma);
        }

        void bindData(final MProgramas item, int position){

            txtnombre.setText(item.getNombrePrograma());
            txtperiodo.setText(item.getNombrePeriodoPrograma());
            txtfecha.setText(item.getFechaInicioPeriodoPrograma() + "  a  " + item.getFechaFinPeriodoPrograma());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


}
