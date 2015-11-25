package com.codingbad.roombooking.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Booking;
import com.codingbad.roombooking.model.Room;
import com.codingbad.roombooking.model.RoomsErrorModel;
import com.codingbad.roombooking.model.SendPassError;
import com.codingbad.roombooking.task.AbstractTask;
import com.codingbad.roombooking.task.SendPassTask;
import com.codingbad.roombooking.ui.fragment.AvailableRoomsFragment;
import com.codingbad.roombooking.ui.fragment.BookRoomCreateEventFragment;
import com.codingbad.roombooking.ui.fragment.BookRoomAddParticipantsFragment;
import com.codingbad.roombooking.ui.fragment.BookRoomDateFragment;
import com.codingbad.roombooking.ui.fragment.ErrorFragment;
import com.codingbad.roombooking.ui.fragment.PassesSuccessfullyRetrievedFragment;
import com.codingbad.roombooking.ui.fragment.RoomDetailsFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roboguice.activity.RoboActionBarActivity;

public class MainActivity extends RoboActionBarActivity implements
        AvailableRoomsFragment.Callbacks,
        RoomDetailsFragment.Callbacks,
        BookRoomAddParticipantsFragment.Callbacks,
        BookRoomDateFragment.Callbacks,
        BookRoomCreateEventFragment.Callbacks,
        PassesSuccessfullyRetrievedFragment.Callbacks {

    private static final String AVAILABLE_ROOMS_FRAGMENT_TAG = "available_rooms_fragment";
    private static final String ERROR_FRAGMENT_TAG = "error_fragment";
    private static final String LAST_RESPONSE = "last_response";
    private static final String ROOM_DETAILS_FRAGMENT_TAG = "room_details_fragment";
    private static final String ROOM_BOOKING_DATE_FRAGMENT_TAG = "room_booking_date_tag";
    private static final String ROOM_BOOKING_EVENT_FRAGMENT_TAG = "room_booking_event_tag";
    private static final String PASSES_SUCCESSFULLY_RETRIEVED_FRAGMENT_TAG = "passes_successfully_retrieved_tag";
    private static final String ROOM_BOOKING_PARTICIPANTS_FRAGMENT_TAG = "room_booking_participants_tab";

    private FragmentManager mFragmentManager;
    private List<Room> mLastResponse = new ArrayList<>();
    private Room mSelectedRoom;
    private Date mSelectedDate;
    private Booking mBookingModel;

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
    public void onRoomsError(AbstractTask.ErrorEvent event) {
        RoomsErrorModel error = event.getRoomsError();
        showErrorFragment(error.getCode(), error.getDescription());
    }

    @Override
    public void onRetrofitError(AbstractTask.RetrofitErrorEvent error) {
        showErrorFragment(getString(R.string.unknown_error), error.getMessage());
    }

    @Override
    public void onSendPassError(SendPassError error) {
        showErrorFragment(String.valueOf(error.getCode()), error.getMessage());
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
    public Booking getBookingModel() {
        if (mBookingModel == null) {
            mBookingModel = new Booking(mSelectedRoom);
        }

        return mBookingModel;
    }

    @Override
    public void onPassesSuccessfullyRetrieved(SendPassTask.Event event) {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        PassesSuccessfullyRetrievedFragment passesSuccessfullyRetrievedFragment = new PassesSuccessfullyRetrievedFragment();
        startFragment.addToBackStack(PASSES_SUCCESSFULLY_RETRIEVED_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, passesSuccessfullyRetrievedFragment, PASSES_SUCCESSFULLY_RETRIEVED_FRAGMENT_TAG);
        startFragment.commit();
    }

    @Override
    public void onNextPressed(Date date) {
        if (!mBookingModel.hasStartDate()) {
            mBookingModel.setStart(date);
            showBookRoomDate(false);
        } else {
            mBookingModel.setEnd(date);
            createEvent();
            // addParticipants();
        }
    }

    private void createEvent() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        BookRoomCreateEventFragment bookRooCreateEventFragment = new BookRoomCreateEventFragment();
        startFragment.addToBackStack(ROOM_BOOKING_EVENT_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, bookRooCreateEventFragment, ROOM_BOOKING_EVENT_FRAGMENT_TAG);
        startFragment.commit();
    }

    @Override
    public void startBookingRoom() {
        showBookRoomDate(true);
        mBookingModel = new Booking(mSelectedRoom);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.fragment);
        if (currentFragment instanceof BookRoomDateFragment) {
            if (((BookRoomDateFragment) currentFragment).isStart()) {
                mBookingModel.setStart(null);
            } else {
                mBookingModel.setEnd(null);
            }
        }
    }

    private void showBookRoomDate(boolean start) {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        BookRoomDateFragment bookRoomFromFragment = new BookRoomDateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BookRoomDateFragment.DATE, mSelectedDate);
        bundle.putBoolean(BookRoomDateFragment.START, start);
        bookRoomFromFragment.setArguments(bundle);
        startFragment.addToBackStack(ROOM_BOOKING_DATE_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, bookRoomFromFragment, ROOM_BOOKING_DATE_FRAGMENT_TAG);
        startFragment.commit();
    }

    public void addParticipants() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        BookRoomAddParticipantsFragment bookRoomFragment = new BookRoomAddParticipantsFragment();
        startFragment.addToBackStack(ROOM_BOOKING_PARTICIPANTS_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, bookRoomFragment, ROOM_BOOKING_PARTICIPANTS_FRAGMENT_TAG);
        startFragment.commit();
    }

    @Override
    public void eventCreated(String title, String description) {
        mBookingModel.setEventTitle(title);
        mBookingModel.setEventDescription(description);
        addParticipants();

    }

    @Override
    public void goBackToBookRoomList() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        AvailableRoomsFragment availableRoomsFragment = new AvailableRoomsFragment();
        startFragment.replace(R.id.fragment, availableRoomsFragment, AVAILABLE_ROOMS_FRAGMENT_TAG);
        startFragment.commit();
    }
}
