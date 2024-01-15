package com.example.soes.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.soes.R;
import com.example.soes.WebService.WebService;
import com.example.soes.fragments.usuarioFragment_soporte;
import com.example.soes.inicializacion.usuario_soporte;
import com.example.soes.inicializacion.usuario_soporteItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class admin_fragmentSoporte extends Fragment {
    RecyclerView recyclerView;
    admin_soporte myAdapter;
    String msj;
    WebService obj = new WebService();
    private List<String> idList = new ArrayList<>();
    private  List<String> companiaList = new ArrayList<>();
    private  List<String> tubosList = new ArrayList<>();
    private  List<String> personasList = new ArrayList<>();
    private  List<String> garantialist = new ArrayList<>();
    private  List<String> telefonoList = new ArrayList<>();
    private  List<String> paginaList = new ArrayList<>();
    private  List<String> correoList = new ArrayList<>();
    private  List<String> direccionList = new ArrayList<>();
    private  List<String> imageList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin_soporte, container, false);

        recyclerView = v.findViewById(R.id.recyclerView_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myAdapter = new admin_soporte(idList, companiaList, telefonoList, imageList);
        myAdapter.setOnItemClickListener(new usuario_soporte.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent i = new Intent(getActivity(), admin_soporteItem.class);
                i.putExtra("id", idList.get(position));
                i.putExtra("compania", companiaList.get(position));
                i.putExtra("cantidad_tubos", tubosList.get(position));
                i.putExtra("cantidad_personas", personasList.get(position));
                i.putExtra("tiempo_garantia", garantialist.get(position));
                i.putExtra("telefono", telefonoList.get(position));
                i.putExtra("pagina_web", paginaList.get(position));
                i.putExtra("correo_e", correoList.get(position));
                i.putExtra("direccion", direccionList.get(position));
                i.putExtra("imagen", imageList.get(position));
                startActivity(i);
            }
        });
        recyclerView.setAdapter(myAdapter);

        MiAsyncTask miAsyncTask = new MiAsyncTask();
        miAsyncTask.execute();

        return v;
    }
    class MiAsyncTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... parameters) {
            msj = obj.buscarUno();
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

                    idList.add(json_data.getString("id"));
                    companiaList.add(json_data.getString("compania"));
                    tubosList.add(json_data.getString("cantidad_tubos"));
                    personasList.add(json_data.getString("cantidad_personas"));
                    garantialist.add(json_data.getString("tiempo_garantia"));
                    telefonoList.add(json_data.getString("telefono"));
                    paginaList.add(json_data.getString("pagina_web"));
                    correoList.add(json_data.getString("correo_e"));
                    direccionList.add(json_data.getString("direccion"));

                    imageList.add(String.valueOf(R.drawable.solaris1));
                    imageList.add(String.valueOf(R.drawable.freecon1));
                    imageList.add(String.valueOf(R.drawable.sunnergy1));
                    imageList.add(String.valueOf(R.drawable.era1));
                    imageList.add(String.valueOf(R.drawable.sunshine1));
                    imageList.add(String.valueOf(R.drawable.optimus1));
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(), progress[0], Toast.LENGTH_LONG).show();
            }
            myAdapter.notifyDataSetChanged();
        }
    }
}