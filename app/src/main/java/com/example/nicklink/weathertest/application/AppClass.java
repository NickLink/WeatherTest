package com.example.nicklink.weathertest.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.example.nicklink.weathertest.database.WeatherDatabase;
import com.example.nicklink.weathertest.database.WeatherDatabaseImpl;
import com.example.nicklink.weathertest.domain.weather.WeatherItem;

/**
 * Created by User on 13.06.2017.
 */

public class AppClass extends Application {

    private static AppClass mInstance;
    private static WeatherDatabase dBase;
    private static WeatherItem selectedItem;
    private static ConnectivityManager cm;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        dBase = new WeatherDatabaseImpl(mInstance);
        selectedItem = new WeatherItem();
        cm = (ConnectivityManager) mInstance.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static WeatherDatabase getDB(){
        return dBase;
    }
    public static WeatherItem getSelectedItem() {
        return selectedItem;
    }
    public static ConnectivityManager getCm(){
        return cm;
    }

    public static void setSelectedItem(WeatherItem selectedItem) {
        mInstance.selectedItem = selectedItem;
    }

}
