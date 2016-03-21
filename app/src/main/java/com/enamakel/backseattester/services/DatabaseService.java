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


public class DatabaseService extends BaseService {
    static final String TAG = DatabaseService.class.getSimpleName();

    @Inject Websocket websocket;
    @Inject TabletResource tabletResource;
    final Handler handler = new Handler();
    final long interval = 5000;

    final Runnable statusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                tabletResource.heartbeat();
            } finally {
                handler.postDelayed(statusChecker, interval);
            }
        }
    };


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