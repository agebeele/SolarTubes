package com.example.soes.inicializacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soes.R;
import com.example.soes.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class usuario_registro extends AppCompatActivity {
    String crud;
    WebService obj = new WebService();
    private EditText nombre, apellidoPaterno, apellidoMaterno, telefono, correoElctronico, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_registro);

        nombre = findViewById(R.id.nombreRegistro);
        apellidoPaterno = findViewById(R.id.paternoRegistro);
        apellidoMaterno = findViewById(R.id.maternoRegistro);
        telefono = findViewById(R.id.telefonoRegistro);
        correoElctronico = findViewById(R.id.correoRegistro);
        contraseña = findViewById(R.id.contraseñaRegistro);
    }
    public void registrarUsuario (View view){
        if (nombre.getText().toString().isEmpty()
                || apellidoPaterno.getText().toString().isEmpty()
                || apellidoMaterno.getText().toString().isEmpty()
                || telefono.getText().toString().isEmpty()
                || correoElctronico.getText().toString().isEmpty()
                || contraseña.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes", Toast.LENGTH_LONG).show();
        } else {
            crud = "registro";
            new MiAsyncTask().execute(crud,
                    nombre.getText().toString(),
                    apellidoPaterno.getText().toString(),
                    apellidoMaterno.getText().toString(),
                    telefono.getText().toString(),
                    correoElctronico.getText().toString(),
                    contraseña.getText().toString());
        }
    }
    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "registro":
                    msj = obj.registarUsuario(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6]);
                    publishProgress(msj);
                    break;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            try {
                JSONArray jArray = new JSONArray(progress[0]);
                JSONObject json_data = null;
                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                }
                nombre.setText(json_data.getString("nombre"));
                apellidoPaterno.setText(json_data.getString("apellido_paterno"));
                apellidoMaterno.setText(json_data.getString("apellido_materno"));
                telefono.setText(json_data.getString("telefono"));
                correoElctronico.setText(json_data.getString("correo"));
                contraseña.setText(json_data.getString("contra"));
                Toast.makeText(usuario_registro.this, "Usuario registrado.", Toast.LENGTH_SHORT).show();

                // Inicio de sesión exitoso, cambiar a la siguiente actividad
                Intent intent = new Intent(usuario_registro.this, usuario_login.class); // Cambia HomeActivity por el nombre de tu actividad de inicio
                startActivity(intent);
                finish(); // Opcional, si deseas finalizar la actividad actual

            } catch (JSONException e) {
                nombre.setText("");
                apellidoPaterno.setText("");
                apellidoMaterno.setText("");
                telefono.setText("");
                correoElctronico.setText("");
                contraseña.setText("");
                Toast.makeText(getApplicationContext(), progress[0], Toast.LENGTH_LONG).show();
            }
        }
    }
}