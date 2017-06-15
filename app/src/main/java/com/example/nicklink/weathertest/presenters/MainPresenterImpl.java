package com.example.nicklink.weathertest.presenters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;

import com.example.nicklink.weathertest.R;
import com.example.nicklink.weathertest.activities.CitySelectActivity;
import com.example.nicklink.weathertest.activities.DetailsActivity;
import com.example.nicklink.weathertest.activities.SettingsActivity;
import com.example.nicklink.weathertest.adapters.WeatherAdapter;
import com.example.nicklink.weathertest.application.AppClass;
import com.example.nicklink.weathertest.database.WeatherDatabase;
import com.example.nicklink.weathertest.domain.weather.WeatherItem;
import com.example.nicklink.weathertest.domain.weather.WeatherResponse;
import com.example.nicklink.weathertest.fragments.RetainData;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.nicklink.weathertest.database.DatabaseContract.CITY;
import static com.example.nicklink.weathertest.database.DatabaseContract.RETAIN_TAG;
import static com.example.nicklink.weathertest.utils.Utils.isNetworkAvailable;

/**
 * Created by User on 13.06.2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private MainView mainView;
    private ArrayList<String> cityData = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private AppCompatActivity activity;
    private RetainData retainData;
    private List<WeatherItem> itemList;
    private WeatherDatabase db = AppClass.getDB();

    private int selectedCityPosition;
    private int selectedDaysPosition;

    public MainPresenterImpl(MainView view) {
        mainView = view;
        activity = (AppCompatActivity) mainView;
        retainData = getRetainData();

        cityData = getCities();
        adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_checked, cityData);
        mainView.setCityAdapter(adapter);

        selectedCityPosition = getDefaultCityPosition();
        selectedDaysPosition = getDaysDefaultId();

        mainView.setCitySelected(getDefaultCityPosition());
        mainView.setDaysSelected(selectedDaysPosition);
        loadData(adapter.getItem(selectedCityPosition), selectedDaysPosition, false);
    }

    void loadData(String s, int d, boolean force) {
        mainView.showLoadingIndicator();
        if(isNetworkAvailable(activity)) {
            retainData.UnSubscribe();
            retainData.getObservableResponse(s, d, force)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WeatherResponse>() {
                        @Override
                        public void onCompleted() {
                            Log.e(TAG, " -> onCompleted ");
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            getRetainData().UnSubscribe();
                            mainView.hideLoadingIndicator();
                            //TODO show error case in main Acttivity
                            mainView.showError("Loading error -> " + throwable.getLocalizedMessage());
                        }

                        @Override
                        public void onNext(WeatherResponse response) {
                            itemList = response.getList();
                            mainView.setWeatherAdapter(new WeatherAdapter(itemList, activity));
                            mainView.setToolBarTitle(adapter.getItem(selectedCityPosition));
                            mainView.hideLoadingIndicator();
                        }
                    });
        } else {
            mainView.hideLoadingIndicator();
            mainView.showError("No network!!!");
        }
    }

    @Override
    public void onCitySelected(int position) {
        if (position == (adapter.getCount() - 1)) {
            //Add more city
            Intent intent = new Intent(activity, CitySelectActivity.class);
            activity.startActivityForResult(intent, 1);
        } else {
            //Call RetainData loading data
            selectedCityPosition = position;
            mainView.toggleDrawer(Gravity.LEFT);
            mainView.setToolBarTitle(adapter.getItem(selectedCityPosition));
            db.saveDefaultCity(adapter.getItem(selectedCityPosition));
            loadData(adapter.getItem(selectedCityPosition), selectedDaysPosition, false);
        }
    }

    @Override
    public void onDaysSelected(int days) {
        //TODO Set days count
        switch (days) {
            case R.id.radio_7:
                selectedDaysPosition = 7;
                break;
            case R.id.radio_3:
                selectedDaysPosition = 3;
                break;
        }
        db.saveDefaultDays(selectedDaysPosition);
        mainView.toggleDrawer(Gravity.LEFT);
        loadData(adapter.getItem(selectedCityPosition), selectedDaysPosition, false);
    }

    @Override
    public void addCity(String city) {
        db.addCity(city);
        cityData.add(cityData.size() - 1, city);
        adapter.notifyDataSetChanged();
        onCitySelected(adapter.getCount()-2);
    }

    @Override
    public void showDetailInfo(int position) {
        //TODO Cast Detail Activity
        AppClass.setSelectedItem(itemList.get(position));
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(CITY, adapter.getItem(selectedCityPosition));
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        activity.startActivity(intent);
    }

    @Override
    public void showSettings() {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        activity.startActivity(intent);
    }

    @Override
    public void refreshData() {
        loadData(db.getDefaultCity(), db.getDefaultDays(), true);
    }

    @Override
    public void onDestroy() {
        getRetainData().UnSubscribe();
    }

    private ArrayList<String> getCities() {
        ArrayList<String> cityList = db.getCities();
        cityList.add(activity.getResources().getStringArray(R.array.city_array)
                [activity.getResources().getStringArray(R.array.city_array).length-1]);
        return cityList;
    }

    private RetainData getRetainData() {
        retainData = (RetainData) activity.getSupportFragmentManager().findFragmentByTag(RETAIN_TAG);
        if (retainData == null) {
            retainData = new RetainData();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(retainData, RETAIN_TAG)
                    .commit();
        }
        return retainData;
    }

    private int getDefaultCityPosition(){
        int position = 0;
        if(db.getDefaultCity() != null){
            position = cityData.indexOf(db.getDefaultCity());
        }
        return position;
    }

    private int getDaysDefaultId(){
        return db.getDefaultDays();
    }


}
