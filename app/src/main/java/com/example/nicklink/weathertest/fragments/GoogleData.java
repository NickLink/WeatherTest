package com.example.nicklink.weathertest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.nicklink.weathertest.domain.autocomplete.AutoComplete;
import com.example.nicklink.weathertest.rest.ApiClient;
import com.example.nicklink.weathertest.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import static com.example.nicklink.weathertest.utils.StringUtils.buildGoogleURL;
import static com.example.nicklink.weathertest.utils.StringUtils.getList;

/**
 * Created by User on 15.06.2017.
 */

public class GoogleData extends Fragment {

    private BehaviorSubject<List<String>> observableGoogle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public Observable<List<String>> getObservableGoogle(String input) {
        if (observableGoogle == null) {
            loadGoogleData(input);
        }
        return observableGoogle;
    }

    public void loadGoogleData(String input) {
        observableGoogle = BehaviorSubject.create();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AutoComplete> call = apiInterface.getGoogleResponce(buildGoogleURL(input));
        call.enqueue(new Callback<AutoComplete>() {
            @Override
            public void onResponse(Call<AutoComplete> call, Response<AutoComplete> response) {
                if (response != null)
                    observableGoogle.onNext(getList(response.body()));
            }

            @Override
            public void onFailure(Call<AutoComplete> call, Throwable t) {
                observableGoogle.onError(t);
            }
        });
    }

    public void UnSubscribe() {
        if (observableGoogle != null && !observableGoogle.subscribe().isUnsubscribed()) {
            observableGoogle.subscribe().unsubscribe();
            observableGoogle = null;
        }
    }
}
