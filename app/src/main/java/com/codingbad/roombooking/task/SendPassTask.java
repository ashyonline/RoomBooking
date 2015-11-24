package com.codingbad.roombooking.task;

import android.content.Context;

import com.codingbad.roombooking.model.Booking;
import com.codingbad.roombooking.model.Room;
import com.codingbad.roombooking.model.RoomsErrorModel;
import com.codingbad.roombooking.model.SendPassResult;
import com.codingbad.roombooking.network.client.RoomClient;
import com.codingbad.roombooking.otto.OttoBus;
import com.codingbad.roombooking.utils.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import roboguice.util.RoboAsyncTask;

/**
 * Created by ayelen on 11/24/15.
 */
public class SendPassTask extends RoboAsyncTask<Response> {
    private final Booking mBooking;
    @Inject
    protected OttoBus mOttoBus;
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
            return new Event(sendPassResult);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
//        }

        return null;
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        if (e instanceof RetrofitError) {
            RetrofitError e1 = (RetrofitError) e;
            if (e1.getResponse() != null) {
                RoomsErrorModel roomsError = (RoomsErrorModel) e1.getBodyAs(RoomsErrorModel.class);
                mOttoBus.post(new ErrorEvent(roomsError));
            } else {
                super.onException(e);
                mOttoBus.post(new RetrofitErrorEvent(e1.getMessage()));
                e.printStackTrace();
            }
        } else {
            super.onException(e);
        }
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

    public class ErrorEvent {
        private final RoomsErrorModel mRoomsError;

        public ErrorEvent(RoomsErrorModel roomsError) {
            this.mRoomsError = roomsError;
        }

        public RoomsErrorModel getRoomsError() {
            return mRoomsError;
        }
    }

    public class RetrofitErrorEvent {
        private final String mMessage;

        public RetrofitErrorEvent(String message) {
            mMessage = message;
        }

        public String getMessage() {
            return mMessage;
        }
    }
}
