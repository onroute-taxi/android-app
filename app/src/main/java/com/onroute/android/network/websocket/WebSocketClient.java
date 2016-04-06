package com.onroute.android.network.websocket;


import android.content.Context;
import android.util.Log;

import com.onroute.android.App;
import com.onroute.android.data.resources.TabletResource;
import com.google.gson.Gson;

import org.java_websocket.handshake.ServerHandshake;

import java.net.ConnectException;
import java.net.URI;
import java.nio.channels.NotYetConnectedException;

import lombok.Getter;
import lombok.Setter;


public class WebSocketClient extends org.java_websocket.client.WebSocketClient {
    Context context;
    Gson gson;
    @Getter boolean connected = false;
    @Getter boolean connecting = false;
    @Setter MessageListener messageListener;


    public WebSocketClient(URI serverURI, Context context, Gson gson) {
        super(serverURI);
        this.context = context;
        this.gson = gson;
    }


    @Override
    public void connect() {
        super.connect();
        connecting = true;
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        Log.i("Websocket", "Opened");
        connected = true;

        // As soon as the connection is made, we checkin the app!
        TabletResource tabletResource = App.get(TabletResource.class);
        tabletResource.checkin();
    }


    @Override
    public void send(String text) throws NotYetConnectedException {
        super.send(text);
    }


    @Override
    public void onMessage(String message) {
        Log.i("Websocket", message);

        Response response = gson.fromJson(message, Response.class);
        if (response.getStatus().equals("ok")) {
            // Inform each of our resources about the socket response!
            if (messageListener != null) messageListener.onMessage(response);

        } else {
            Log.e("Websocket", response.getError_message());
        }
    }


    @Override
    public void onClose(int i, String s, boolean b) {
        Log.i("Websocket", "Closed " + s);

        connected = false;
        connecting = false;
    }


    @Override
    public void onError(Exception exception) {
        Log.e("Websocket", "Error " + exception.getMessage());

        // Connection exceptions keep repeating
        if (!(exception instanceof ConnectException)) exception.printStackTrace();

        close();
        connected = false;
        connecting = false;
    }


    public interface MessageListener {
        void onMessage(Response response);
    }
}