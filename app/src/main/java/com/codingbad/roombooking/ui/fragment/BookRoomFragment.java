package com.codingbad.roombooking.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Room;

/**
 * Created by ayi on 11/22/15.
 */
public class BookRoomFragment  extends AbstractFragment<BookRoomFragment.Callbacks> {
    private static final String ROOM = "room";
    private Room mSelectedRoom;

    public BookRoomFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_booking, container, false);
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

        setupRoom();
    }

    private void setupRoom() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSelectedRoom != null) {
            outState.putSerializable(ROOM, mSelectedRoom);
        }
    }


    public interface Callbacks {
        Room getSelectedRoom();
    }
}
