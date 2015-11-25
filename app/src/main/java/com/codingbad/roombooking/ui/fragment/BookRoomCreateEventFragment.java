package com.codingbad.roombooking.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.validator.EditTextValidator;

import roboguice.inject.InjectView;

/**
 * Created by ayi on 11/25/15.
 */
public class BookRoomCreateEventFragment extends AbstractFragment<BookRoomCreateEventFragment.Callbacks> implements View.OnClickListener {
    @InjectView(R.id.fragment_room_booking_create_event_title_field)
    private EditText mTitle;

    @InjectView(R.id.fragment_room_booking_create_event_description_field)
    private EditText mDescription;

    @InjectView(R.id.fragment_room_booking_create_event_next)
    private Button mNext;

    public BookRoomCreateEventFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_booking_create_event, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean error = false;

        if (!new EditTextValidator(mTitle, getString(R.string.field_should_not_be_empty)).validate()) {
            error = true;
        }

        if (!new EditTextValidator(mDescription, getString(R.string.field_should_not_be_empty)).validate()) {
            error = true;
        }

        if (!error) {
            String title = mTitle.getText().toString();
            String description = mDescription.getText().toString();
            callbacks.eventCreated(title, description);
        }
    }

    public interface Callbacks {
        void eventCreated(String title, String description);
    }

}
