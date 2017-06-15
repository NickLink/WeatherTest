package com.example.nicklink.weathertest.presenters;

import android.widget.ArrayAdapter;

import com.example.nicklink.weathertest.adapters.WeatherAdapter;

/**
 * Created by User on 13.06.2017.
 */

public interface MainView {
    void toggleDrawer(int toggle);
    void setCityAdapter(ArrayAdapter<String> adapter);
    void setWeatherAdapter(WeatherAdapter adapter);
    void setToolBarTitle(String title);

    void showLoadingIndicator();
    void hideLoadingIndicator();
    void setCitySelected(int position);
    void setDaysSelected(int id);

    void showError(String message);
}
