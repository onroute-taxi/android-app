package com.enamakel.backseattester.data.resources;


import android.util.Log;

import com.enamakel.backseattester.activities.TabbedActivity;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.data.models.TabletModel;
import com.enamakel.backseattester.network.websocket.Request;
import com.enamakel.backseattester.network.websocket.Websocket;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class TabletResource extends BaseResource {
    @Inject SessionModel session;
    @Inject Websocket websocket;
    @Inject TabletModel tablet;


    public TabletResource() {
        super();
        Log.d("tablet", "helo");
    }


    @Override
    public void onServerResponse(SessionModel session) {

    }


    public void checkin() {
        // Set the tablet into the session
//        session.setTablet(tablet);

        // Create and send the request!
        TabbedActivity.info("checking in");
        Request request = new Request("tablet", "checkin");
        websocket.send(request);
    }


    public void heartbeat() {
        // Create and send the request
        TabbedActivity.info("sending heartbeat");
        Request request = new Request("tablet", "heartbeat");
        websocket.send(request);
    }
}
