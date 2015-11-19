package com.codingbad.roombooking.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ayelen on 11/19/15.
 */
public class Availability {

    private Date mFromTime;
    private Date mToTime;

    public Availability(String availability) {
        String[] split = availability.split(" - ");
        String from = split[0];
        String to = split[1];

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            mFromTime = simpleDateFormat.parse(from);
            mToTime = simpleDateFormat.parse(to);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean includes(Date date) {
        return mFromTime.before(date) && mToTime.after(date);
    }
}
