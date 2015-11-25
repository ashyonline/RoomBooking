package com.codingbad.roombooking.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingbad.roombooking.R;

import roboguice.inject.InjectView;

/**
 * Created by ayi on 11/25/15.
 */
public class PassesSuccessfullyRetrievedFragment extends AbstractFragment<ErrorFragment.Callbacks> {
    @InjectView(R.id.fragment_passes_successfully_retrieved_message)
    private TextView mMessage;

    public PassesSuccessfullyRetrievedFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passes_successfully_retrieved, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMessage.setText(getString(R.string.success));
    }

    public interface Callbacks {
    }


}
