package com.example.soes.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soes.R;
import com.example.soes.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class admin_editarUsuarios extends AppCompatActivity {
    EditText id, nombre, paterno, materno, telefono, correo, contra;
    String crud;
    WebService obj = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_editar_usuarios);

        id = (EditText) findViewById(R.id.idUsuario);
        nombre = (EditText) findViewById(R.id.nombreUsuario);
        paterno = (EditText) findViewById(R.id.paternoUusuario);
        materno = (EditText) findViewById(R.id.maternoUsuario);
        telefono = (EditText) findViewById(R.id.telefonoUsuario);
        correo = (EditText) findViewById(R.id.correoUsuario);
        contra = (EditText) findViewById(R.id.contraUsuario);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "borrar":
                    msj = obj.eliminarUsuario(parameter[1]);
                    publishProgress(msj);
                    break;
                case "actualizar":
                    msj = obj.actualizarUsuario(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5]);
                    publishProgress(msj);
                    break;
                case "buscar":
                    msj = obj.buscarUsuario(parameter[1]);
                    publishProgress(msj);
                    break;
                default:
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
                id.setText(json_data.getString("id"));
                nombre.setText(json_data.getString("nombre"));
                paterno.setText(json_data.getString("apellido_paterno"));
                materno.setText(json_data.getString("apellido_materno"));
                telefono.setText(json_data.getString("telefono"));
                correo.setText(json_data.getString("correo"));
                contra.setText(json_data.getString("contra"));
            } catch (JSONException e) {
                id.setText("");
                nombre.setText("");
                paterno.setText("");
                materno.setText("");
                telefono.setText("");
                correo.setText("");
                contra.setText("");
                Toast.makeText(getApplicationContext(), progress[0], Toast.LENGTH_LONG).show();
            }
        }

    }
    public void ActualizarPerfil (View view){
        if(id.getText().toString().isEmpty()||
                nombre.getText().toString().isEmpty()||
                paterno.getText().toString().isEmpty()||
                materno.getText().toString().isEmpty()||
                telefono.getText().toString().isEmpty()||
                correo.getText().toString().isEmpty()||
                contra.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Datos Faltantes :3",Toast.LENGTH_LONG).show();
        }
        else {crud="actualizar";
            new MiAsyncTask().execute(crud,
                    id.getText().toString(),
                    nombre.getText().toString(),
                    paterno.getText().toString(),
                    materno.getText().toString(),
                    telefono.getText().toString(),
                    telefono.getText().toString(),
                    correo.getText().toString(),
                    contra.getText().toString());
        }
    }

    public void BuscarPerfil (View view){
        if (id.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes :)", Toast.LENGTH_LONG).show();
        } else {crud = "buscar";
            new MiAsyncTask().execute(crud,id.getText().toString());

        }
    }

    public void BorrarPerfil (View view){
        if (id.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes :(", Toast.LENGTH_LONG).show();
        } else {crud = "borrar";
            new MiAsyncTask().execute(crud,id.getText().toString());

        }
    }
    public void regresarPantalla(View view){
        onBackPressed();
    }
    public void limpiarDatos(View view){
        id.setText("");
        nombre.setText("");
        paterno.setText("");
        materno.setText("");
        telefono.setText("");
        correo.setText("");
        contra.setText("");
    }
}