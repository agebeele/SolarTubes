package com.example.soes.inicializacion;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.soes.R;

public class usuario_pantallaCarga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtén la referencia de la ProgressBar desde el diseño
        ProgressBar progressBar = findViewById(R.id.progressBar);

        // Crea un objeto ObjectAnimator para animar el progreso de la ProgressBar
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);

        // Establece la duración de la animación en milisegundos (en este caso, 5000 ms o 5 segundos)
        progressAnimator.setDuration(5000);

        // Inicia la animación
        progressAnimator.start();

        // Configura un Handler para iniciar la nueva actividad después de 5 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Inicia la nueva actividad aquí
                Intent intent = new Intent(usuario_pantallaCarga.this, usuario_login.class);
                startActivity(intent);
                finish(); // Opcional: cierra la actividad actual si no quieres volver a ella
            }
        }, 5000); // 5000 ms o 5 segundos
    }
}