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
public class Prediction {

    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("matched_substrings")
    @Expose
    public List<MatchedSubstring> matchedSubstrings = new ArrayList<MatchedSubstring>();
    @SerializedName("place_id")
    @Expose
    public String placeId;
    @SerializedName("reference")
    @Expose
    public String reference;
    @SerializedName("structured_formatting")
    @Expose
    public StructuredFormatting structuredFormatting;
    @SerializedName("terms")
    @Expose
    public List<Term> terms = new ArrayList<Term>();
    @SerializedName("types")
    @Expose
    public List<String> types = new ArrayList<String>();
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}