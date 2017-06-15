package com.example.nicklink.weathertest.domain.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by NickLink on 10.06.2017.
 */

@Getter
@Setter
@NoArgsConstructor
public class WeatherResponse {

    @SerializedName("city")
    @Expose
    public City city;
    @SerializedName("cod")
    @Expose
    public String cod;
    @SerializedName("message")
    @Expose
    public Double message;
    @SerializedName("cnt")
    @Expose
    public Integer cnt;
    @SerializedName("list")
    @Expose
    public List<WeatherItem> list = new ArrayList<WeatherItem>();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
