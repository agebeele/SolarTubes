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

public class admin_editarSoporte extends AppCompatActivity {
    EditText id, compania, cantidad_tubos, cantidad_personas, tiempo_garantia, telefono, correo_e, pagina, direc;
    String crud;
    WebService obj = new WebService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_editar_soporte);

        id = (EditText) findViewById(R.id.txt_id);
        compania = (EditText) findViewById(R.id.txt_compania);
        cantidad_tubos = (EditText) findViewById(R.id.txt_tubos);
        cantidad_personas = (EditText) findViewById(R.id.txt_personas);
        tiempo_garantia = (EditText) findViewById(R.id.txt_garantia);
        telefono = (EditText) findViewById(R.id.txt_tel);
        correo_e = (EditText) findViewById(R.id.txt_correo);
        pagina = (EditText) findViewById(R.id.txt_web);
        direc = (EditText) findViewById(R.id.txt_direccion);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "insertar":
                    msj = obj.agregarCalentador(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7], parameter[8],parameter[9]);
                    publishProgress(msj);
                    break;
                case "borrar":
                    msj = obj.eliminarCalentador(parameter[1]);
                    publishProgress(msj);
                    break;
                case "actualizar":
                    msj = obj.actualizarCalentador(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7],parameter[8],parameter[9]);
                    publishProgress(msj);
                    break;
                case "buscar":
                    msj = obj.buscarCalentador(parameter[1]);
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
                compania.setText(json_data.getString("compania"));
                cantidad_tubos.setText(json_data.getString("cantidad_tubos"));
                cantidad_personas.setText(json_data.getString("cantidad_personas"));
                tiempo_garantia.setText(json_data.getString("tiempo_garantia"));
                telefono.setText(json_data.getString("telefono"));
                correo_e.setText(json_data.getString("correo_e"));
                pagina.setText(json_data.getString("pagina_web"));
                direc.setText(json_data.getString("direccion"));
            } catch (JSONException e) {
                id.setText("");
                compania.setText("");
                cantidad_tubos.setText("");
                cantidad_personas.setText("");
                tiempo_garantia.setText("");
                telefono.setText("");
                correo_e.setText("");
                direc.setText("");
                pagina.setText("");
                Toast.makeText(getApplicationContext(), progress[0], Toast.LENGTH_LONG).show();
            }
        }

    }

    public void InsertarPerfil (View view){
        if(id.getText().toString().isEmpty()||
                compania.getText().toString().isEmpty()||
                cantidad_tubos.getText().toString().isEmpty()||
                cantidad_personas.getText().toString().isEmpty()||
                tiempo_garantia.getText().toString().isEmpty()||
                telefono.getText().toString().isEmpty()||
                pagina.getText().toString().isEmpty()||
                correo_e.getText().toString().isEmpty()||
                direc.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Datos Faltantes :3",Toast.LENGTH_LONG).show();
        }
        else {crud="insertar";
            new MiAsyncTask().execute(crud,id.getText().toString(),
                    compania.getText().toString(),
                    cantidad_tubos.getText().toString(),
                    cantidad_personas.getText().toString(),
                    tiempo_garantia.getText().toString(),
                    telefono.getText().toString(),
                    pagina.getText().toString(),
                    correo_e.getText().toString(),
                    direc.getText().toString());
        }
    }

    public void ActualizarPerfil (View view){
        if(id.getText().toString().isEmpty()||
                compania.getText().toString().isEmpty()||
                cantidad_tubos.getText().toString().isEmpty()||
                cantidad_personas.getText().toString().isEmpty()||
                tiempo_garantia.getText().toString().isEmpty()||
                telefono.getText().toString().isEmpty()||
                pagina.getText().toString().isEmpty()||
                correo_e.getText().toString().isEmpty()||
                direc.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Datos Faltantes :3",Toast.LENGTH_LONG).show();
        }
        else {crud="actualizar";
            new MiAsyncTask().execute(crud,
                    id.getText().toString(),
                    compania.getText().toString(),
                    cantidad_tubos.getText().toString(),
                    cantidad_personas.getText().toString(),
                    tiempo_garantia.getText().toString(),
                    telefono.getText().toString(),
                    pagina.getText().toString(),
                    correo_e.getText().toString(),
                    direc.getText().toString());
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
        compania.setText("");
        cantidad_tubos.setText("");
        cantidad_personas.setText("");
        tiempo_garantia.setText("");
        telefono.setText("");
        correo_e.setText("");
        direc.setText("");
        pagina.setText("");
    }
}