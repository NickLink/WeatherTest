package com.example.nicklink.weathertest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nicklink.weathertest.R;
import com.example.nicklink.weathertest.fragments.GoogleData;
import com.example.nicklink.weathertest.utils.Utils;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CitySelectActivity extends AppCompatActivity {

    @BindView(R.id.gvMain)
    GridView gvMain;
    @BindView(R.id.autoText)
    AutoCompleteTextView autoText;
    @BindView(R.id.selectButton)
    Button selectButton;

    public static final String GOOGLE_TAG = "GOOGLE_TAG";
    private String[] data = {"Львів", "Житомир", "Харків", "Одеса", "Херсон", "Миколаїв", "Жмеринка", "Бердянськ", "Суми"};
    private ArrayAdapter<String> adapter;
    private GoogleData gData;
    private ArrayAdapter<String> autoAdapter;
    private String lastSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        ButterKnife.bind(this);

        setGvMain();
        gData = getData();
        autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastSelection = autoAdapter.getItem(position);
                gData.UnSubscribe();
                autoText.dismissDropDown();
                selectButton.setEnabled(true);
            }
        });
        selectButton.setEnabled(false);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelection(lastSelection);
            }
        });

        RxTextView.textChangeEvents(autoText)
                .debounce(400, TimeUnit.MILLISECONDS)
                .map(new Func1<TextViewTextChangeEvent, String>() {
                    @Override
                    public String call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        return textViewTextChangeEvent.text().toString();
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.length() > 2;
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return Utils.isNetworkAvailable(CitySelectActivity.this);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !s.equals(lastSelection);
                    }
                })
                .flatMap(new Func1<String, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(String text) {
                        gData.UnSubscribe();
                        return gData.getObservableGoogle(text)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CitySelectActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(List<String> strings) {
                        selectButton.setEnabled(false);
                        autoAdapter = new ArrayAdapter<String>(CitySelectActivity.this,
                                android.R.layout.simple_dropdown_item_1line, strings);
                        autoText.setAdapter(autoAdapter);
                        autoText.showDropDown();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gData.UnSubscribe();
    }

    private void setGvMain(){
        adapter = new ArrayAdapter<String>(this, R.layout.city_item, R.id.tvText, data);
        gvMain.setAdapter(adapter);
        gvMain.setNumColumns(3);
        gvMain.setVerticalSpacing(10);
        gvMain.setHorizontalSpacing(10);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                setSelection(adapter.getItem(position));
            }
        });
    }

    void setSelection(String city) {
        Intent intent = new Intent();
        intent.putExtra("name", city);
        setResult(RESULT_OK, intent);
        finish();
    }

    private GoogleData getData() {
        GoogleData data = (GoogleData) getSupportFragmentManager().findFragmentByTag(GOOGLE_TAG);
        if (data == null) {
            data = new GoogleData();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(data, GOOGLE_TAG)
                    .commit();
        }
        return data;
    }
}
