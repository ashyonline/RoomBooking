package com.codingbad.roombooking.network.client;

import com.codingbad.roombooking.BuildConfig;
import com.codingbad.roombooking.model.Booking;
import com.codingbad.roombooking.model.BookingModel;
import com.codingbad.roombooking.model.DateModel;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by ayi on 11/18/15.
 */
public class RoomClient {
    private RestAdapter restAdapter;
    private IRoomClient mRoomClient;

    public RoomClient() {
        if (restAdapter == null || mRoomClient == null) {
            OkHttpClient okHttpClient = new OkHttpClient();

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BuildConfig.HOST)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(okHttpClient))
                    .build();

            mRoomClient = restAdapter.create(IRoomClient.class);
        }
    }

    public Response sendPass(Booking bookingModel) {
        return mRoomClient.sendPass(new BookingModel(bookingModel));
    }

    public Response getRooms(String date) {
        return mRoomClient.getRooms(new DateModel(date));
    }
}