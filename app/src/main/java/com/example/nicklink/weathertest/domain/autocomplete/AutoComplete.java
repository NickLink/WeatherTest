package com.example.nicklink.weathertest.domain.autocomplete;

/**
 * Created by User on 14.06.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutoComplete {

    @SerializedName("predictions")
    @Expose
    public List<Prediction> predictions = new ArrayList<Prediction>();
    @SerializedName("status")
    @Expose
    public String status;
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}