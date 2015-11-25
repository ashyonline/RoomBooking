package com.codingbad.roombooking.task;

import android.content.Context;

import com.codingbad.roombooking.model.Room;
import com.codingbad.roombooking.model.RoomsErrorModel;
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
 * Created by ayi on 11/18/15.
 */
public class GetRoomsTask extends AbstractTask {
    @Inject
    private RoomClient mRoomClient;
    private String mDate;

    public GetRoomsTask(Context context, String date) {
        super(context);
        mDate = date;
    }

    @Override
    public Response call() throws Exception {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        Response response = mRoomClient.getRooms(mDate);
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
            Room[] rooms = gson.fromJson(body, Room[].class);
            return new Event(Arrays.asList(rooms));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }


    /*
         * Otto Events
    */
    public class Event {
        private List<Room> mResult;

        public Event(List<Room> result) {
            this.mResult = result;
        }

        public List<Room> getResult() {
            return mResult;
        }
    }
}
