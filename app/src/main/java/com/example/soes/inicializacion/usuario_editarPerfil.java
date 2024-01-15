package com.example.soes.inicializacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soes.R;
import com.example.soes.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class usuario_editarPerfil extends AppCompatActivity {
    EditText datoContra, datoPaterno, datoMaterno, datoTelefono, datoNombres;
    TextView datoCorreo, datoId;
    WebService obj = new WebService();
    String crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_editar_perfil);

        datoId = findViewById(R.id.dato_id);
        datoNombres = findViewById(R.id.dato_nombres);
        datoPaterno = findViewById(R.id.dato_apellidoPaterno);
        datoMaterno = findViewById(R.id.dato_apellidoMaterno);
        datoTelefono = findViewById(R.id.dato_telefono);
        datoCorreo = findViewById(R.id.dato_correoElectronico);
        datoContra = findViewById(R.id.dato_contraseña);

        // Recuperar el correo guardado en SharedPreferences
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String correoUsuario = preferences.getString("correo", "");

        if (!correoUsuario.isEmpty()) {
            // Ejecutar AsyncTask para obtener los datos del usuario
            MiAsyncTask miAsyncTask = new MiAsyncTask();
            miAsyncTask.execute("datosUsuario", correoUsuario);
        }

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    class MiAsyncTask extends AsyncTask<String, String, String> {
        private SharedPreferences preferences;

        @Override
        protected String doInBackground(String... parameter) {
            String msj = null;
            try {
                switch (parameter[0]) {
                    case "datosUsuario":
                        msj = obj.datosUsuarioCorreo(parameter[1]);
                        if (msj != null) {
                            publishProgress(msj);
                        }
                        break;
                    case "actualizarInformacion":
                        msj = obj.enviarSolicitud(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7],parameter[8],parameter[9]);
                        if (msj != null) {
                            publishProgress(msj);
                        }
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                msj = "Error en la ejecución de la tarea asíncrona.";
                publishProgress(msj);
            }
            return msj;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            preferences = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            Log.d("JSON_DEBUG", progress[0]);

            if (!TextUtils.isEmpty(progress[0])) {
                try {
                    JSONObject jsonObject = new JSONObject(progress[0]);

                    if (jsonObject.has("message")) {
                        String mensaje = jsonObject.getString("message");
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                    }

                    if (jsonObject.has("success") && jsonObject.getBoolean("success")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        JSONObject usuario = data.getJSONObject(0);

                        // Actualizar los campos en los EditText
                        datoId.setText(usuario.getString("id"));
                        datoNombres.setText(usuario.getString("nombre"));
                        datoPaterno.setText(usuario.getString("apellido_paterno"));
                        datoMaterno.setText(usuario.getString("apellido_materno"));
                        datoTelefono.setText(usuario.getString("telefono"));
                        datoCorreo.setText(usuario.getString("correo"));
                        datoContra.setText(usuario.getString("contra"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Solicitud Enviada", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error: Respuesta nula o vacía del servidor", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void enviarSolicitud(View view) {
        try {
            if (datoNombres.getText().toString().isEmpty() ||
                    datoPaterno.getText().toString().isEmpty() ||
                    datoCorreo.getText().toString().isEmpty() ||
                    datoId.getText().toString().isEmpty() ||
                    datoMaterno.getText().toString().isEmpty() ||
                    datoTelefono.getText().toString().isEmpty() ||
                    datoContra.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Datos Faltantes.", Toast.LENGTH_LONG).show();
            } else {

                // Obtener fecha y hora actual por separado
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String fechaActual = dateFormat.format(calendar.getTime());
                String horaActual = timeFormat.format(calendar.getTime());

                crud = "actualizarInformacion";
                new MiAsyncTask().execute(
                        crud,
                        datoId.getText().toString(),
                        datoNombres.getText().toString(),
                        datoPaterno.getText().toString(),
                        datoMaterno.getText().toString(),
                        datoTelefono.getText().toString(),
                        datoCorreo.getText().toString(),
                        datoContra.getText().toString(),
                        fechaActual,
                        horaActual);

                // Después de enviar la solicitud, inicia otra actividad
                Intent intent = new Intent(usuario_editarPerfil.this, usuario_perfil.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}