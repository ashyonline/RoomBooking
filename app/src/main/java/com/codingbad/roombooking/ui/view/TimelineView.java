package com.codingbad.roombooking.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Period;
import com.codingbad.roombooking.model.Timeline;
import com.codingbad.roombooking.utils.ViewUtils;

import roboguice.inject.InjectView;

/**
 * Created by ayelen on 11/20/15.
 */
public class TimelineView extends LinearLayout {
    @InjectView(R.id.view_timeline_root)
    private LinearLayout mRootView;
    private Timeline mTimeline;

    public TimelineView(Context context) {
        super(context);
        init();
    }

    public TimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimelineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_timeline, this);
        ViewUtils.reallyInjectViews(this);
    }

    public void fill(Timeline timeline) {
        mTimeline = timeline;
        // fill room UI
        for (Period period : timeline.getPeriods()) {
            PeriodView periodView = new PeriodView(getContext());
            periodView.fill(period);
            mRootView.addView(periodView);
        }
    }
}
