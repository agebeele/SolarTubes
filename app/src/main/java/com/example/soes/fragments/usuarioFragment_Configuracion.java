package com.example.soes.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.soes.R;
import com.example.soes.inicializacion.usuario_editarPerfil;
import com.example.soes.inicializacion.usuario_login;
import com.example.soes.inicializacion.usuario_acercaNosotros;
import com.example.soes.inicializacion.usuario_perfil;

public class usuarioFragment_Configuracion extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    Button editarPerfil, ubicacion, nosotros, exit;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_usuario__configuracion, container, false);

        editarPerfil = v.findViewById(R.id.miperfil);
        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarInformacion();
            }
        });
        ubicacion = v.findViewById(R.id.aceptarUbicacion);
        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revisarPermiso();
            }
        });
        nosotros = v.findViewById(R.id.acercaNosotros);
        nosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                informacionNosotros();
            }
        });
        exit = v.findViewById(R.id.cerrarSesion);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        return v;
    }

    private void cerrarSesion() {
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

    private void informacionNosotros() {
        Intent pantallaNosotros = new Intent(getActivity(), usuario_acercaNosotros.class);
        startActivity(pantallaNosotros);
    }

    private void revisarPermiso() {
        // Verificar si el permiso ya está concedido
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Si el permiso no está concedido, solicitarlo
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);

        } else {
            Intent locationIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(locationIntent);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Verificar si la solicitud de permiso es para ubicación y si se concedió
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, realizar acciones relacionadas con la ubicación
            } else {
                // Permiso denegado, puedes mostrar un mensaje al usuario o realizar otras acciones
            }
        }
    }

    private void actualizarInformacion() {
        Intent intent = new Intent(getActivity(), usuario_perfil.class);
        startActivity(intent);
    }
}