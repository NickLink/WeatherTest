package com.example.nicklink.weathertest.domain.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by NickLink on 12.06.2017.
 */

@Getter
@Setter
@NoArgsConstructor
public class Temp {

    @SerializedName("day")
    @Expose
    public Double day;
    @SerializedName("min")
    @Expose
    public Double min;
    @SerializedName("max")
    @Expose
    public Double max;
    @SerializedName("night")
    @Expose
    public Double night;
    @SerializedName("eve")
    @Expose
    public Double eve;
    @SerializedName("morn")
    @Expose
    public Double morn;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
