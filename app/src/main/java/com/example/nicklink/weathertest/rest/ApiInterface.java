package com.example.nicklink.weathertest.rest;

import com.example.nicklink.weathertest.domain.autocomplete.AutoComplete;
import com.example.nicklink.weathertest.domain.weather.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by NickLink on 10.06.2017.
 */

public interface ApiInterface {
    @GET
    Call<WeatherResponse> getWeatherResponce(@Url String url);

    @GET
    Call<AutoComplete> getGoogleResponce(@Url String url);

}
