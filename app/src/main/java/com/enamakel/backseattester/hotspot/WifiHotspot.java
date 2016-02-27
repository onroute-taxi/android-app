package com.enamakel.backseattester.hotspot;


import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.util.Log;

import com.enamakel.backseattester.R;

import java.util.List;

import javax.inject.Inject;


public class WifiHotspot {
    final static String TAG = WifiHotspot.class.getSimpleName();

    final WifiApManager manager;
    final Context context;
    final WifiConfiguration configuration;


    @Inject
    public WifiHotspot(Context context) {
        this.context = context;
        manager = new WifiApManager(context);
        configuration = getConfiguration();
    }


    public void start() {
        Log.d(TAG, "starting hotspot");
        manager.setWifiApState(configuration, true);
    }


    public void stop() {
        Log.d(TAG, "stopping hotspot");
        manager.setWifiApState(configuration, false);
    }


    public boolean isRunning() {
        return manager.getWifiApState() == WifiApState.WIFI_AP_STATE_ENABLED ||
                manager.getWifiApState() == WifiApState.WIFI_AP_STATE_ENABLING;
    }


    public boolean isInactive() {
        return manager.getWifiApState() == WifiApState.WIFI_AP_STATE_DISABLED;
    }


    public boolean isStarting() {
        return manager.getWifiApState() == WifiApState.WIFI_AP_STATE_DISABLED ||
                manager.getWifiApState() == WifiApState.WIFI_AP_STATE_DISABLING ||
                manager.getWifiApState() == WifiApState.WIFI_AP_STATE_ENABLING;
    }


    public List<ClientScanResult> getClients() {
        return manager.getClientList(true);
    }


    WifiConfiguration getConfiguration() {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = context.getResources().getString(R.string.wifi_ssid);
        configuration.status = WifiConfiguration.Status.ENABLED;
        configuration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        return configuration;
    }
}
