package com.enamakel.backseattester.activities;


import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.activities.base.InjectableActivity;
import com.enamakel.backseattester.network.hotspot.ClientScanResult;
import com.enamakel.backseattester.network.hotspot.WifiHotspot;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import javax.inject.Inject;


@EActivity(R.layout.activity_welcome)
public class WelcomeActivity extends InjectableActivity {
    static final String TAG = WelcomeActivity.class.getSimpleName();

    Handler handler = new Handler();
    boolean continueChecking = true;
    final int interval = 500;

    @Inject WifiHotspot wifiHotspot;

    final Runnable statusChecker = new Runnable() {
        @Override
        public void run() {
            if (!continueChecking) {
                handler.removeCallbacks(statusChecker);
                return;
            }

            try {
                Log.d(TAG, "refresing hotspot");
                refreshHotspot();
            } finally {
                // 100% guarantee that this always happens, even if your update method throws an
                // exception
                handler.postDelayed(statusChecker, interval);
            }
        }
    };


    @Background
    void startHotspot() {
        continueChecking = true;
        wifiHotspot.start();
        statusChecker.run();
    }


    @Background
    void refreshHotspot() {
        List<ClientScanResult> clients = wifiHotspot.getClients();

        if (clients.size() > 0) {
            Log.d(TAG, "got clients");

            for (ClientScanResult client : clients) {
                onUserConnected(client.getHWAddr());
                return;
            }

        } else if(!wifiHotspot.isRunning()) startHotspot();
    }


    void onUserConnected(String macAddress) {
        Log.d(TAG, macAddress);

        // Stop checking the wifi, for now!
        continueChecking = false;
        handler.removeCallbacks(statusChecker);

        // Register client here and goto next screen
        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TabbedActivity_.class);
        intent.putExtra(TabbedActivity.PASSENGER_MAC, macAddress);
        startActivity(intent);
    }
}