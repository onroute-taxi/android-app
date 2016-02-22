package com.enamakel.backseattester.websocket;


import com.enamakel.backseattester.TabbedActivity;
import com.google.gson.Gson;

import java.net.URI;

import javax.inject.Inject;


public class Websocket {
    public static WebSocketClient client;
    @Inject Gson gson;


    public void connect(final URI uri) {
        TabbedActivity.info("connecting to " + uri.toString());
        client = new WebSocketClient(uri, TabbedActivity.context);
        client.connect();
    }


    public void send(String message) {
        if (client == null) {
            TabbedActivity.info("not connected to server");
            return;
        }
        client.send(message);
    }


    public void send(Request request) {
        client.send(gson.toJson(request));
    }
}
