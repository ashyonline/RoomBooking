package com.codingbad.roombooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ayelen on 11/24/15.
 */
public class SendPassResult implements Serializable {
    @SerializedName("success")
    private boolean mSuccess;
}
