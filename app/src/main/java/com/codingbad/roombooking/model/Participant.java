package com.codingbad.roombooking.model;

/**
 * Created by ayelen on 11/23/15.
 */
public class Participant {
    private final String mName;
    private final String mEmail;
    private final String mPhone;

    public Participant(String name, String email, String phone) {
        mName = name;
        mEmail = email;
        mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhoneNumber() {
        return mPhone;
    }
}
