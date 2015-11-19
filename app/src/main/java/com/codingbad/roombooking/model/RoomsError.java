package com.codingbad.roombooking.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayi on 11/18/15.
 */
public class RoomsError {
    @SerializedName("text")
    private String mDescription;
    @SerializedName("code")
    private String mCode;

    public String getDescription() {
        return mDescription;
    }

    public String getCode() {
        return mCode;
    }
}
