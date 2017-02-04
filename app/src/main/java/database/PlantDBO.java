package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Plant;

/**
 * Created by wildan on 7/30/2016.
 */
public class PlantDBO {

    public static final String TAG_NAME = "PlantDBO";

    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;
    private Context mContext;
    private String[] mAllColumns =
            {mDBHelper.COLUMN_PLANT_KEY_ID, mDBHelper.COLUMN_PLANT_NAME,
                    mDBHelper.COLUMN_PLANT_PH, mDBHelper.COLUMN_PLANT_PPM};


    public PlantDBO(Context context){
        this.mContext = context;
        mDBHelper = new DBHelper(context);

        //open database
        try{
            open();
        } catch (SQLException e){
            Log.e(TAG_NAME, "SQLException on opening database, " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close(){ mDBHelper.close(); }

    public List<Plant> getAllPlants(){
        List<Plant> plants = new ArrayList<>();

        //grab all the data on table
        Cursor cursor = mDatabase.query(mDBHelper.TABLE_PLANT_NAME, mAllColumns,
                null, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Plant plant = cursorToPlant(cursor);
                plants.add(plant);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return plants;
    }

    private Plant cursorToPlant(Cursor cursor){
        Plant plant = new Plant();

        plant.setId(cursor.getInt(
                cursor.getColumnIndex(DBHelper.COLUMN_PLANT_KEY_ID)));
        plant.setName((cursor.getString(
                cursor.getColumnIndex(DBHelper.COLUMN_PLANT_NAME))));
        plant.setPH(plantConfigParse((cursor.getString(
                cursor.getColumnIndex(DBHelper.COLUMN_PLANT_PH)))));
        plant.setPPM(plantConfigParse((cursor.getString(
                cursor.getColumnIndex(DBHelper.COLUMN_PLANT_PPM)))));

        return plant;
    }

    // A reverse funtion for plant configs (PH and PPM) in DBHelper's plantsToString() method to Plant.Config object
    public Plant.Config plantConfigParse(String plantConfigString){
        Plant.Config plantConfig = new Plant.Config();
        plantConfigString.trim();
        String[] plantConfigs = plantConfigString.split("-");
        double TL = Double.parseDouble(plantConfigs[0]);
        double TH = Double.parseDouble(plantConfigs[1]);
        plantConfig = new Plant.Config(TL,TH);

        return plantConfig;
    }

}
