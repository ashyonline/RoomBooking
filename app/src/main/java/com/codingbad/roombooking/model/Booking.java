package com.codingbad.roombooking.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ayelen on 11/23/15.
 */
public class Booking {
    private final Room mRoom;
    private List<Participant> mParticipantsNames = new ArrayList<>();
    private Date mStartDate;
    private Date mEndDate;
    private String mTitle;
    private String mDescription;

    public Booking(Room room) {
        mRoom = room;
    }

    public List<Participant> getParticipants() {
        return mParticipantsNames;
    }

    public void addParticipant(Participant participant) {
        mParticipantsNames.add(participant);
    }

    public void setStart(Date date) {
        mStartDate = date;
    }

    public boolean hasStartDate() {
        return mStartDate != null;
    }

    public void setEnd(Date date) {
        mEndDate = date;
    }

    public String getRoomName(Context context) {
        return mRoom.getName(context);
    }

    public List<Availability> getRoomAvailability() {
        return mRoom.getAvailability();
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public String getRoom() {
        return mRoom.getRawName();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setEventTitle(String title) {
        mTitle = title;
    }

    public void setEventDescription(String description) {
        mDescription = description;
    }
}
