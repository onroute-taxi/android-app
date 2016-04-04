package com.enamakel.backseattester.data.resources;


import com.enamakel.backseattester.data.models.AdvertisementModel;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.network.websocket.Request;
import com.enamakel.backseattester.network.websocket.Websocket;

import javax.inject.Inject;


public class AdvertisementResource extends BaseResource {
    @Inject Websocket websocket;


    @Override
    public void onServerResponse(SessionModel session) {

    }


    public void getAd() {
        Request request = new Request("advertisement", "get");
        websocket.send(request);
    }


    public void onAdWatched(AdvertisementModel advertisement) {
        Request request = new Request("advertisement", "watched", advertisement);
        websocket.send(request);
    }
}
