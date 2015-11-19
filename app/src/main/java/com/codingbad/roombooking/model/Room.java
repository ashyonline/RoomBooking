package com.codingbad.roombooking.model;

import android.content.Context;

import com.codingbad.roombooking.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ayi on 11/18/15.
 */
public class Room implements Serializable {
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
    private Object mImages;

    public String getName() {
        return mName;
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

    public List<String> getAvailability() {
        return mAvailability;
    }

    public Object getImages() {
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
}
