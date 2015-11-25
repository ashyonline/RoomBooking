package com.codingbad.roombooking.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingbad.roombooking.R;
import com.codingbad.roombooking.model.Participant;

import java.util.List;

/**
 * Created by ayelen on 11/23/15.
 */
public class ParticipantAdapter extends ArrayAdapter<Participant> {

    private final ParticipantItemEventListener mParticipantItemEventListener;
    private ViewHolder mViewHolder;

    public ParticipantAdapter(Context context, int resource, List<Participant> participants, ParticipantItemEventListener listener) {
        super(context, resource, participants);
        mParticipantItemEventListener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.participant_item, parent, false);

            mViewHolder = new ViewHolder(convertView, mParticipantItemEventListener);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        Participant participant = getItem(position);
        if (participant != null) {
            mViewHolder.setParticipant(participant);
        }

        return convertView;
    }

    public interface ParticipantItemEventListener {
        void removeItem(Participant participant);
    }

    private static class ViewHolder implements View.OnClickListener {
        private final ParticipantItemEventListener mParticipantItemEventListener;
        private TextView mParticipantName;
        private ImageView mParticipantRemove;
        private Participant mParticipant;

        public ViewHolder(View convertView, ParticipantItemEventListener listener) {
            mParticipantName = (TextView) convertView.findViewById(R.id.participant_item_name);
            mParticipantRemove = (ImageView) convertView.findViewById(R.id.participant_item_remove);
            mParticipantRemove.setOnClickListener(this);
            mParticipantItemEventListener = listener;
        }

        public void setParticipant(Participant participant) {
            mParticipant = participant;
            mParticipantName.setText(participant.getName());
        }

        @Override
        public void onClick(View v) {
            mParticipantItemEventListener.removeItem(mParticipant);
        }
    }
}
