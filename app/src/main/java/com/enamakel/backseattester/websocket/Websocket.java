package com.enamakel.backseattester.websocket;


import android.content.Context;

import com.enamakel.backseattester.activities.TabbedActivity;
import com.google.gson.Gson;

import java.net.URI;

import javax.inject.Inject;


public class Websocket {
    public WebSocketClient client;
    URI uri;

    @Inject Gson gson;


    public Websocket(URI uri, Context context) {
        this.uri = uri;
        client = new WebSocketClient(uri, context);
    }


    public void connect() {
        connect(uri);
    }


    public void connect(final URI uri) {
        TabbedActivity.info("connecting to " + uri.toString());
        client.connect();
    }


    public void send(String message) {
        if (client == null) {
            TabbedActivity.info("not connected to server");
            connect();
        }
        client.send(message);
    }


    public void send(Request request) {
        send(gson.toJson(request));
    }
}