package com.example.soes.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soes.R;

import java.util.Random;

public class usuarioFragmet_inicio extends Fragment {
    @Nullable
    private TextView textViewTemperature, tiempoAguaCaliente, personasEstimadas;
    private Handler handler;
    private Runnable temperatureUpdateRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_usuario_fragmet_inicio, container, false);

        tiempoAguaCaliente = v.findViewById(R.id.tiempoAgua);
        personasEstimadas = v.findViewById(R.id.personasEstimadas);

        textViewTemperature = v.findViewById(R.id.textViewTemperature);
        handler = new Handler(Looper.getMainLooper());

        temperatureUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                generateRandomTemperature();
                handler.postDelayed(this, 5000); // Actualizar cada 5 segundos
            }
        };

        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        startTemperatureUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTemperatureUpdates();
    }

    private void startTemperatureUpdates() {
        handler.postDelayed(temperatureUpdateRunnable, 0);
    }

    private void stopTemperatureUpdates() {
        handler.removeCallbacks(temperatureUpdateRunnable);
    }

    private void generateRandomTemperature() {
        Random random = new Random();
        int temperature = random.nextInt(41) - 10; // Genera un número aleatorio entre -10 y 30 (grados centígrados)
        String temperatureString = String.valueOf(temperature) + " °C";
        textViewTemperature.setText(temperatureString);

        // Lógica para tiempoAguaCaliente y personasEstimadas
        if (temperature < 0) {
            // Muy frío
            tiempoAguaCaliente.setText("10 minutos");
            personasEstimadas.setText("5 personas");
        } else if (temperature >= 0 && temperature < 10) {
            // Frío
            tiempoAguaCaliente.setText("15 minutos");
            personasEstimadas.setText("8 personas");
        } else if (temperature >= 10 && temperature < 20) {
            // Templado
            tiempoAguaCaliente.setText("20 minutos");
            personasEstimadas.setText("10 personas");
        } else {
            // Caliente
            tiempoAguaCaliente.setText("30 minutos");
            personasEstimadas.setText("15 personas");
        }
    }

}