package com.enamakel.backseattester.data.resources;


import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.network.websocket.Request;
import com.enamakel.backseattester.network.websocket.Websocket;

import javax.inject.Inject;


public class JourneyResource extends BaseResource {
    @Inject Websocket websocket;


    @Override
    public void onServerResponse(SessionModel session) {

    }


    public void beginJourney() {
        Request request = new Request("journey", "begin");
        websocket.send(request);
    }


    public void endJourney() {
        Request request = new Request("journey", "end");
        websocket.send(request);
    }
}
