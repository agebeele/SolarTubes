package com.example.soes.inicializacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soes.R;
import com.example.soes.WebService.WebService;
import com.example.soes.admin.admin_login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class usuario_login extends AppCompatActivity {
    String crud;
    WebService obj = new WebService();
    EditText correoUsuario, contraseñaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_login);

        correoUsuario = findViewById(R.id.correoLogin);
        contraseñaUsuario = findViewById(R.id.contraLogin);
    }
    public void registrarUsuario (View view){
        Intent intent = new Intent(usuario_login.this, usuario_registro.class);
        startActivity(intent);
    }
    public void admin (View view){
        Intent intent = new Intent(usuario_login.this, admin_login.class);
        startActivity(intent);
    }
    public void iniciarSesion (View view){
        if (correoUsuario.getText().toString().isEmpty() || contraseñaUsuario.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes", Toast.LENGTH_LONG).show();
        } else {
            crud = "log";
            new MiAsyncTask().execute(crud, correoUsuario.getText().toString(), contraseñaUsuario.getText().toString());
        }
    }
    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "log":
                    msj = obj.login(parameter[1], parameter[2]);
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

                // Obtener la matrícula del JSON
                String correo = json_data.getString("correo");

                // Resto del código para redireccionar a la próxima actividad
                correoUsuario.setText(correo);
                contraseñaUsuario.setText(json_data.getString("contra"));
                Toast.makeText(usuario_login.this, "Bienvenido...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(usuario_login.this, usuario_inicio.class);
                startActivity(intent);
                finish();

                // Guardar la matrícula en SharedPreferences al iniciar sesión
                SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("correo", correo);
                editor.apply();

                // Agregar log para verificar que se guardaron los datos en SharedPreferences
                Log.d("usuario_login", "Correo guardado en SharedPreferences: " + correo);

            } catch (JSONException e) {
                correoUsuario.setText("");
                contraseñaUsuario.setText("");
                Toast.makeText(getApplicationContext(), progress[0], Toast.LENGTH_LONG).show();
            }
        }
    }
}