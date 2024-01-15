package com.example.soes.inicializacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.example.soes.R;
import com.example.soes.databinding.ActivityUsuarioInicioBinding;
import com.example.soes.fragments.usuarioFragment_Configuracion;
import com.example.soes.fragments.usuarioFragment_explorar;
import com.example.soes.fragments.usuarioFragment_soporte;
import com.example.soes.fragments.usuarioFragmet_inicio;

public class usuario_inicio extends AppCompatActivity {
    ActivityUsuarioInicioBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_inicio);

        binding = ActivityUsuarioInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Iniciar transacción de fragmento solo si savedInstanceState es nulo
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.contenedorFragment.getId(), new usuarioFragmet_inicio())
                    .commit();
        }
        binding.barraNavegacion.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.inicio) {
                replaceFragment(new usuarioFragmet_inicio());
            } else if (item.getItemId() == R.id.explorar) {
                replaceFragment(new usuarioFragment_explorar());
            } else if (item.getItemId() == R.id.soporte) {
                replaceFragment(new usuarioFragment_soporte());
            } else if (item.getItemId() == R.id.configuracion) {
                replaceFragment(new usuarioFragment_Configuracion());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragment, fragment);
        fragmentTransaction.commit();
    }
}