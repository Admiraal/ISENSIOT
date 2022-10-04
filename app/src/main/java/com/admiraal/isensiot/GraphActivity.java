package com.admiraal.isensiot;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.time.Duration;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private Button closeBtn;
    private LineChart sensorChart;
    private ArrayList<ILineDataSet> dataSets;
    private LineDataSet lineDataSet;
    private int measureAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        this.sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        this.initCloseBtn();
        this.initChart();
    }

    private ArrayList<Entry> defaultData(){
        ArrayList<Entry> dataValues = new ArrayList<>();
        dataValues.add(new Entry(0,1));
        return dataValues;
    }

    private void initChart(){

        this.lineDataSet = new LineDataSet(this.defaultData(), "Sensor Values");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        this.dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        this.sensorChart = (LineChart) findViewById(R.id.sensorDataChart);

        //customization
        sensorChart.setTouchEnabled(true);
        sensorChart.setDragEnabled(true);
        sensorChart.setScaleEnabled(true);
        sensorChart.setPinchZoom(false);
        sensorChart.setDrawGridBackground(false);
        sensorChart.setExtraLeftOffset(15);
        sensorChart.setExtraRightOffset(15);
        sensorChart.setAutoScaleMinMaxEnabled(true);

        // set background lines
        sensorChart.getXAxis().setDrawGridLines(true);
        sensorChart.getAxisLeft().setDrawGridLines(true);
        sensorChart.getAxisRight().setDrawGridLines(true);


        // add axis
        YAxis rightYAxis = sensorChart.getAxisRight();
        rightYAxis.setEnabled(true);

        XAxis topXAxis = sensorChart.getXAxis();
        topXAxis.setEnabled(false);
        XAxis xAxis = sensorChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        lineDataSet.setLineWidth(4f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setDrawValues(false);
        lineDataSet.setCircleHoleColor(getResources().getColor(R.color.purple_200));
        lineDataSet.setCircleColor(getResources().getColor(R.color.teal_200));

        // set the graph data
        LineData data = new LineData(this.dataSets);
        sensorChart.setData(data);
        sensorChart.animateX(2000);
        sensorChart.invalidate();
        sensorChart.getLegend().setEnabled(false);
        sensorChart.getDescription().setEnabled(true);
        Description description = new Description();
        String descriptionTxt = getResources().getString(R.string.sensorGraphData);
        description.setText(descriptionTxt);
        description.setTextSize(15f);
        sensorChart.setDescription(description);
    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onResume(){
        super.onResume();

        this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(sensor != null){
            sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_FASTEST);
        } else {
            String inaccurateDataMessage = getResources().getString(R.string.sensorNotAvailable);
            Toast toast = Toast.makeText(this, inaccurateDataMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float sensorValue = sensorEvent.values[0];
        this.measureAmount += 1;
        this.lineDataSet.addEntry(new Entry(measureAmount, sensorValue));
        this.sensorChart.notifyDataSetChanged();
        this.sensorChart.invalidate();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        String inaccurateDataMessage = getResources().getString(R.string.sensorGraphData);
        Toast toast = Toast.makeText(this, inaccurateDataMessage, Toast.LENGTH_SHORT);
        toast.show();
    }


    private void initCloseBtn(){
        this.closeBtn = (Button) findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
    }

    private void closeActivity(){
        this.finish();
    }
}