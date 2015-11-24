package com.codingbad.roombooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ayi on 11/18/15.
 */
public class DateModel implements Serializable {
    @SerializedName("date")
    private String mDate;

    public DateModel(String date) {
        this.mDate = date;
    }
}
