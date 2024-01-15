package com.example.soes.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.soes.R;
import com.example.soes.inicializacion.usuario_acercaNosotros;
import com.example.soes.inicializacion.usuario_login;
import com.example.soes.inicializacion.usuario_perfil;


public class admin_fragmentConfiguracion extends Fragment {
    Intent intent;
    Button perfil, adminUsuarios, adminSoporte, nosotros, cerrarSesion, solicitudes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin_configuracion, container, false);

        perfil = v.findViewById(R.id.miperfil);
        adminUsuarios = v.findViewById(R.id.adminUsuarios);
        adminSoporte = v.findViewById(R.id.soporteTecnico);
        nosotros = v.findViewById(R.id.acercaNosotros);
        cerrarSesion = v.findViewById(R.id.cerrarSesion);
        solicitudes = v.findViewById(R.id.ActualizacionInformacion);

        solicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verSolicitudes();
            }
        });
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verPerfil();
            }
        });
        adminUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                administrarUsuarios();
            }
        });
        adminSoporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                administrarSoporte();
            }
        });
        nosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acercaNosotros();
            }
        });
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cerrarSesion();
            }
        });

        return v;
    }

    private void verSolicitudes() {
        Intent intent = new Intent(getActivity(), admin_solicitudes.class);
        startActivity(intent);
    }

    private void dialog_cerrarSesion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Seguro que quieres cerrar la sesión?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(getActivity(), usuario_login.class);
                startActivity(intent);
                getActivity().finish();  // Cierra la actividad actual

                Toast.makeText(getActivity(), "Sesion cerrada", Toast.LENGTH_SHORT).show();
            }

        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // El usuario hizo clic en "No", no hacer nada
            }
        });
        builder.show();
    }

    private void acercaNosotros() {
        Intent intent = new Intent(getActivity(), usuario_acercaNosotros.class);
        startActivity(intent);
    }

    private void administrarSoporte() {
        Intent intent = new Intent(getActivity(), admin_editarSoporte.class);
        startActivity(intent);
    }

    private void administrarUsuarios() {
        Intent intent = new Intent(getActivity(), admin_editarUsuarios.class);
        startActivity(intent);
    }

    private void verPerfil() {
        Intent intent = new Intent(getActivity(), admin_perfil.class);
        startActivity(intent);
    }
}