package com.onroute.android.data.resources;


import com.onroute.android.data.models.AdvertisementModel;
import com.onroute.android.data.models.SessionModel;
import com.onroute.android.network.websocket.Request;
import com.onroute.android.network.websocket.Websocket;

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
