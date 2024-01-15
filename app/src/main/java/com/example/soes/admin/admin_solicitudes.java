package com.example.soes.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.soes.R;
import com.example.soes.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class admin_solicitudes extends AppCompatActivity {
    String msj;
    WebService obj = new WebService();
    RecyclerView recyclerView;
    admin_solicitudesAdapter adapter;
    private List<String> idList = new ArrayList<>();
    private List<String> nombreList = new ArrayList<>();
    private List<String> maternoList = new ArrayList<>();
    private List<String> paternoList = new ArrayList<>();
    private List<String> telefonoList = new ArrayList<>();
    private List<String> correoList = new ArrayList<>();
    private List<String> contraList = new ArrayList<>();
    private List<String> horaList = new ArrayList<>();
    private List<String> fechaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_solicitudes);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSolicitudes);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(admin_solicitudes.this));

        adapter = new admin_solicitudesAdapter(nombreList, paternoList, maternoList, fechaList, this);

        recyclerView.setAdapter(adapter);

        MiAsyncTask miAsyncTask = new MiAsyncTask();
        miAsyncTask.execute();

        adapter.setOnItemClickListener(new admin_solicitudesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(getApplicationContext(), admin_solicitudesItem.class);
                i.putExtra("id", idList.get(position));
                i.putExtra("nombre", nombreList.get(position));
                i.putExtra("apellido_paterno", paternoList.get(position));
                i.putExtra("apellido_materno", maternoList.get(position));
                i.putExtra("correo", correoList.get(position));
                i.putExtra("contra", contraList.get(position));
                i.putExtra("telefono", telefonoList.get(position));
                i.putExtra("fecha", fechaList.get(position));
                i.putExtra("hora", horaList.get(position));

                startActivity(i);
            }
        });
    }
    class MiAsyncTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... parameters) {
            msj = obj.solicitudesInformacion();
            publishProgress(msj);
            return null;
        }
        @SuppressLint({"NotifyDataSetChanged", "DiscouragedApi"})
        @Override
        protected void onProgressUpdate(String... progress) {
            try {
                JSONArray jsonArray = new JSONArray(progress[0]);
                JSONObject json_data;
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    json_data = jsonArray.getJSONObject(i);

                    String nombre =  json_data.getString("nombre");
                    String paterno =  json_data.getString("apellido_paterno");
                    String materno =  json_data.getString("apellido_materno");
                    String correo =  json_data.getString("correo");
                    String contra =  json_data.getString("contra");
                    String telefono =  json_data.getString("telefono");

                    String fecha = json_data.getString("fecha");
                    String hora = json_data.getString("hora");
                    String id =  json_data.getString("id");

                    nombreList.add(nombre);
                    paternoList.add(paterno);
                    maternoList.add(materno);
                    correoList.add(correo);
                    contraList.add(contra);
                    idList.add(id);
                    telefonoList.add(telefono);

                    fechaList.add(fecha);
                    horaList.add(hora);
                }
            } catch (JSONException e) {
                Toast.makeText(admin_solicitudes.this, progress[0], Toast.LENGTH_LONG).show();
            }
            adapter.notifyDataSetChanged();
        }
    }
}