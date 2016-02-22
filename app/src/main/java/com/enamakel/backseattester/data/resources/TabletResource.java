package com.enamakel.backseattester.data.resources;


import com.enamakel.backseattester.TabbedActivity;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.data.models.TabletModel;
import com.enamakel.backseattester.websocket.Request;
import com.enamakel.backseattester.websocket.Websocket;

import javax.inject.Inject;


public class TabletResource extends BaseResource {
    @Inject SessionModel session;


    @Override
    public void onSocketResponse(SessionModel session) {

    }


    public void checkin() {
        // Set the tablet into the session
        session.setTablet(TabletModel.getInstance());

        // Create and send the request!
        TabbedActivity.info("checking in");
        Request request = Request.create("tablet", "checkin");
        Websocket.send(request);
    }


    public void hearbeat() {
        // Create and send the request
        TabbedActivity.info("sending hearbeat");
        Request request = Request.create("tablet", "heartbeat");
        Websocket.send(request);
    }
}
