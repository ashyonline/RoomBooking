package com.codingbad.roombooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ayi on 11/25/15.
 */
public class SendPassErrorBody implements Serializable {
    @SerializedName("text")
    private String mText;
    @SerializedName("index")
    private int mIndex;
    @SerializedName("code")
    private int mCode;

    public int getCode() {
        return mCode;
    }

    public String getMessage() {
        return mText;
    }
}
