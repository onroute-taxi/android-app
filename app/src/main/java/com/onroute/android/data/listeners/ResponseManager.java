package com.onroute.android.data.listeners;


import com.onroute.android.data.models.SessionModel;

import javax.inject.Singleton;


@Singleton
public class ResponseManager extends BaseListener<ResponseManager.Listener, SessionModel> {

    @Override
    void onListenerNotify(Listener listener, SessionModel data) {
        listener.onServerResponse(data);
    }


    public interface Listener {
        /**
         * This function is called whenever a response is received from the server. The resource handler
         * is responsible for performing various tasks based on the data in the session object.
         *
         * @param session The session object from the server.
         */
        void onServerResponse(SessionModel session);
    }
}