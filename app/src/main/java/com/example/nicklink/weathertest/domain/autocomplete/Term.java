package com.example.nicklink.weathertest.domain.autocomplete;

/**
 * Created by User on 14.06.2017.
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
public class Term {

    @SerializedName("offset")
    @Expose
    public Integer offset;
    @SerializedName("value")
    @Expose
    public String value;
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}