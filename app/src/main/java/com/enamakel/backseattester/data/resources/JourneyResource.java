package com.enamakel.backseattester.data.resources;


import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.websocket.Request;
import com.enamakel.backseattester.websocket.Websocket;


public class JourneyResource extends BaseResource {
    @Override
    public void onSocketResponse(SessionModel session) {

    }


    public void beginJourney() {
        Request request = Request.create("journey", "begin");
        Websocket.send(request);
    }


    public void endJourney() {
        Request request = Request.create("journey", "end");
        Websocket.send(request);
    }
}
