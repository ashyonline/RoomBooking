package com.codingbad.roombooking.network.client;

import com.codingbad.roombooking.model.BookingModel;
import com.codingbad.roombooking.model.DateModel;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by ayi on 11/18/15.
 */
public interface IRoomClient {
    @Headers("Content-Type: application/json")
    @POST("/getrooms")
    Response getRooms(@Body DateModel date);

    @Headers("Content-Type: application/json")
    @POST("/sendpass")
    Response sendPass(@Body BookingModel bookingModel);

}
