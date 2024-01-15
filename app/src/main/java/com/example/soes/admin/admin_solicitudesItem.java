package com.example.soes.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.soes.R;

public class admin_solicitudesItem extends AppCompatActivity {
    TextView hora, fecha, correo, nombre, paterno, materno, telefono, contra, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_solicitudes_item);

        hora = (TextView) findViewById(R.id.item_hora);
        fecha = (TextView) findViewById(R.id.item_fecha);
        correo = (TextView) findViewById(R.id.item_correo);
        nombre = (TextView) findViewById(R.id.item_nombre);
        paterno = (TextView) findViewById(R.id.item_apellidoPaterno);
        materno = (TextView) findViewById(R.id.item_apellidoMaterno);
        contra = (TextView) findViewById(R.id.item_contra);
        telefono = (TextView) findViewById(R.id.item_telefono);
        id = (TextView) findViewById(R.id.item_id);


        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion !=null){
            String info_hora = recibirInformacion.getString("hora");
            String info_fecha = recibirInformacion.getString("fecha");
            String info_correo = recibirInformacion.getString("correo");
            String info_nombre = recibirInformacion.getString("nombre");
            String info_paterno = recibirInformacion.getString("apellido_paterno");
            String info_materno = recibirInformacion.getString("apellido_materno");
            String info_telefono = recibirInformacion.getString("telefono");
            String info_contra = recibirInformacion.getString("contra");
            String info_id = recibirInformacion.getString("id");

            hora.setText(info_hora);
            fecha.setText(info_fecha);
            correo.setText(info_correo);
            nombre.setText(info_nombre);
            paterno.setText(info_paterno);
            materno.setText(info_materno);
            contra.setText(info_contra);
            telefono.setText(info_telefono);
            id.setText(info_id);
        }
    }
}