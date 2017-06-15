package com.example.nicklink.weathertest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nicklink.weathertest.R;
import com.example.nicklink.weathertest.database.DatabaseContract.CityEntry;
import com.example.nicklink.weathertest.database.DatabaseContract.WeatherEntry;


/**
 * Created by NickLink on 10.06.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = DatabaseHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE "
                + WeatherEntry.TABLE_NAME + " ("
                + WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WeatherEntry.COLUMN_DATE + " INTEGER NOT NULL, "
                + WeatherEntry.COLUMN_CITY + " TEXT NOT NULL, "
                + WeatherEntry.COLUMN_DAYS + " INTEGER NOT NULL, "
                + WeatherEntry.COLUMN_DATA + " TEXT NOT NULL"
                + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);

        final String SQL_CREATE_CITY_TABLE = "CREATE TABLE "
                + CityEntry.TABLE_NAME + " ("
                + CityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CityEntry.COLUMN_CITY + " TEXT NOT NULL"
                + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_CITY_TABLE);

        //TODO Init default cities defined in resources string array
        initDefaultCities(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    private void initDefaultCities(SQLiteDatabase db){
        String[] city = context.getResources().getStringArray(R.array.city_array);
        ContentValues cv = new ContentValues();
        db.beginTransaction();
        try {
            for(int i = 0; i < (city.length - 1); i++){
                cv.put(CityEntry.COLUMN_CITY, city[i]);
                db.insert(CityEntry.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } catch (Exception e){
            Log.e(TAG, TAG + " Exception ->" + e.getLocalizedMessage());
        } finally {
            db.endTransaction();
        }
    }
}
