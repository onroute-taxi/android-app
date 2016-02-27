package com.enamakel.backseattester.websocket;


import android.content.Context;
import android.util.Log;

import com.enamakel.backseattester.ActivityModule;
import com.enamakel.backseattester.activities.TabbedActivity;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import lombok.Setter;


public class Websocket {
    WebSocketClient client;
    Gson gson;
    @Setter URI uri;


    @Inject
    public Websocket(Context context, Gson gson) {
        Log.d("ActivityModule", "providing websocket");
        this.gson = gson;

        try {
            uri = new URI("ws://" + ActivityModule.socket_ip + ":1414");
            client = new WebSocketClient(uri, context, gson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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