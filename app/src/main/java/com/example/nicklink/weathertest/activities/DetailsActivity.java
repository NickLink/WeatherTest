package com.example.nicklink.weathertest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicklink.weathertest.R;
import com.example.nicklink.weathertest.utils.StringUtils;
import com.example.nicklink.weathertest.application.AppClass;
import com.example.nicklink.weathertest.domain.weather.WeatherItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.nicklink.weathertest.database.DatabaseContract.CITY;

public class DetailsActivity extends AppCompatActivity {
    private WeatherItem item;

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.temperature_day)
    TextView temperature_day;
    @BindView(R.id.temperature_night)
    TextView temperature_night;
    @BindView(R.id.temp_m_data)
    TextView temp_m_data;
    @BindView(R.id.temp_e_data)
    TextView temp_e_data;
    @BindView(R.id.pressure_data)
    TextView pressure_data;
    @BindView(R.id.humidity_data)
    TextView humidity_data;
    @BindView(R.id.windspeed_data)
    TextView windspeed_data;
    @BindView(R.id.rain_data)
    TextView rain_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        item = AppClass.getSelectedItem();
        if (item == null) finish();
        setData();
        Intent intent = getIntent();
        String city_title = intent.getStringExtra(CITY);
        if (city_title != null)
            getSupportActionBar().setTitle(city_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setData() {
        date.setText(StringUtils.getDateFromMillis(true, (long) item.getDt()));
        title.setText(item.getWeather().get(0).getDescription());
        temperature_day.setText(StringUtils.getTemp(item.getTemp().getDay()));
        temperature_night.setText(StringUtils.getTemp(item.getTemp().getNight()));
        Picasso.with(this).load("http://openweathermap.org/img/w/" + item.getWeather().get(0).getIcon() + ".png")
                .resize(64, 64)
                .into(imageView);
        temp_m_data.setText(StringUtils.getTemp(item.getTemp().getMorn()));
        temp_e_data.setText(StringUtils.getTemp(item.getTemp().getEve()));
        pressure_data.setText(String.valueOf(item.getPressure()));
        humidity_data.setText(String.valueOf(item.getHumidity()));
        windspeed_data.setText(String.valueOf(item.getSpeed()));
        rain_data.setText(item.getRain() != null ? String.valueOf(item.getRain()) : getString(R.string.no_rain_chance));
    }
}
