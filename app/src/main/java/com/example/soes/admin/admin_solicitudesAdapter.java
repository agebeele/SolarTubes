package com.example.soes.admin;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.soes.R;

import java.util.List;

public class admin_solicitudesAdapter extends RecyclerView.Adapter<admin_solicitudesAdapter.admin_solicitudesAdapterViewHolder> {
    private List<String> nombreList;
    private List<String> maternoList;
    private List<String> paternoList;
    private List<String> fechaList;
    private SharedPreferences preferenciaSolicitudes;
    private OnItemClickListener listener;
    public admin_solicitudesAdapter(List<String> nombreList, List<String> paternoList, List<String> maternoList, List<String> fechaList, Context context) {
        this.nombreList = nombreList;
        this.paternoList = paternoList;
        this.maternoList = maternoList;
        this.fechaList = fechaList;
        preferenciaSolicitudes = context.getSharedPreferences("MyPreferencia_Solicitudes", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public admin_solicitudesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_solicitudes_adapter, parent, false);
        return new admin_solicitudesAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_solicitudesAdapterViewHolder holder, int position) {
        holder.nombre.setText(nombreList.get(position));
        holder.paterno.setText(paternoList.get(position));
        holder.materno.setText(maternoList.get(position));
        holder.fecha.setText(fechaList.get(position));

        // Configurar el estado del CheckBox
        boolean tramiteRealizado = preferenciaSolicitudes.getBoolean("tramiteRealizado_" + position, false);
        holder.realizado.setChecked(tramiteRealizado);

        // Guardar el estado del CheckBox en SharedPreferences cuando cambia
        holder.realizado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferenciaSolicitudes.edit();
            editor.putBoolean("tramiteRealizado_" + position, isChecked);
            editor.apply();
        });
    }

    @Override
    public int getItemCount() {
        return nombreList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class admin_solicitudesAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, paterno, materno, fecha;
        CheckBox realizado;

        public admin_solicitudesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreAdapter);
            paterno = itemView.findViewById(R.id.paternoAdapter);
            materno = itemView.findViewById(R.id.maternoAdapter);
            fecha = itemView.findViewById(R.id.fechaAdapter);
            realizado = itemView.findViewById(R.id.checkBoxTramiteRealizado);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}