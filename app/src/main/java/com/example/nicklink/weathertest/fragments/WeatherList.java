package com.example.nicklink.weathertest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicklink.weathertest.R;

/**
 * Created by User on 13.06.2017.
 */

public class WeatherList extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipe_container;

    public WeatherList(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weather_list_fragment, container, false);
        swipe_container = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                topRatedPresenter.deleteData();
//                topRatedPresenter.loadWeatherData(getActivity());
//                swipe_container.setRefreshing(false);
//            }
//        });
        return v;
    }


}
