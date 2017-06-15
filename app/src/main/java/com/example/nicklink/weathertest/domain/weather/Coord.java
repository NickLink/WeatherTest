package com.example.nicklink.weathertest.domain.weather;

/**
 * Created by NickLink on 10.06.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Coord {

    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lon")
    @Expose
    public Double lon;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}