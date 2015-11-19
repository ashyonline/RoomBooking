package com.codingbad.roombooking.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Room;
import com.codingbad.roombooking.model.RoomsErrorModel;
import com.codingbad.roombooking.task.GetRoomsTask;
import com.codingbad.roombooking.ui.fragment.AvailableRoomsFragment;
import com.codingbad.roombooking.ui.fragment.ErrorFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActionBarActivity;

public class MainActivity extends RoboActionBarActivity implements AvailableRoomsFragment.Callbacks {

    private static final String AVAILABLE_ROOMS_FRAGMENT_TAG = "available_rooms_fragment";
    private static final String ERROR_FRAGMENT_TAG = "error_fragment";
    private static final String LAST_RESPONSE = "last_response";

    private FragmentManager mFragmentManager;
    private List<Room> mLastResponse = new ArrayList<>();


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
}
