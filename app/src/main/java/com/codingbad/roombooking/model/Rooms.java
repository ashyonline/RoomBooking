package com.codingbad.roombooking.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ayi on 11/18/15.
 */
public class Rooms implements Serializable {
    Room[] mRooms;

    public List<Room> getRooms() {
        return Arrays.asList(mRooms);
    }
}
