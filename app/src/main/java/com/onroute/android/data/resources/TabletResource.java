package com.onroute.android.data.resources;


import com.onroute.android.data.models.SessionModel;
import com.onroute.android.data.models.TabletModel;
import com.onroute.android.network.websocket.Request;
import com.onroute.android.network.websocket.Websocket;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class TabletResource extends BaseResource {
    @Inject SessionModel session;
    @Inject Websocket websocket;
    @Inject TabletModel tablet;


    public TabletResource() {
        super();
    }


    @Override
    public void onServerResponse(SessionModel session) {

    }


    public void checkin() {
        // Set the tablet into the session
//        session.setTablet(tablet);

        // Create and send the request!
        Request request = new Request("tablet", "checkin");
        websocket.send(request);
    }


    public void heartbeat() {
        // Create and send the request
        Request request = new Request("tablet", "heartbeat");
        websocket.send(request);
    }
}
