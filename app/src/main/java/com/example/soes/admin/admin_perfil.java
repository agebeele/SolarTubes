package com.example.soes.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soes.R;
import com.example.soes.WebService.WebService;
import com.example.soes.inicializacion.usuario_editarPerfil;
import com.example.soes.inicializacion.usuario_perfil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class admin_perfil extends AppCompatActivity {
    TextView telefono, nombre, nombre1, paterno, materno, correoU, contra;
    WebService obj = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_perfil);

        telefono = findViewById(R.id.telefonoPerfil);
        nombre1 = findViewById(R.id.nombrePerfil);
        nombre = findViewById(R.id.nombrePerfilDG);
        paterno = findViewById(R.id.paternoPerfil);
        materno = findViewById(R.id.maternoPerfil);
        correoU = findViewById(R.id.correoPerfil);
        contra = findViewById(R.id.contraseñaPerfil);

        // Recuperar la matrícula (nombre de usuario) guardada en SharedPreferences
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String correo = preferences.getString("correo", "");

        // Luego, utiliza el correo para obtener los demás datos del usuario
        if (!correo.isEmpty()) {
            // Ejecutar AsyncTask para obtener los datos del usuario
            MiAsyncTask miAsyncTask = new MiAsyncTask();
            miAsyncTask.execute("datosAdmin", correo);
        }
    }
    private class MiAsyncTask extends AsyncTask<String, String, String> {
        private SharedPreferences preferences;

        @Override
        protected String doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "datosAdmin":
                    // Utilizar el nombre de usuario obtenido de SharedPreferences
                    msj = obj.datosAdmin(parameter[1]);
                    publishProgress(msj);
                    break;
                default:
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Recuperar las SharedPreferences en onPreExecute
            preferences = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            try {
                JSONObject jsonObject = new JSONObject(progress[0]);
                boolean success = jsonObject.getBoolean("success");

                if (success) {
                    JSONArray usuario = jsonObject.getJSONArray("data");
                    JSONObject json_data = usuario.getJSONObject(0); // Obtén el primer objeto del JSONArray

                    // Asignar los datos del usuario a los TextViews
                    telefono.setText(json_data.getString("telefono"));
                    nombre1.setText(json_data.getString("nombre"));
                    nombre.setText(json_data.getString("nombre"));
                    paterno.setText(json_data.getString("apellido_paterno"));
                    materno.setText(json_data.getString("apellido_materno"));
                    correoU.setText(json_data.getString("correo"));
                    contra.setText(json_data.getString("contra"));

                    // Agregar log para verificar que se están mostrando los datos en los TextViews
                    Log.d("usuario_perfil", "Datos del usuario mostrados en TextViews");

                } else {
                    String mensaje = jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                telefono.setText("");
                nombre1.setText("");
                nombre.setText("");
                paterno.setText("");
                materno.setText("");
                correoU.setText("");
                contra.setText("");
                // Agregar log para verificar errores en onProgressUpdate
                Log.e("admin_perfil", "Error en onProgressUpdate: " + e.getMessage());
                Toast.makeText(getApplicationContext(), "Error al obtener los datos del usuario", Toast.LENGTH_LONG).show();
            }
        }
    }
}