package com.codingbad.roombooking.task;

import android.content.Context;

import com.codingbad.roombooking.model.Booking;
import com.codingbad.roombooking.model.SendPassError;
import com.codingbad.roombooking.model.SendPassResult;
import com.codingbad.roombooking.network.client.RoomClient;
import com.codingbad.roombooking.utils.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import retrofit.client.Response;

/**
 * Created by ayelen on 11/24/15.
 */
public class SendPassTask extends AbstractTask {
    private final Booking mBooking;

    @Inject
    private RoomClient mRoomClient;

    public SendPassTask(Context context, Booking booking) {
        super(context);
        mBooking = booking;
    }

    @Override
    public Response call() throws Exception {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        Response response = mRoomClient.sendPass(mBooking);
        return response;
    }

    @Override
    protected void onSuccess(Response result) throws Exception {
        super.onSuccess(result);

        if (!isCancelled()) {
            mOttoBus.post(parseResult(result));
        }
    }

    private Object parseResult(Response result) {
        try {
            Gson gson = new Gson();
            String body = StringUtils.convertToString(result.getBody().in());
            SendPassResult sendPassResult = gson.fromJson(body, SendPassResult.class);

            if (!sendPassResult.isSuccess()) {
                SendPassError sendPassError = gson.fromJson(body, SendPassError.class);
                return sendPassError;
            }

            return new Event(sendPassResult);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    /*
         * Otto Events
    */
    public class Event {
        private SendPassResult mResult;

        public Event(SendPassResult result) {
            this.mResult = result;
        }

        public SendPassResult getResult() {
            return mResult;
        }
    }
}
