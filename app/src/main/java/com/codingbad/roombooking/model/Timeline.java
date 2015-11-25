package com.codingbad.roombooking.model;

import com.codingbad.roombooking.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ayelen on 11/20/15.
 */
public class Timeline {

    private final List<Availability> mAvailability;
    private List<Period> mPeriods = new ArrayList<>();
    private Date mFromTime;
    private Date mToTime;

    // step is 15 mins: 15 * 60 * 1000
    public Timeline(String from, String to, Room room, int step) {
        mFromTime = DateUtils.getTime(from);
        mToTime = DateUtils.getTime(to);
        mAvailability = room.getAvailability();

        int totalSteps = Math.round(mToTime.getTime() - mFromTime.getTime()) / step;
        long beginning = mFromTime.getTime();
        for (int i = 0; i < totalSteps; i++) {
            mPeriods.add(new Period(DateUtils.getDate(beginning), DateUtils.getDate(beginning + step), mAvailability));
            beginning = beginning + step;
        }
    }


    public List<Period> getPeriods() {
        return mPeriods;
    }
}
