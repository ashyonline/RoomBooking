package com.codingbad.roombooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayelen on 11/24/15.
 */
public class BookingModel implements Serializable {
    @SerializedName("booking")
    private BookingData mBooking;

    @SerializedName("passes")
    private List<Pass> mPasses;

    public BookingModel(Booking bookingModel) {
        mBooking = new BookingData(bookingModel.getStartDate(), bookingModel.getStartDate(), bookingModel.getEndDate(), bookingModel.getTitle(), bookingModel.getDescription(), bookingModel.getRoom());
        mPasses = new ArrayList<>();

        for (Participant participant : bookingModel.getParticipants()) {
            mPasses.add(new Pass(participant));
        }
    }
}
