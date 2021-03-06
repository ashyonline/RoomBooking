package com.codingbad.roombooking.model;

import com.codingbad.roombooking.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ayelen on 11/20/15.
 */
public class Period {
    private Date mFromTime;
    private Date mToTime;
    private List<Availability> mAvailabilities;
    private boolean mIsAvailable;
    private boolean mToBook;

    public Period(Date from, Date to, List<Availability> availability) {
        mFromTime = from;
        mAvailabilities = availability;
        mToTime = to;

        mIsAvailable = setupAvailability();
    }

    public boolean setupAvailability() {
        for (Availability availability : mAvailabilities) {
            if (availability.includes(mFromTime)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAvailable() {
        return mIsAvailable;
    }

    public boolean isStart() {
        return mFromTime.getMinutes() == 0;
    }

    public String fromDate() {
        return DateUtils.formatMillisecondsIntoTime(mFromTime.getTime());
    }

    public void markToBook(boolean toBook) {
        mToBook = toBook;
    }
}
