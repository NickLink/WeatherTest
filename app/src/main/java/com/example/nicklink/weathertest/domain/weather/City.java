package com.example.nicklink.weathertest.domain.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by NickLink on 10.06.2017.
 */

@Getter
@Setter
@NoArgsConstructor
public class City {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("coord")
    @Expose
    public Coord coord;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("population")
    @Expose
    public Integer population;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}