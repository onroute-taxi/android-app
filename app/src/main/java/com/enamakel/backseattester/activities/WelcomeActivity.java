package com.enamakel.backseattester.activities;


import android.net.wifi.WifiConfiguration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.hotspot.ClientScanResult;
import com.enamakel.backseattester.hotspot.WifiApManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;


@EActivity(R.layout.activity_welcome)
public class WelcomeActivity extends AppCompatActivity {
    @ViewById Button enterButton;
    static final String TAG = WelcomeActivity.class.getSimpleName();

    WifiApManager manager;
    WifiConfiguration configuration;
    Handler handler;

    final int interval = 10 * 1000;
    final Runnable statusChecker = new Runnable() {
        @Override
        public void run() {
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


    @AfterViews
    void afterViews() {
        manager = new WifiApManager(getApplicationContext());

        configuration = getConfiguration();
        manager.setWifiApState(configuration, true);

        handler = new Handler();
        statusChecker.run();
    }


    WifiConfiguration getConfiguration() {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = "taxicab";
        configuration.status = WifiConfiguration.Status.ENABLED;
        configuration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        return configuration;
    }


    @Click
    void enterButtonClicked() {
        refreshHotspot();
    }


    @Background
    void startHotspot() {
        manager.setWifiApState(configuration, true);
    }


    @Background
    void stopHotspot() {
        manager.setWifiApState(configuration, false);
    }


    @Background
    void refreshHotspot() {
        List<ClientScanResult> clients = manager.getClientList(true);

        if (clients.size() > 0) {
            Log.d(TAG, "got clients connected. stopping hotspot");
            stopHotspot();

            for (ClientScanResult client : clients) {
                Log.d(TAG, client.getHWAddr());

                // Register client here and goto next screen
            }
        } else startHotspot();
    }
}