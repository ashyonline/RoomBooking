package com.codingbad.roombooking.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.inject.InjectView;

public class AvailableRoomsFragment extends AbstractFragment<AvailableRoomsFragment.Callbacks> implements View.OnClickListener, RoomsAdapter.RecyclerViewListener, SearchView.OnQueryTextListener {

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
    private SearchView mSearchView;
    private List<Room> mRooms;
    private CheckBox mNextHourFilter;
    private String mLastQuery;

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

        setupLoadingIndicator();

        if (mSelectedDate == null) {
            mSelectedDate = mCalendar.getTime();
            getAvailableRooms();
        }

        setupDate();
        setUpRooms();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Retrieve the SearchView and plug it into SearchManager
        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        mSearchView.setQueryHint(getString(R.string.search_hint));
        mSearchView.setOnQueryTextListener(this);

        mNextHourFilter = (CheckBox) MenuItemCompat.getActionView(menu.findItem(R.id.action_checkbox));
        mNextHourFilter.setOnClickListener(this);
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
        mRooms = callbacks.getRooms();
        mRoomsAdapter.addItemList(mRooms);
        mAvailableRooms.setAdapter(mRoomsAdapter);
    }

    private void getAvailableRooms() {
        mLoadingIndicator.show();
        new GetRoomsTask(getContext(), String.valueOf(mCalendar.getTimeInMillis()/1000)).execute();
    }

    private void updateLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, getResources().getConfiguration().locale);
        mCurrentDate.setText(simpleDateFormat.format(mSelectedDate));
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
            case R.id.action_checkbox:
                if (mLastQuery == null || mLastQuery.isEmpty()) {
                    List<Room> filteredRooms;
                    if (mNextHourFilter.isChecked()) {
                        filteredRooms = filterAvailableNextHour(mRooms);
                    } else {
                        filteredRooms = new ArrayList<>();
                        filteredRooms.addAll(mRooms);
                    }
                    mRoomsAdapter.removeAll();
                    mRoomsAdapter.addItemList(filteredRooms);
                    mAvailableRooms.scrollToPosition(0);
                }
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
        mRooms = event.getResult();
        mRoomsAdapter.addItemList(mRooms);
        mLoadingIndicator.dismiss();
        callbacks.onRoomsSuccessfullyRetrieved(mRooms);
    }

    @Subscribe
    public void onRoomsError(GetRoomsTask.ErrorEvent error) {
        mLoadingIndicator.dismiss();
        callbacks.onRoomsError(error);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mLastQuery = query;
        List<Room> filteredModelList = filter(mRooms, query);
        boolean lookForAvailableForNextHour = mNextHourFilter.isChecked();
        if (lookForAvailableForNextHour) {
            filteredModelList = filterAvailableNextHour(filteredModelList);
        }
        mRoomsAdapter.animateTo(filteredModelList);
        mAvailableRooms.scrollToPosition(0);
        return true;
    }

    private List<Room> filterAvailableNextHour(List<Room> rooms) {

        final List<Room> filteredRooms = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date now = mSelectedDate;
        try {
            now = simpleDateFormat.parse(simpleDateFormat.format(mSelectedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date backup = (Date) now.clone();
        for (Room room : rooms) {
            now = (Date) backup.clone();
            if (room.isAvailableForNextHour(now)) {
                filteredRooms.add(room);
            }
        }
        return filteredRooms;
    }

    private List<Room> filter(List<Room> items, String query) {
        query = query.toLowerCase();

        final List<Room> filteredRooms = new ArrayList<>();
        for (Room room : items) {
            String roomName = room.getName().toLowerCase();
            String equipment = room.getEquipmentDescription(this.getActivity()).toLowerCase();
            if (roomName.contains(query) || equipment.contains(query)) {
                filteredRooms.add(room);
            }
        }
        return filteredRooms;
    }

    public interface Callbacks {
        List<Room> getRooms();

        void onRoomsSuccessfullyRetrieved(List<Room> rooms);

        void onRoomsError(GetRoomsTask.ErrorEvent error);
    }
}
