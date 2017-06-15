package com.example.nicklink.weathertest.utils;

import com.example.nicklink.weathertest.domain.autocomplete.AutoComplete;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.nicklink.weathertest.rest.ApiClient.G_BASE_URL;
import static com.example.nicklink.weathertest.rest.ApiClient.W_BASE_URL;

/**
 * Created by NickLink on 13.06.2017.
 */

public class StringUtils {
    public static String getDateFromMillis(boolean top, long millis){
        String date_string = "";
        try{
            String format = "dd-MM";
            if(top){
                format = "dd-MM-yyyy"; //For top item in list
            }
            SimpleDateFormat f = new SimpleDateFormat(format);
            Date date = (new Date(millis * 1000));
            date_string = f.format(date);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return date_string;
    }

    public static String getTemp(Double inValue){
        String temp = "";
        if(inValue.intValue() > 0){
            temp = "+" + String.valueOf(inValue.intValue());
        } else if(inValue.intValue() < 0){
            temp = "-" + String.valueOf(inValue.intValue());
        } else {
            temp = String.valueOf(inValue.intValue());
        }
        return temp + (char)0x00B0;
    }

    public static String buildWeatherURL(
            String city,
            String APPID,
            int days,
            String UNITS,
            String LANG
    ) {
        StringBuilder sb = new StringBuilder(W_BASE_URL);
        try {
            sb.append("?q=" + URLEncoder.encode(city, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&appid=" + APPID);
        sb.append("&cnt=" + days);
        sb.append("&units=" + UNITS);
        sb.append("&lang=" + LANG);
        return sb.toString();
    }

    public static String buildGoogleURL(
            String input
    ) {
        StringBuilder sb = new StringBuilder(G_BASE_URL);
        try {
            sb.append("?input=" + URLEncoder.encode(input, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&types=(cities)");
        sb.append("&key=AIzaSyA6--Fd9KeRWcE7nTdEKrk_iaFNNniYbCs");
        return sb.toString();
    }

    public static List<String> getList(AutoComplete autoComplete) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < autoComplete.getPredictions().size(); i++) {
            list.add(autoComplete.getPredictions().get(i).getStructuredFormatting().getMainText()
                    //  + " " + autoComplete.getPredictions().get(i).getStructuredFormatting().getSecondaryText()
            );
        }
        return list;
    }
}
