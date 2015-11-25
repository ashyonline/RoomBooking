package com.codingbad.roombooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ayi on 11/25/15.
 */
public class SendPassError implements Serializable {
    @SerializedName("error")
    SendPassErrorBody mError;

    public int getCode() {
        return mError.getCode();
    }

    public String getMessage() {
        return mError.getMessage();
    }
}
