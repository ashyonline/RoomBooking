package com.codingbad.roombooking.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayi on 11/18/15.
 */
public class RoomsErrorModel {
    @SerializedName("error")
    private RoomsError mError;

    public String getDescription() {
        return mError.getDescription();
    }

    public String getCode() {
        return mError.getCode();
    }
}
