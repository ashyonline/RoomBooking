package com.codingbad.roombooking.model;

import com.codingbad.roombooking.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by ayelen on 11/20/15.
 */
public class Period {
    private Date mFromTime;
    private Date mToTime;
    private List<Availability> mAvailabilities;
    private boolean mIsAvailable;

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
}
