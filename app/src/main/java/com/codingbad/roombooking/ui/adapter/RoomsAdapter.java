package com.codingbad.roombooking.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.codingbad.roombooking.model.Room;
import com.codingbad.roombooking.ui.view.RoomView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayi on 11/18/15.
 */
public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    private List<Room> mRooms;
    private RecyclerViewListener mRecyclerViewListener;

    public RoomsAdapter(RecyclerViewListener recyclerViewListener) {
        this.mRecyclerViewListener = recyclerViewListener;
        this.mRooms = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RoomView view = new RoomView(parent.getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Room room = mRooms.get(position);
        holder.mRoomView.fill(room);
    }

    public void addItem(Room room) {
        this.mRooms.add(room);
        notifyItemInserted(getItemCount());
    }

    public void addItemList(List<Room> rooms) {
        this.mRooms.addAll(rooms);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.mRooms.clear();
        notifyDataSetChanged();
    }

    public void removeItemAt(int position) {
        this.mRooms.remove(position);
        notifyItemRemoved(position);
    }

    public Room getItemAtPosition(int position) {
        return this.mRooms.get(position);
    }

    @Override
    public int getItemCount() {
        return this.mRooms.size();
    }

    public interface RecyclerViewListener {
        void onItemClickListener(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RoomView mRoomView;

        public ViewHolder(RoomView itemView) {
            super(itemView);
            this.mRoomView = itemView;
            this.mRoomView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecyclerViewListener.onItemClickListener(v, getAdapterPosition());
        }
    }
}


