package com.codingbad.roombooking.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Availability;
import com.codingbad.roombooking.model.Booking;
import com.codingbad.roombooking.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by ayi on 11/23/15.
 */
public class BookRoomDateFragment extends AbstractFragment<BookRoomDateFragment.Callbacks> implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static final String DATE = "date";
    public static final String START = "start_date";
    private Booking mBookingModel;

    @InjectView(R.id.fragment_room_booking_date_title)
    private TextView mTitle;

    @InjectView(R.id.fragment_room_booking_date_subtitle)
    private TextView mSubTitle;

    @InjectView(R.id.fragment_room_booking_date_hour)
    private Spinner mHour;

    @InjectView(R.id.fragment_room_booking_date_minute)
    private Spinner mMinutes;

    @InjectView(R.id.fragment_room_booking_date_button)
    private Button mNext;
    private Date mDate;
    private Boolean mIsStart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(DATE);
        mIsStart = getArguments().getBoolean(START);
        return inflater.inflate(R.layout.fragment_room_booking_date, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBookingModel = callbacks.getBookingModel();

        mNext.setOnClickListener(this);
        setupTitle();
        setupTimes();
    }

    private void setupTimes() {
        List<String> availableHours;
        List<String> availableMinutes;
        if (mIsStart) {
            availableHours = getAvailableHours();
            availableMinutes = getAvailableMinutes(availableHours.get(0));
        } else {
            Date start = mBookingModel.getStartDate();
            int hour = DateUtils.get(Calendar.HOUR_OF_DAY, start);
            int minutes = DateUtils.get(Calendar.MINUTE, start);
            String hours = toString(hour);
            availableHours = getAvailableHoursFrom(hours, toString(minutes));
            String firstHour = availableHours.get(0);
            if (firstHour.equals(hours)) {
                availableMinutes = getAvailableMinutesFrom(firstHour, toString(minutes));
            } else {
                availableMinutes = getAvailableMinutesFrom(firstHour, toString(-1));
            }
        }

        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, availableHours);
        mHour.setAdapter(hourAdapter);
        mHour.setOnItemSelectedListener(this);
        ArrayAdapter<String> minutesAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, availableMinutes);
        mMinutes.setAdapter(minutesAdapter);
    }

    private String toString(int i) {
        if (i < 10 && i >= 0) {
            return "0" + i;
        }
        return String.valueOf(i);
    }

    private List<String> getAvailableHoursFrom(String hour, String minutes) {
        List<String> availableHours = new ArrayList<>();
        for (Availability av : mBookingModel.getRoomAvailability()) {
            if (av.getAvailableHours().contains(hour)) {
                availableHours.addAll(av.getAvailableHoursFrom(hour));
                if (availableHours.get(0).equals(hour) && minutes.equals("45")) {
                    availableHours.remove(0);
                }
            }
        }

        return availableHours;
    }
    private List<String> getAvailableHours() {
        List<String> availableHours = new ArrayList<>();
        for (Availability av : mBookingModel.getRoomAvailability()) {
            availableHours.addAll(av.getAvailableHours());
        }

        return availableHours;
    }

    private List<String> getAvailableMinutes(String hour) {
        List<String> availableMinutes = new ArrayList<>();
        for (Availability av : mBookingModel.getRoomAvailability()) {
            if (av.getAvailableHours().contains(hour)) {
                availableMinutes.addAll(av.getAvailableMinutes(hour));
            }
        }

        return availableMinutes;
    }

    private List<String> getAvailableMinutesFrom(String hour, String minutes) {
        List<String> availableMinutes = new ArrayList<>();
        for (Availability av : mBookingModel.getRoomAvailability()) {
            if (av.getAvailableHours().contains(hour)) {
                availableMinutes.addAll(av.getAvailableMinutesFrom(hour, minutes));
            }
        }

        return availableMinutes;
    }

    private void setupTitle() {
        if (mIsStart) {
            mTitle.setText(getString(R.string.booking_room_from_date, mBookingModel.getRoomName(getContext())));
        } else {
            mTitle.setText(getString(R.string.booking_room_to_date, mBookingModel.getRoomName(getContext())));
        }

        mSubTitle.setText(mDate.toString());
    }

    @Override
    public void onClick(View v) {
        // DateModel chosen by user
        Date date = DateUtils.getDate(mDate, mHour.getSelectedItem().toString(), mMinutes.getSelectedItem().toString());
        callbacks.onNextPressed(date);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        List<String> availableMinutes;
        String firstHour = (String) mHour.getSelectedItem();
        if (mIsStart) {
            availableMinutes = getAvailableMinutes(firstHour);
        } else {
            Date start = mBookingModel.getStartDate();
            int minutes = DateUtils.get(Calendar.MINUTE, start);
            int hour = DateUtils.get(Calendar.HOUR_OF_DAY, start);
            String hours = toString(hour);

            if (firstHour.equals(hours)) {
                availableMinutes = getAvailableMinutesFrom(firstHour, toString(minutes));
            } else {
                availableMinutes = getAvailableMinutesFrom(firstHour, toString(-1));
            }

        }

        ArrayAdapter<String> minutesAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, availableMinutes);
        mMinutes.setAdapter(minutesAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean isStart() {
        return mIsStart;
    }

    public interface Callbacks {
        void onNextPressed(Date date);

        Booking getBookingModel();
    }
}
