package com.example.nicklink.weathertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class CitySelectActivity extends AppCompatActivity {
    GridView gvMain;
    String[] data = {"Львів", "Житомир", "Харків", "Одеса", "Херсон", "Миколаїв", "Жмеринка", "Бердянськ", "Суми"};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);

        adapter = new ArrayAdapter<String>(this, R.layout.city_item, R.id.tvText, data);
        gvMain = (GridView) findViewById(R.id.gvMain);
        gvMain.setAdapter(adapter);
        gvMain.setNumColumns(3);
        gvMain.setVerticalSpacing(10);
        gvMain.setHorizontalSpacing(10);

        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                setSelection(position);

            }
        });


    }

    void setSelection(int position) {
        Intent intent = new Intent();
        intent.putExtra("name", adapter.getItem(position));
        setResult(RESULT_OK, intent);
        finish();
    }
}
