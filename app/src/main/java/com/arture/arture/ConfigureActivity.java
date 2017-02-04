package com.arture.arture;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import database.PlantDBO;
import model.Plant;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ConfigureActivity extends AppCompatActivity {

    PlantDBO mPlantDBO;
    Plant selectedPlant;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPlantDBO = new PlantDBO(getApplicationContext());
        List<Plant> plants = mPlantDBO.getAllPlants();
        ArrayAdapter plantsAdapter = new ArrayAdapter(this, R.layout.spinner, plants);

        Spinner plantSpinner = (Spinner) findViewById(R.id.choosePlant);
        plantSpinner.setAdapter(plantsAdapter);
        plantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPlant = (Plant) ((Spinner) findViewById(R.id.choosePlant)).getSelectedItem();
                TextView ph = (TextView) findViewById(R.id.viewPH);
                TextView ppm = (TextView) findViewById(R.id.viewPPM);

                ph.setText(selectedPlant.getPH().toString());
                ppm.setText(selectedPlant.getPPM().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



}
