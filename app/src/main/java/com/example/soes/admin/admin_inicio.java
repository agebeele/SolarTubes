package com.example.soes.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.soes.R;
import com.example.soes.admin.admin_fragmentConfiguracion;
import com.example.soes.admin.admin_fragmentSoporte;
import com.example.soes.databinding.ActivityAdminInicioBinding;

public class admin_inicio extends AppCompatActivity {
    ActivityAdminInicioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inicio);
        binding = ActivityAdminInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Iniciar transacción de fragmento solo si savedInstanceState es nulo
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.contenedorAdmin.getId(), new admin_fragmentSoporte())
                    .commit();
        }
        binding.barraNavegacionAdmin.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.soporte) {
                replaceFragment(new admin_fragmentSoporte());
            } else if (item.getItemId() == R.id.configuracion) {
                replaceFragment(new admin_fragmentConfiguracion());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorAdmin, fragment);
        fragmentTransaction.commit();
    }
}