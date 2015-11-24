package com.codingbad.roombooking.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Room;

/**
 * Created by ayi on 11/23/15.
 */
public class BookRoomFromFragment extends AbstractFragment<BookRoomFromFragment.Callbacks> implements View.OnClickListener {
    public static final String DATE = "date";
    private static final String ROOM = "room";
    private Room mSelectedRoom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_booking_from, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            mSelectedRoom = (Room) savedInstanceState.getSerializable(ROOM);
        }

        if (mSelectedRoom == null) {
            mSelectedRoom = callbacks.getSelectedRoom();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSelectedRoom != null) {
            outState.putSerializable(ROOM, mSelectedRoom);
        }
    }

    @Override
    public void onClick(View v) {

    }

    public interface Callbacks {
        Room getSelectedRoom();
    }
}
