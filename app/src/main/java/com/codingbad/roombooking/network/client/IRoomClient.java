package com.codingbad.roombooking.network.client;

import com.codingbad.roombooking.model.Date;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by ayi on 11/18/15.
 */
public interface IRoomClient {
    @Headers("Content-Type: application/json")
    @POST("/getrooms")
    Response getRooms(@Body Date date);
}
