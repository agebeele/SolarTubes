package com.example.soes.admin;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class admin_login extends AppCompatActivity {
    String crud;
    WebService obj = new WebService();
    EditText correoAdmin, contraseñaAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        correoAdmin = findViewById(R.id.correoAdmin);
        contraseñaAdmin = findViewById(R.id.contraAdmin);
    }
    public void iniciarSesion (View view){
        if (correoAdmin.getText().toString().isEmpty() || contraseñaAdmin.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes", Toast.LENGTH_LONG).show();
        } else {
            crud = "log";
            new MiAsyncTask().execute(crud, correoAdmin.getText().toString(), contraseñaAdmin.getText().toString());
        }
    }
    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "log":
                    msj = obj.login_admin(parameter[1], parameter[2]);
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
                correoAdmin.setText(correo);
                contraseñaAdmin.setText(json_data.getString("contra"));
                Toast.makeText(admin_login.this, "Bienvenido...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(admin_login.this, admin_inicio.class);
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
                correoAdmin.setText("");
                contraseñaAdmin.setText("");
                Toast.makeText(getApplicationContext(), progress[0], Toast.LENGTH_LONG).show();
            }
        }
    }
}