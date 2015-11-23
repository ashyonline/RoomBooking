package com.codingbad.roombooking.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayelen on 11/23/15.
 */
public class BookingModel {
    private List<Participant> mParticipantsNames = new ArrayList<>();

    public List<Participant> getParticipants() {
        return mParticipantsNames;
    }

    public void addParticipant(Participant participant) {
        mParticipantsNames.add(participant);
    }
}
