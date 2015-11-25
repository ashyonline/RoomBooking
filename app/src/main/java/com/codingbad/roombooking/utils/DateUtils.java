package com.codingbad.roombooking.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ayelen on 11/20/15.
 */
public class DateUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    public static Date getTime(long millis) {
        return getTime(String.valueOf(millis));
    }

    public static Date getTime(String millis) {

        try {
            return simpleDateFormat.parse(millis);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatMillisecondsIntoTime(long milliSeconds) {
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static Date getDate(long milliSeconds) {
        return getTime(formatMillisecondsIntoTime(milliSeconds));
    }

    public static Date getDate(Date date, String hour, String minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
        calendar.set(Calendar.MINUTE, Integer.valueOf(minutes));
        return calendar.getTime();
    }

    public static int get(int field, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }
}
