package com.example.soes.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soes.R;
import com.example.soes.inicializacion.usuario_soporte;

import java.util.List;

public class admin_soporte extends RecyclerView.Adapter<admin_soporte.ViewHolder> {

    private List<String> idList;
    private  List<String> companiaList;
    private  List<String> telefonoList;
    private  List<String> imageList;
    private usuario_soporte.OnItemClickListener listener;

    public admin_soporte(List<String> idList, List<String> companiaList, List<String> telefonoList, List<String> imageList) {
        {
            this.idList = idList; this.companiaList = companiaList; this.telefonoList = telefonoList;
            this.imageList = imageList;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_soporte,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_soporte.ViewHolder holder, int position) {
        holder.id.setText(idList.get(position));
        holder.compania.setText(companiaList.get(position));
        holder.tel.setText(telefonoList.get(position));
        holder.imagen.setImageResource(Integer.parseInt(imageList.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    int pos = holder.getAdapterPosition();
                    listener.onItemClick(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(usuario_soporte.OnItemClickListener listener) {
        this.listener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id, compania, tel;
        public ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.txt_id);
            compania = itemView.findViewById(R.id.txt_compania);
            tel = itemView.findViewById(R.id.txt_tubos);
            imagen = itemView.findViewById(R.id.imageView_icon0);
        }
    }
}