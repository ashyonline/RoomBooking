package com.codingbad.roombooking.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Booking;
import com.codingbad.roombooking.model.Participant;
import com.codingbad.roombooking.model.Room;
import com.codingbad.roombooking.ui.adapter.ParticipantAdapter;
import com.codingbad.roombooking.validator.EditTextValidator;
import com.codingbad.roombooking.validator.EmailValidator;

import roboguice.inject.InjectView;

/**
 * Created by ayi on 11/22/15.
 */
public class BookRoomAddParticipantsFragment extends AbstractFragment<BookRoomAddParticipantsFragment.Callbacks> implements View.OnClickListener, ParticipantAdapter.ParticipantItemEventListener {

    @InjectView(R.id.fragment_room_booking_add_participants_add)
    private Button mAddParticipant;

    @InjectView(R.id.fragment_room_booking_add_participants_name_field)
    private EditText mParticipantName;

    @InjectView(R.id.fragment_room_booking_add_participants_email_field)
    private EditText mParticipantEmail;

    @InjectView(R.id.fragment_room_booking_add_participants_phone_field)
    private EditText mParticipantPhone;

    @InjectView(R.id.fragment_room_booking_add_participants_participants)
    private ListView mParticipants;

    private ParticipantAdapter mAdapter;
    private Booking mBookingModel;

    public BookRoomAddParticipantsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_booking_add_participants, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRoom();
        mAddParticipant.setOnClickListener(this);
        initializeParticipants();
    }

    private void initializeParticipants() {
        mBookingModel = callbacks.getBookingModel();
        mAdapter = new ParticipantAdapter(this.getActivity(), R.layout.participant_item, mBookingModel.getParticipants(), this);

        mParticipants.setAdapter(mAdapter);
    }

    private void setupRoom() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        inflater.inflate(R.menu.menu_book, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_book:
                bookRoom();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bookRoom() {
        // book room!
        if (!mBookingModel.getParticipants().isEmpty()) {
            callbacks.bookRoom();
        } else {
            Toast.makeText(getContext(), getString(R.string.should_add_one_participant), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_room_booking_add_participants_add:
                addParticipant();
                break;
        }
    }

    private void addParticipant() {
        boolean error = false;

        if (!new EditTextValidator(mParticipantName, getString(R.string.field_should_not_be_empty)).validate()) {
            error = true;
        }

        if (!new EmailValidator(mParticipantEmail, getString(R.string.invalid_email)).validate()) {
            error = true;
        }

        if (!new EditTextValidator(mParticipantPhone, getString(R.string.field_should_not_be_empty)).validate()) {
            error = true;
        }

        if (!error) {
            String name = mParticipantName.getText().toString();
            String email = mParticipantEmail.getText().toString();
            String phone = mParticipantPhone.getText().toString();
            Participant participant = new Participant(name, email, phone);
            mBookingModel.addParticipant(participant);
            mParticipantName.setText("");
            mParticipantEmail.setText("");
            mParticipantPhone.setText("");
        }
    }

    @Override
    public void removeItem(Participant participant) {
        mAdapter.remove(participant);
        mAdapter.notifyDataSetChanged();
    }

    public interface Callbacks {
        Room getSelectedRoom();

        Booking getBookingModel();

        void bookRoom();
    }
}
