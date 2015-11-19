package com.codingbad.roombooking.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Room;
import com.codingbad.roombooking.otto.OttoBus;
import com.codingbad.roombooking.task.GetRoomsTask;
import com.codingbad.roombooking.ui.adapter.RoomsAdapter;
import com.codingbad.roombooking.ui.view.LoadingIndicatorView;
import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.inject.InjectView;

public class AvailableRoomsFragment extends AbstractFragment<AvailableRoomsFragment.Callbacks> implements View.OnClickListener, RoomsAdapter.RecyclerViewListener {

    private static final String DATE = "date";
    @InjectView(R.id.fragment_available_rooms_current_date)
    private TextView mCurrentDate;

    @InjectView(R.id.fragment_available_rooms_recyclerview)
    private RecyclerView mAvailableRooms;

    @InjectView(R.id.fragment_available_rooms_loading_group)
    private LinearLayout mViewGroup;

    private Calendar mCalendar = Calendar.getInstance();
    private RoomsAdapter mRoomsAdapter;

    @Inject
    OttoBus ottoBus;
    @Inject
    private LoadingIndicatorView mLoadingIndicator;

    private Date mSelectedDate;

    DatePickerDialog.OnDateSetListener mDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mSelectedDate = mCalendar.getTime();
            updateLabel();
            getAvailableRooms();
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

        if (savedInstanceState != null) {
            mSelectedDate = (Date) savedInstanceState.getSerializable(DATE);
        }

        if (mSelectedDate == null) {
            mSelectedDate = mCalendar.getTime();
            getAvailableRooms();
        }

        setupDate();
        setupLoadingIndicator();
        setUpRooms();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSelectedDate != null) {
            outState.putSerializable(DATE, mSelectedDate);
        }
    }

    private void setupLoadingIndicator() {
        mLoadingIndicator.attach(mViewGroup);
    }


    @Override
    public void onStart() {
        super.onStart();
        ottoBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ottoBus.unregister(this);
        mLoadingIndicator.dismiss();
        mViewGroup.removeView(mLoadingIndicator);
    }

    private void setUpRooms() {
        mAvailableRooms.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        mAvailableRooms.setLayoutManager(layoutManager);

        mRoomsAdapter = new RoomsAdapter(this);
        mRoomsAdapter.addItemList(callbacks.getRooms());
        mAvailableRooms.setAdapter(mRoomsAdapter);
    }

    private void getAvailableRooms() {
        mLoadingIndicator.show();
        new GetRoomsTask(getContext(), String.valueOf(mCalendar.getTimeInMillis()/1000)).execute();
    }

    private void updateLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, getResources().getConfiguration().locale);
        mCurrentDate.setText(sdf.format(mSelectedDate));
    }

    private void setupDate() {
        updateLabel();
        mCurrentDate.setOnClickListener(this);
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

    @Override
    public void onItemClickListener(View view, int position) {

    }

    /**
     * Otto events
     */
    @Subscribe
    public void onRoomsSuccessfullyRetrieved(GetRoomsTask.Event event) {
        mRoomsAdapter.removeAll();
        List<Room> rooms = event.getResult();
        mRoomsAdapter.addItemList(rooms);
        mLoadingIndicator.dismiss();
        callbacks.onRoomsSuccessfullyRetrieved(rooms);
    }

    @Subscribe
    public void onRoomsError(GetRoomsTask.ErrorEvent error) {
        mLoadingIndicator.dismiss();
        callbacks.onRoomsError(error);
    }

    public interface Callbacks {
        List<Room> getRooms();

        void onRoomsSuccessfullyRetrieved(List<Room> rooms);

        void onRoomsError(GetRoomsTask.ErrorEvent error);
    }
}
