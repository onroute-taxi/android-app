package com.enamakel.backseattester.network.websocket;


import android.content.Context;
import android.util.Log;

import com.enamakel.backseattester.ActivityModule;
import com.enamakel.backseattester.App;
import com.enamakel.backseattester.data.listeners.ResponseManager;
import com.enamakel.backseattester.data.models.SessionModel;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.inject.Inject;

import lombok.Setter;


public class Websocket {
    public static String BROADCAST_ACTION = Websocket.class.getName() + ".RESPONSE";
    static final String TAG = Websocket.class.getSimpleName();

    @Inject Context context;
    @Inject Gson gson;
    @Inject SessionModel session;
    @Inject ResponseManager responseManager;

    WebSocketClient client;
    @Setter URI uri;


    public Websocket() {
        Log.d("ActivityModule", "providing websocket");
        App.inject(this);

        try {
            uri = new URI("ws://" + ActivityModule.socket_ip + ":1414");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public WebSocketClient generateClient() {
        WebSocketClient client = new WebSocketClient(uri, context, gson);

        // Everytime the server sends us a message, we create a broadcast intent and relay it to
        // the entire app.
        client.setMessageListener(new WebSocketClient.MessageListener() {
            @Override
            public void onMessage(Response response) {
                // Update the session first
                session.update(response.getSession());

                responseManager.notifyListeners(session);

                // Erase the commands from the session!
                response.getSession().setCommands(new ArrayList<SessionModel.Command>());
            }
        });

        return client;
    }


    public void connect() {
        connect(uri);
    }


    public void connect(final URI uri) {
//        TabbedActivity.info("connecting to " + uri.toString());
        try {
            if (client != null) client.close();
            client = generateClient();
            client.connect();
        } catch (Exception exception) {
            Log.i(TAG, exception.toString());

            // enque the request for later or launch a interval set function to reconnect to the
            // server.
        }
    }


    public void send(String message) {
        if (client == null || !client.isConnected()) {
//            TabbedActivity.info("not connected to server");
            connect();
        }

        try {
            client.send(message);
        } catch (Exception exception) {
            Log.e(TAG, exception.toString());
            client.close();

            // If the client was not connected, then we try to connect the client and try again.
            connect();

//            try {
//                // Send the message, now that the client is connected.
//                client.send(message);
//            } catch (Exception exception2) {
//                Log.e(TAG, exception2.toString());
//                client.close();
//
//                // If we still encounter an error, then we enque the request to be sent at a later
//                // time.
//            }
        }
    }


    public void send(Request request) {
        send(gson.toJson(request));
    }
}