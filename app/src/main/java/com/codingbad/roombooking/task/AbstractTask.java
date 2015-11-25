package com.codingbad.roombooking.task;

import android.content.Context;

import com.codingbad.roombooking.model.RoomsErrorModel;
import com.codingbad.roombooking.otto.OttoBus;

import javax.inject.Inject;

import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.util.RoboAsyncTask;

/**
 * Created by ayi on 11/24/15.
 */
public abstract class AbstractTask extends RoboAsyncTask<Response> {

    @Inject
    protected OttoBus mOttoBus;

    protected AbstractTask(Context context) {
        super(context);
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
