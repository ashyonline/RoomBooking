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

    public Room removeItem(int position) {
        final Room removed = this.mRooms.remove(position);
        notifyItemRemoved(position);
        return removed;
    }

    public Room getItemAtPosition(int position) {
        return this.mRooms.get(position);
    }

    @Override
    public int getItemCount() {
        return this.mRooms.size();
    }

    public void animateTo(List<Room> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Room> newItems) {
        for (int i = mRooms.size() - 1; i >= 0; i--) {
            final Room room = mRooms.get(i);
            if (!newItems.contains(room)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Room> newItems) {
        for (int i = 0, count = newItems.size(); i < count; i++) {
            final Room room = newItems.get(i);
            if (!mRooms.contains(room)) {
                addItem(i, room);
            }
        }
    }

    public void addItem(int position, Room room) {
        this.mRooms.add(position, room);
        notifyItemInserted(getItemCount());
    }


    private void applyAndAnimateMovedItems(List<Room> newItems) {
        for (int toPosition = newItems.size() - 1; toPosition >= 0; toPosition--) {
            final Room room = newItems.get(toPosition);
            final int fromPosition = mRooms.indexOf(room);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Room moved = this.mRooms.remove(fromPosition);
        this.mRooms.add(toPosition, moved);
        notifyItemMoved(fromPosition, toPosition);
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


