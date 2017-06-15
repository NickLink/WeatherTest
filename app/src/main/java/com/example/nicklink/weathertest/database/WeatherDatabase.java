package com.example.nicklink.weathertest.database;


import com.example.nicklink.weathertest.domain.weather.WeatherResponse;

import java.util.ArrayList;

/**
 * Created by NickLink on 10.06.2017.
 */

public interface WeatherDatabase {
    void saveWeather(String city, int days, WeatherResponse response);
    void deleteWeather(String city, int days);
    WeatherResponse getWeather(String city, int days);

    void saveDefaultDays(int days);
    int getDefaultDays();
    void saveDefaultCity(String city);
    String getDefaultCity();
    void addCity(String city);
    ArrayList<String> getCities();
}
