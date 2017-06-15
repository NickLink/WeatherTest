package com.example.nicklink.weathertest.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NickLink on 10.06.2017.
 */

public class ApiClient {

    public static final String W_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily";
    public static final String G_BASE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
    public static final String BASE_URL = "http://google.com";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
