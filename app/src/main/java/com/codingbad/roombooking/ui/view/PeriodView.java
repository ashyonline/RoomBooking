package com.codingbad.roombooking.ui.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Period;
import com.codingbad.roombooking.utils.ViewUtils;

import roboguice.inject.InjectView;

/**
 * Created by ayelen on 11/20/15.
 */
public class PeriodView extends LinearLayout {
    private int mColor;
    @InjectView(R.id.view_period_main)
    private View mMainView;
    @InjectView(R.id.view_period_time)
    private TextView mTime;
    private Period mPeriod;
    private int mBookingColor;
    private int mAvailableColor;
    private int mNotAvailableColor;

    public PeriodView(Context context) {
        super(context);
        init();
    }

    public PeriodView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PeriodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBookingColor = ContextCompat.getColor(getContext(), R.color.booking);
        mAvailableColor = ContextCompat.getColor(getContext(), R.color.available);
        mNotAvailableColor = ContextCompat.getColor(getContext(), R.color.not_available);
        inflate(getContext(), R.layout.view_period, this);
        ViewUtils.reallyInjectViews(this);
    }

    public void fill(Period period) {
        mPeriod = period;
        mColor = period.isAvailable() ? mAvailableColor : mNotAvailableColor;

        paint();

        if (period.isStart()) {
            mTime.setText(period.fromDate());
        }
    }

    private void paint() {
        GradientDrawable backgroundDrawable = new GradientDrawable();
        backgroundDrawable.setColor(mColor);
        backgroundDrawable.setCornerRadius(5);
        backgroundDrawable.setStroke(1, 0x00000000);
        mMainView.setBackgroundDrawable(backgroundDrawable);
    }

    public void showBook() {
        mColor = mBookingColor;
        paint();
    }

    public Period getPeriod() {
        return mPeriod;
    }

    public void showAvailable() {
        mColor = mAvailableColor;
        paint();
    }

    public boolean isMarkedBooked() {
        return mColor == mBookingColor;
    }
}
