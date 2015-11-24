package com.codingbad.roombooking.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Room;
import com.codingbad.roombooking.model.RoomsErrorModel;
import com.codingbad.roombooking.model.Timeline;
import com.codingbad.roombooking.task.GetRoomsTask;
import com.codingbad.roombooking.ui.fragment.AvailableRoomsFragment;
import com.codingbad.roombooking.ui.fragment.BookRoomFragment;
import com.codingbad.roombooking.ui.fragment.BookRoomFromFragment;
import com.codingbad.roombooking.ui.fragment.ErrorFragment;
import com.codingbad.roombooking.ui.fragment.RoomDetailsFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roboguice.activity.RoboActionBarActivity;

public class MainActivity extends RoboActionBarActivity implements AvailableRoomsFragment.Callbacks, RoomDetailsFragment.Callbacks, BookRoomFragment.Callbacks {

    private static final String AVAILABLE_ROOMS_FRAGMENT_TAG = "available_rooms_fragment";
    private static final String ERROR_FRAGMENT_TAG = "error_fragment";
    private static final String LAST_RESPONSE = "last_response";
    private static final String ROOM_DETAILS_FRAGMENT_TAG = "room_details_fragment";
    private static final String ROOM_BOOKING_FROM_FRAGMENT_TAG = "room_booking_from";

    private FragmentManager mFragmentManager;
    private List<Room> mLastResponse = new ArrayList<>();
    private Room mSelectedRoom;
    private Date mSelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mLastResponse = (List<Room>) savedInstanceState.getSerializable(LAST_RESPONSE);
        }

        setContentView(R.layout.activity_main);
        if (getCurrentFragment() == null) {
            setMainFragment();
        }
    }

    private void setMainFragment() {
        mFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        startFragment.add(R.id.fragment, new AvailableRoomsFragment(), AVAILABLE_ROOMS_FRAGMENT_TAG);
        startFragment.commit();
    }

    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    public List<Room> getRooms() {
        return mLastResponse;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mLastResponse != null) {
            outState.putSerializable(LAST_RESPONSE, (Serializable) mLastResponse);
        }
    }

    @Override
    public void onRoomsSuccessfullyRetrieved(List<Room> rooms) {
        showRooms(rooms);
    }

    private void showRooms(List<Room> rooms) {
        mLastResponse = rooms;
    }

    @Override
    public void onRoomsError(GetRoomsTask.ErrorEvent event) {
        RoomsErrorModel error = event.getRoomsError();
        showErrorFragment(error.getCode(), error.getDescription());
    }

    @Override
    public void onRetrofitError(GetRoomsTask.RetrofitErrorEvent error) {
        showErrorFragment(getString(R.string.unknown_error), error.getMessage());
    }

    @Override
    public void showDetails(Room room, Date selectedDate) {
        mSelectedDate = selectedDate;
        mSelectedRoom = room;
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        RoomDetailsFragment roomDetailsFragment = new RoomDetailsFragment();
        startFragment.addToBackStack(ROOM_DETAILS_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, roomDetailsFragment, ROOM_DETAILS_FRAGMENT_TAG);
        startFragment.commit();
    }

    private void showErrorFragment(String code, String errorMessage) {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(ErrorFragment.CODE, code);
        bundle.putString(ErrorFragment.MESSAGE, errorMessage);
        ErrorFragment fyberErrorFragment = new ErrorFragment();
        fyberErrorFragment.setArguments(bundle);
        startFragment.addToBackStack(ERROR_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, fyberErrorFragment, ERROR_FRAGMENT_TAG);
        startFragment.commit();
    }

    @Override
    public Room getSelectedRoom() {
        return mSelectedRoom;
    }

    @Override
    public Timeline getTimeline() {
        return mSelectedRoom.getTimeline();
    }

    @Override
    public void startBookingRoom() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        BookRoomFromFragment bookRoomFromFragment = new BookRoomFromFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BookRoomFromFragment.DATE, mSelectedDate);
        startFragment.addToBackStack(ROOM_BOOKING_FROM_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, bookRoomFromFragment, ROOM_BOOKING_FROM_FRAGMENT_TAG);
        startFragment.commit();
    }

    public void addParticipants() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        BookRoomFragment bookRoomFragment = new BookRoomFragment();
        startFragment.addToBackStack(ROOM_DETAILS_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, bookRoomFragment, ROOM_DETAILS_FRAGMENT_TAG);
        startFragment.commit();
    }
}
