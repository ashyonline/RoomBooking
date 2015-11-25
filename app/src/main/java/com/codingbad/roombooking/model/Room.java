package com.codingbad.roombooking.model;

import android.content.Context;

import com.codingbad.roombooking.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ayi on 11/18/15.
 */
public class Room implements Serializable {
    public static final String AVAILABILITY_END = "19:00";
    public static final String AVAILABILITY_START = "07:00";
    private Timeline mTimeline;
    @SerializedName("name")
    private String mName;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("equipment")
    private List<String> mEquipment;
    @SerializedName("size")
    private String mSize;
    @SerializedName("capacity")
    private int mCapacity;
    @SerializedName("avail")
    private List<String> mAvailability;
    @SerializedName("images")
    private List<String> mImages;


    public String getName(Context context) {
        return context.getString(R.string.room_name, mName);
    }

    public String getLocation() {
        return mLocation;
    }

    public List<String> getEquipment() {
        return mEquipment;
    }

    public String getSize() {
        return mSize;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public List<Availability> getAvailability() {
        List<Availability> availabilities = new ArrayList<>();
        for (String availability : mAvailability) {
            availabilities.add(new Availability(availability));
        }
        return availabilities;
    }

    public List<String> getImages() {
        return mImages;
    }

    public String getEquipmentDescription(Context context) {
        String and = context.getString(R.string.and);
        if (mEquipment.isEmpty()) {
            return context.getString(R.string.no_available_equipment);
        }
        if (mEquipment.size() == 1) {
            return mEquipment.get(0);
        }

        if (mEquipment.size() == 2) {
            return mEquipment.get(0) + and + mEquipment.get(1);
        }

        String equipments = "";
        for (int i = 0; i < mEquipment.size() - 3; i++) {
            equipments = equipments + mEquipment.get(i) + ", ";
        }

        equipments = equipments + mEquipment.get(mEquipment.size() - 2);
        equipments = equipments + and + mEquipment.get(mEquipment.size() - 3);

        return equipments;
    }

    public boolean isAvailableForNextHour(Date now) {
        now.setTime(now.getTime() + (60 * 60 * 1000));
        for (Availability availability : getAvailability()) {
            if (availability.includes(now)) {
                return true;
            }
        }
        return false;
    }

    public String getNameAndDescription(Context context) {
        return getName(context) + ": " + getDescription(context);
    }

    public String getDescription(Context context) {
        return context.getString(R.string.room_description, mLocation, getEquipmentDescription(context));
    }

    public String getAvailabilityStart() {
        return AVAILABILITY_START;
    }

    public String getAvailabilityEnd() {
        return AVAILABILITY_END;
    }

    public int getAvailabilityStep() {
        return 15 * 60 * 1000;
    }

    public String getLongDescription(Context context) {
        return context.getString(R.string.room_long_description, getEquipmentDescription(context), mSize, mCapacity);
    }

    public String getRoomLocation(Context context) {
        return context.getString(R.string.room_location, mLocation);
    }

    public Timeline getTimeline() {
        if (mTimeline == null) {
            mTimeline = new Timeline(getAvailabilityStart(), getAvailabilityEnd(), this, getAvailabilityStep());
        }
        return mTimeline;
    }

    public String getRawName() {
        return mName;
    }
}
