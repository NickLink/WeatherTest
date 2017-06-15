package com.example.nicklink.weathertest.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.nicklink.weathertest.database.DatabaseContract.CityEntry;
import com.example.nicklink.weathertest.database.DatabaseContract.WeatherEntry;
import com.example.nicklink.weathertest.domain.weather.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import static com.example.nicklink.weathertest.database.DatabaseContract.DEFAULT_CITY;
import static com.example.nicklink.weathertest.database.DatabaseContract.DEFAULT_DAY;


/**
 * Created by NickLink on 10.06.2017.
 */

public class WeatherDatabaseImpl implements WeatherDatabase {
    public static final String TAG = WeatherDatabaseImpl.class.getSimpleName();
    private Gson gson;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private SharedPreferences prefs;
    Context context;

    public WeatherDatabaseImpl(Context context) {
        this.context = context;
        gson = new GsonBuilder().create();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    @Override
    public void saveWeather(String city, int days, WeatherResponse response) {
        deleteWeather(city, days);
        ContentValues cv = new ContentValues();
        cv.put(WeatherEntry.COLUMN_CITY, city);
        cv.put(WeatherEntry.COLUMN_DAYS, days);
        cv.put(WeatherEntry.COLUMN_DATE, String.valueOf(System.currentTimeMillis()));
        cv.put(WeatherEntry.COLUMN_DATA, gson.toJson(response));
        db.beginTransaction();
        try {
            if (db.insert(WeatherEntry.TABLE_NAME, null, cv) != -1) {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e(TAG, TAG + " saveWeather ->" + " Exception " + e.getLocalizedMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void deleteWeather(String city, int days) {
        String selection = WeatherEntry.COLUMN_CITY + " = ? AND " + WeatherEntry.COLUMN_DAYS + " = ?";
        String[] selectionArgs = {city, String.valueOf(days)};
        db.beginTransaction();
        try {
            if (db.delete(WeatherEntry.TABLE_NAME, selection, selectionArgs) > 0)
                db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, TAG + " deleteWeather ->" + " Exception " + e.getLocalizedMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public WeatherResponse getWeather(String city, int days) {
        Cursor cursor = mCursor(city, days);
        WeatherResponse response = null;
        if (cursor.moveToFirst()) {
            long date = cursor.getLong(cursor.getColumnIndex(WeatherEntry.COLUMN_DATE));
            //TODO Check stale data
            if(diffInSec(date) > 60 * 60){
                deleteWeather(city,days);
            } else {
                response = gson.fromJson(cursor.getString(cursor.getColumnIndex(WeatherEntry.COLUMN_DATA)), WeatherResponse.class);
            }
        }
        return response;
    }

    @Override
    public void saveDefaultDays(int days) {
        prefs.edit().putInt(DEFAULT_DAY, days).apply();
    }

    @Override
    public int getDefaultDays() {
        if (prefs.getInt(DEFAULT_DAY, 0) == 0)
            return 3;
        else
            return prefs.getInt(DEFAULT_DAY, 0);
    }

    @Override
    public void saveDefaultCity(String city) {
        prefs.edit().putString(DEFAULT_CITY, city).apply();
    }

    @Override
    public String getDefaultCity() {
        if (prefs.getString(DEFAULT_CITY, null) == null) {
            return getCities().get(0);
        } else
            return prefs.getString(DEFAULT_CITY, null);
    }

    @Override
    public void addCity(String city) {
        ContentValues cv = new ContentValues();
        cv.put(CityEntry.COLUMN_CITY, city);
        db.beginTransaction();
        try {
            if (db.insert(CityEntry.TABLE_NAME, null, cv) != -1)
                db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, TAG + " addCity ->" + " Exception " + e.getLocalizedMessage());
        } finally {
            db.endTransaction();
        }

    }

    @Override
    public ArrayList<String> getCities() {
        ArrayList<String> cityList = null;
        Cursor cursor = db.query(CityEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            cityList = new ArrayList<>();
            int Index = cursor.getColumnIndex(CityEntry.COLUMN_CITY);
            do {
                cityList.add(cursor.getString(Index));
            } while (cursor.moveToNext());
        }
        return cityList;
    }

    private Cursor mCursor(String city, int days) {
        String selection = WeatherEntry.COLUMN_CITY + " = ? AND " + WeatherEntry.COLUMN_DAYS + " = ?";
        String[] selectionArgs = {city, String.valueOf(days)};
        return db.query(WeatherEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    int diffInSec(long date){
        return (int) (System.currentTimeMillis() - date)/1000;
    }

}
