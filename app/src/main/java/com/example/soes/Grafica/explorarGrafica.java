package com.example.soes.Grafica;


import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import com.example.soes.R;

public class explorarGrafica extends AppCompatActivity {
    private static final String TAG= "grafica";
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar_grafica);

        mChart = (LineChart) findViewById(R.id.linechart1);

        mChart.setDragXEnabled(true);
        mChart.setScaleEnabled(true);

        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0,60f));
        yValues.add(new Entry(1,50f));
        yValues.add(new Entry(2,70f));
        yValues.add(new Entry(3,30f));
        yValues.add(new Entry(4,50f));
        yValues.add(new Entry(5,60f));
        yValues.add(new Entry(6,65f));

        LineDataSet set1 = new LineDataSet(yValues, "Dato set 1");
        set1.setFillAlpha(110);

        set1.setColor(Color.RED);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);


        LineData data=  new LineData(dataSets);

        mChart.setData(data);
    }
}