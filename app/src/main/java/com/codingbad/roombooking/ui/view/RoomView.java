package com.codingbad.roombooking.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Room;
import com.codingbad.roombooking.model.Timeline;
import com.codingbad.roombooking.utils.ViewUtils;

import roboguice.inject.InjectView;

/**
 * Created by ayi on 11/18/15.
 */
public class RoomView extends LinearLayout {

    @InjectView(R.id.view_room_name_and_description)
    private TextView mRoomNameAndDescription;

    @InjectView(R.id.view_room_timeline)
    private TimelineView mRoomTimeline;

    public RoomView(Context context) {
        super(context);
        init();
    }

    public RoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_room, this);
        ViewUtils.reallyInjectViews(this);
    }

    public void fill(Room room) {
        // fill room UI
        mRoomNameAndDescription.setText(getDescription(room));
        mRoomTimeline.fill(new Timeline("07:00", "19:00", room, 15*60*1000));
    }

    private String getDescription(Room room) {
        return this.getContext().getString(R.string.room_description, room.getName(), room.getLocation(), room.getEquipmentDescription(getContext()));
    }
}

