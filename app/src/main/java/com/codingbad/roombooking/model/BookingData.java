package com.codingbad.roombooking.model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ayelen on 11/24/15.
 */
public class BookingData {
    @SerializedName("date")
    private DateModel mDate;

    @SerializedName("time_start")
    private DateModel mTimeStart;

    @SerializedName("time_end")
    private DateModel mTimeEnd;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("room")
    private String mRoom;

    public BookingData(Date date, Date startDate, Date endDate, String title, String description, String room) {
        mDate = new DateModel(getDate(date));
        mTimeStart = new DateModel(getDate(startDate));
        mTimeEnd = new DateModel(getDate(endDate));
        mTitle = title;
        mDescription = description;
        mRoom = room;
    }

    protected String getDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.getTimeInMillis() / 1000);
    }
}
