package com.enamakel.backseattester.data.resources;


import com.enamakel.backseattester.activities.TabbedActivity;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.data.models.TabletModel;
import com.enamakel.backseattester.websocket.Request;
import com.enamakel.backseattester.websocket.Websocket;

import javax.inject.Inject;


public class TabletResource extends BaseResource {
    @Inject SessionModel session;
    @Inject Websocket websocket;


    @Override
    public void onSocketResponse(SessionModel session) {

    }


    public void checkin() {
        // Set the tablet into the session
        session.setTablet(TabletModel.getInstance());

        // Create and send the request!
        TabbedActivity.info("checking in");
        Request request = new Request("tablet", "checkin");
        websocket.send(request);
    }


    public void hearbeat() {
        // Create and send the request
        TabbedActivity.info("sending hearbeat");
        Request request = new Request("tablet", "heartbeat");
        websocket.send(request);
    }
}
