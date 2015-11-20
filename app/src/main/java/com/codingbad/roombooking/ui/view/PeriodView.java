package com.codingbad.roombooking.ui.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Period;

/**
 * Created by ayelen on 11/20/15.
 */
public class PeriodView extends LinearLayout {
    private int mColor;
    private View mMainView;

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
        inflate(getContext(), R.layout.view_period, this);
        mMainView = findViewById(R.id.view_period_main);
    }

    public void fill(Period period) {
        mColor = period.isAvailable() ? ContextCompat.getColor(getContext(), R.color.available) : ContextCompat.getColor(getContext(), R.color.not_available);
        mMainView.setBackgroundColor(mColor);
    }
}
