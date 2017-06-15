package com.example.nicklink.weathertest.database;

import android.provider.BaseColumns;

/**
 * Created by NickLink on 10.06.2017.
 */

public class DatabaseContract {

    public static final String DEFAULT_DAY = "DEFAULT_DAY";
    public static final String DEFAULT_CITY = "DEFAULT_CITY";
    public static final String SAVED_CITIES = "SAVED_CITIES";
    public static final String RETAIN_TAG = "RETAIN_TAG";
    public static final String CITY = "CITY";

    public static final class WeatherEntry implements BaseColumns {
        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_DAYS = "days";
        public static final String COLUMN_DATA = "data";
    }

    public static final class CityEntry implements BaseColumns {
        public static final String TABLE_NAME = "cities";
        public static final String COLUMN_CITY = "city";
    }

}
