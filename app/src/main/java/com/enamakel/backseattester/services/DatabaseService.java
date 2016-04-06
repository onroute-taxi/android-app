package com.enamakel.backseattester.services;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.enamakel.backseattester.data.resources.TabletResource;
import com.enamakel.backseattester.network.websocket.Websocket;

import javax.inject.Inject;


/**
 * This service is responsible for constantly communicating with the server and sending little
 * 'heartbeats' to the server.
 */
public class DatabaseService extends InjectableService {
    static final String TAG = DatabaseService.class.getSimpleName();

    @Inject Websocket websocket;
    @Inject TabletResource tabletResource;

    /**
     * This interval is counted in ms, and determines how frequently the app sends the heartbeat
     * to the server.
     */
    final long interval = 5000;


    /**
     * A runnable to send the heartbeat to the server at regular intervals.
     */
    final Handler handler = new Handler();
    final Runnable statusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                Log.d(TAG, "heartbeat");
                tabletResource.heartbeat();
            } finally {
                handler.postDelayed(statusChecker, interval);
            }
        }
    };


    /**
     * Returns True if the service is running.
     */
    public static boolean isRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service :
                manager.getRunningServices(Integer.MAX_VALUE))
            if (DatabaseService.class.getName().equals(service.service.getClassName()))
                return true;
        return false;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        int ret = super.onStartCommand(intent, flags, startId);

        websocket.connect();
        tabletResource.checkin();
        statusChecker.run();

        return ret;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}