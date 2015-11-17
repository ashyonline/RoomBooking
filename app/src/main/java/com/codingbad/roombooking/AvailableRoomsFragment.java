package com.codingbad.roombooking;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import roboguice.inject.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class AvailableRoomsFragment extends AbstractFragment<AvailableRoomsFragment.Callbacks> implements View.OnClickListener {

    @InjectView(R.id.fragment_available_rooms_current_date)
    private TextView mCurrentDate;

    @InjectView(R.id.fragment_available_rooms_recyclerview)
    private RecyclerView mAvailableRooms;

    Calendar mCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener mDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    public AvailableRoomsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_available_rooms, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupDate();
    }

    private void updateLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, getResources().getConfiguration().locale);

        mCurrentDate.setText(sdf.format(mCalendar.getTime()));
    }

    private void setupDate() {
        // TODO: save last selected mDate
        showDate(new Date());
        mCurrentDate.setOnClickListener(this);
    }

    private void showDate(Date date) {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(date);
        mCurrentDate.setText(currentDateTimeString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_available_rooms_current_date:
                new DatePickerDialog(AvailableRoomsFragment.this.getActivity(), mDate, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    public interface Callbacks {
    }
}
