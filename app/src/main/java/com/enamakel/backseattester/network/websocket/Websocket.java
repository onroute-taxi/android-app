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


/**
 * This class represents
 */
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
        Log.d(TAG, "initializing");
        App.inject(this);

        try {
            uri = new URI("ws://" + ActivityModule.socket_ip + ":1414");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /**
     * Helper function to generate a websocket client.
     *
     * @return A client with all the wires attached to the rest of the app.
     */
    private WebSocketClient generateClient() {
        Log.d(TAG, "generating client");
        WebSocketClient client = new WebSocketClient(uri, context, gson);

        // Every-time the server sends us a message, we create a broadcast intent and relay it to
        // the entire app.
        client.setMessageListener(new WebSocketClient.MessageListener() {
            @Override
            public void onMessage(Response response) {
                session.update(response.getSession());
                responseManager.notifyListeners(session);

                // Erase the commands from the session.
                response.getSession().setCommands(new ArrayList<SessionModel.Command>());
            }
        });

        return client;
    }


    /**
     * Connect to the server.
     */
    public void connect() {
        Log.d(TAG, "connected to " + uri.toString());

        try {
            if (client != null) client.close();
            client = generateClient();
            client.connect();
        } catch (Exception exception) {
            Log.i(TAG, exception.toString());

            // TODO: If something went wrong, then launch a interval function to reconnect again.
        }
    }


    /**
     * Send the given message to the server, without putting it into a queue if it fails.
     *
     * @param message The message to be sent.
     */
    public void send(String message) {
        send(message, false);
    }


    /**
     * Send the given message to the server. If the app is not connected, then the request is either
     * queued (if the putInQueue flag is set) or discarded.
     * <p/>
     * TODO: Complete the queuing functionality. Also have a retry mechanism added as well.
     *
     * @param message    The message to be sent.
     * @param putInQueue Should the request be put in a queue, when the app reconnects.
     */
    public void send(String message, boolean putInQueue) {
        // If the client was not connected, then we try to connect the client once.
        if (client == null || !client.isConnected()) {
            Log.i(TAG, "Not connected to server");
            connect();
        }

        try {
            client.send(message);
        } catch (Exception exception) {
            // If something went wrong, then we try to reconnect to the server and try again.
            Log.e(TAG, exception.toString());
            client.close();

            // If the client was not connected, then we try to connect the client and try again.
            connect();

            // TODO: Retry the request here. If it fails again, then queue it if needed.
        }
    }


    public void send(Request request) {
        send(gson.toJson(request));
    }
}