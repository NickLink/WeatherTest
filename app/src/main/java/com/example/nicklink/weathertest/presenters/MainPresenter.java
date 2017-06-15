package com.example.nicklink.weathertest.presenters;

/**
 * Created by User on 13.06.2017.
 */

public interface MainPresenter {
    void onCitySelected(int cityPosition);
    void onDaysSelected(int days);
    void addCity(String city);
    void showDetailInfo(int position);
    void showSettings();
    void refreshData();
    void onDestroy();
}
