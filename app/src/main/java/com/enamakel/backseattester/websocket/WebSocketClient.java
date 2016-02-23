package com.enamakel.backseattester.websocket;


import android.content.Context;
import android.util.Log;

import com.enamakel.backseattester.activities.TabbedActivity;
import com.enamakel.backseattester.data.models.SessionModel;
import com.google.gson.Gson;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;

import javax.inject.Inject;


public class WebSocketClient extends org.java_websocket.client.WebSocketClient {
    Context context;
    boolean isConnected;

    @Inject Gson gson;


    public WebSocketClient(URI serverURI, Context context) {
        super(serverURI);
        this.context = context;
        isConnected = false;
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        Log.i("Websocket", "Opened");
        TabbedActivity.info("connected to " + uri.toString());
        isConnected = true;
    }


    @Override
    public void onMessage(String message) {
        Log.i("Websocket", message);
        TabbedActivity.debug("got: " + message);

        Response response = gson.fromJson(message, Response.class);
        SessionModel session = response.getSession();

        if (response.getStatus().equals("ok")) {
//            App.session.update(response.getSession());

            // Inform each of our resources about the socket response!
//            App.passengerResource.onSocketResponse(session);
//            App.journeyResource.onSocketResponse(session);
//            App.tabletResource.onSocketResponse(session);
//            App.mediaResource.onSocketResponse(session);

            // Erase the commands from the session!
            session.setCommands(new ArrayList<SessionModel.Command>());
        } else {
            Log.e("Websocket", response.getError_message());
            TabbedActivity.info(response.getError_message());
        }
    }


    @Override
    public void onClose(int i, String s, boolean b) {
        Log.i("Websocket", "Closed " + s);
        TabbedActivity.info("connection closed");
        isConnected = false;
    }


    @Override
    public void onError(Exception exception) {
        Log.i("Websocket", "Error " + exception.getMessage());
        exception.printStackTrace();
        TabbedActivity.info("connection error: " + exception.getMessage());

        close();
        isConnected = false;
    }
}