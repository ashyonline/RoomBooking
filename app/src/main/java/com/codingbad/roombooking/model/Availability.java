package com.codingbad.roombooking.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by ayelen on 11/19/15.
 */
public class Availability {

    private final String from;
    private final String to;
    private Date mFromTime;
    private Date mToTime;

    public Availability(String availability) {
        String[] split = availability.split(" - ");
        from = split[0];
        to = split[1];

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            mFromTime = simpleDateFormat.parse(from);
            mToTime = simpleDateFormat.parse(to);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean includes(Date date) {
        return (mFromTime.before(date) || mFromTime.equals(date)) && mToTime.after(date);
    }

    public String getFromHour() {
        return from.split(":")[0];
    }

    public String getToHours() {
        return to.split(":")[0];
    }

    public String getFromMinutes() {
        return from.split(":")[1];
    }

    public String getToMinutes() {
        return to.split(":")[1];
    }

    public List<String> getAvailableHours() {
        List<String> available = new ArrayList<>();
        int from = Integer.valueOf(this.from.split(":")[0]);
        int to = Integer.valueOf(this.to.split(":")[0]);

        if (Integer.valueOf(this.to.split(":")[1]) > 0) {
            to = to+1;
        }
        for (int i = from ; i < to; i++) {
            available.add(toString(i));
        }
        return available;
    }

    private String toString(int i) {
        if (i < 10) {
            return "0" + i;
        }
        return String.valueOf(i);
    }

    public List<String> getAvailableMinutes(String hour) {
        List<String> available = new ArrayList<>();
        int from;

        if (Integer.valueOf(this.from.split(":")[0]) < Integer.valueOf(hour)) {
            from = 0;
        } else {
            from = Integer.valueOf(this.from.split(":")[1]);
        }

        int to;
        if (Integer.valueOf(this.to.split(":")[0]) > Integer.valueOf(hour)) {
            to = 59;
        } else {
            to = Integer.valueOf(this.to.split(":")[1]);
        }

        for (int i = from ; i < to; i+=15) {
            available.add(toString(i));
        }
        return available;
    }

    public List<String> getAvailableHoursFrom(String hour) {
        List<String> available = new ArrayList<>();
        int from = Integer.valueOf(hour);
        int to = Integer.valueOf(this.to.split(":")[0]);

        if (Integer.valueOf(this.to.split(":")[1]) > 0) {
            to = to+1;
        }
        for (int i = from ; i < to; i++) {
            available.add(toString(i));
        }
        return available;
    }
}
