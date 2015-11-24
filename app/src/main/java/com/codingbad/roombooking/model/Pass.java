package com.codingbad.roombooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ayelen on 11/24/15.
 */
public class Pass implements Serializable {
    @SerializedName("name")
    private String mName;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("number")
    private String mNumber;

    public Pass(Participant participant) {
        mName = participant.getName();
        mEmail = participant.getEmail();
        mNumber = participant.getPhoneNumber();
    }
}
