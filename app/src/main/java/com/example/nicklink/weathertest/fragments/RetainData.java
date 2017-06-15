package com.example.nicklink.weathertest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.nicklink.weathertest.application.AppClass;
import com.example.nicklink.weathertest.database.WeatherDatabase;
import com.example.nicklink.weathertest.domain.weather.WeatherResponse;
import com.example.nicklink.weathertest.rest.ApiClient;
import com.example.nicklink.weathertest.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import static com.example.nicklink.weathertest.utils.StringUtils.buildWeatherURL;

/**
 * Created by User on 04.05.2017.
 */

public class RetainData extends Fragment {
    private String TAG = RetainData.class.getSimpleName();

    private WeatherResponse weatherResponse;
    private WeatherDatabase database = AppClass.getDB();
    private BehaviorSubject<WeatherResponse> observableResponse;

    public static final String APPID = "5fe568445b7050f344a61cbaa83a1e5a";
    public static final String UNITS = "metric";
    public static final String LANG = "ua";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void loadWeatherData(final String city, final int days, final boolean force) {
        observableResponse = BehaviorSubject.create();
        weatherResponse = getListFromDatabase(city, days);
        if (weatherResponse != null && !force) {
            observableResponse.onNext(weatherResponse);
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<WeatherResponse> call = apiInterface.getWeatherResponce(buildWeatherURL(city, APPID, days, UNITS, LANG));
                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if(response != null) {
                            weatherResponse = response.body();
                            saveInDatabase(city, days, weatherResponse);
                            observableResponse.onNext(weatherResponse);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        observableResponse.onError(t);
                        Log.e(TAG, "Error " + t.toString());
                    }
                });
        }
    }

    private WeatherResponse getListFromDatabase(String city, int days) {
        if (database != null)
            return database.getWeather(city, days);
        else return null;
    }

    private void saveInDatabase(String city, int days, WeatherResponse response) {
        database.saveWeather(city, days, response);
    }

    public void deleteFromDatabase(String city, int days) {
        database.deleteWeather(city, days);
    }

    public Observable<WeatherResponse> getObservableResponse(String city, int days, boolean force) {
        if (observableResponse == null) {
            loadWeatherData(city, days, force);
        }
        return observableResponse;
    }

    public void UnSubscribe() {
        if (observableResponse != null && !observableResponse.subscribe().isUnsubscribed()) {
            observableResponse.subscribe().unsubscribe();
            observableResponse = null;
        }
    }

}
