package com.arture.arture;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import handler.MqttHandler;
import handler.ChartHandler;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MonitorActivity extends AppCompatActivity {

    MqttHandler mqttHandler;

    ChartHandler mChartPh;
    LineChart chartPh;
    ChartHandler mChartPpm;
    LineChart chartPpm;

    EditText editText;

    TextView textCurrentPlant;



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //textCurrentPlant = (TextView) findViewById(R.id.currentPlant);
        chartPh = (LineChart) findViewById(R.id.chartPh);
        chartPpm = (LineChart) findViewById(R.id.chartPpm);

        editText = (EditText) findViewById(R.id.editText);

        mChartPh = new ChartHandler(chartPh,true);
        mChartPpm = new ChartHandler(chartPpm,false);

        startMqtt();

    }

    private void startMqtt(){

        mqttHandler = new MqttHandler(getApplicationContext());
        mqttHandler.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("anjing",topic);
                if(topic.contains("/ph")) mChartPh.addEntry(Float.valueOf(mqttMessage.toString()));
                else if(topic.contains("/ppm")) mChartPpm.addEntry(Float.valueOf(mqttMessage.toString()));
                else editText.setText(mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

}
