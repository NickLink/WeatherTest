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
public class WeatherItem {

    @SerializedName("dt")
    @Expose
    public Integer dt;
    @SerializedName("temp")
    @Expose
    public Temp temp;
    @SerializedName("pressure")
    @Expose
    public Double pressure;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    @SerializedName("weather")
    @Expose
    public List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("speed")
    @Expose
    public Double speed;
    @SerializedName("deg")
    @Expose
    public Integer deg;
    @SerializedName("clouds")
    @Expose
    public Integer clouds;
    @SerializedName("rain")
    @Expose
    public Double rain;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
