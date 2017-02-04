package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import model.Plant;

/**
 * Created by wildan on 7/30/2016.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "AccountDataClinic";
    public static final int DATABASE_VERSION = 1;
    public static final String TAG_NAME = "DBHelper";

    //account table
    public static final String TABLE_PLANT_NAME = "plant";
    public static final String COLUMN_PLANT_KEY_ID = "_id";
    public static final String COLUMN_PLANT_NAME = "name";
    public static final String COLUMN_PLANT_PH = "ph";
    public static final String COLUMN_PLANT_PPM = "ppm";

    private Plant[] plants = new Plant[] {
            new Plant("Eggplant",new Plant.Config(5.5,6.5),new Plant.Config(1740,2450)),
            new Plant("Artichoke",new Plant.Config(6.5,7.5),new Plant.Config(560,1260)),
            new Plant("Asparagus",new Plant.Config(6.0,6.8),new Plant.Config(560,1260))
    };

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String Q_CREATE_TABLE_PLANT = "CREATE TABLE " + TABLE_PLANT_NAME + "("
            + COLUMN_PLANT_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PLANT_NAME + " TEXT NOT NULL, "
            + COLUMN_PLANT_PH + " TEXT NOT NULL, "
            + COLUMN_PLANT_PPM + " TEXT NOT NULL"
            + ");";

    private final String Q_ADD_VALUE_TABLE_PLANT = "INSERT INTO " + TABLE_PLANT_NAME
            + " VALUES "
            + plantsToString();

    // plants value SQL string format : (NULL, "Name", "phTL-phTH", "ppmTL-ppmTH")
    private String plantsToString(){
        String plantStrings = new String();
        for (Plant plant:plants
             ) {
            plantStrings += "(NULL,\""
                    + plant.getName()+"\",\""
                    + plant.getPH().toString()+"\",\""
                    + plant.getPPM().toString()+"\""
                    + "),";
        }
        plantStrings = plantStrings.substring(0,plantStrings.length()-1)+";";
        return plantStrings;
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(Q_CREATE_TABLE_PLANT);
        db.execSQL(Q_ADD_VALUE_TABLE_PLANT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(TAG_NAME, "Upgrading the database form version " + oldVersion + " to " + newVersion);

        //clear all data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANT_NAME);

        //recreate tables
        onCreate(db);
    }

}
