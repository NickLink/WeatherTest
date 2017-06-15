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
public class StructuredFormatting {

    @SerializedName("main_text")
    @Expose
    public String mainText;
    @SerializedName("main_text_matched_substrings")
    @Expose
    public List<MainTextMatchedSubstring> mainTextMatchedSubstrings = new ArrayList<MainTextMatchedSubstring>();
    @SerializedName("secondary_text")
    @Expose
    public String secondaryText;
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}