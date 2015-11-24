package com.codingbad.roombooking.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codingbad.roombooking.BuildConfig;
import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Room;
import com.squareup.picasso.Picasso;

import roboguice.inject.InjectView;

/**
 * Created by ayi on 11/22/15.
 */
public class RoomDetailsFragment extends AbstractFragment<RoomDetailsFragment.Callbacks> implements View.OnClickListener {
    private static final String ROOM = "room";
    private Room mSelectedRoom;

    @InjectView(R.id.fragment_room_details_name)
    private TextView mRoomName;

    @InjectView(R.id.fragment_room_details_description)
    private TextView mRoomDescription;

    @InjectView(R.id.fragment_room_details_location)
    private TextView mRoomLocation;

    @InjectView(R.id.fragment_room_details_images)
    private LinearLayout mImages;

    @InjectView(R.id.fragment_room_details_book_button)
    private Button mBookButton;

    public RoomDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_details, container, false);
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
        mBookButton.setOnClickListener(this);
    }

    private void setupRoom() {
        mRoomName.setText(mSelectedRoom.getName(this.getContext()));
        mRoomDescription.setText(mSelectedRoom.getLongDescription(this.getContext()));
        mRoomLocation.setText(mSelectedRoom.getRoomLocation(this.getContext()));

        int size = getResources().getDimensionPixelSize(R.dimen.image_size);

        for (String url : mSelectedRoom.getImages()) {

            ImageView imageView = new ImageView(getContext());
            Picasso.with(getContext())
                    .load(BuildConfig.HOST + "/" + url)
                    .centerCrop()
                    .resize(size, size)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(imageView);
            mImages.addView(imageView);
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
        callbacks.startBookingRoom();
    }

    public interface Callbacks {
        Room getSelectedRoom();

        void startBookingRoom();
    }
}
