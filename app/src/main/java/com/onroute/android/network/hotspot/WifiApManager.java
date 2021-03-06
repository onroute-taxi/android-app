package com.onroute.android.network.hotspot;


import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;


public class WifiApManager {
    final WifiManager wifiManager;
    final static String TAG = WifiApManager.class.getSimpleName();

    Method wifiControlMethod;
    Method wifiApConfigurationMethod;
    Method wifiApState;


    public WifiApManager(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        try {
            wifiControlMethod = wifiManager.getClass().getMethod("setWifiApEnabled",
                    WifiConfiguration.class, boolean.class);
            wifiApConfigurationMethod = wifiManager.getClass().getMethod("getWifiApConfiguration", null);
            wifiApState = wifiManager.getClass().getMethod("getWifiApState");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public WifiApState getWifiApState() {
        try {
            Method method = wifiManager.getClass().getMethod("getWifiApState");
            int tmp = ((Integer) method.invoke(wifiManager));
            tmp %= 10;
            Log.d(TAG, "" + tmp);
            // Fix for Android 4

            if (tmp >= WifiApState.class.getEnumConstants().length)
                return WifiApState.WIFI_AP_STATE_FAILED;

            return WifiApState.class.getEnumConstants()[tmp];
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "", e);
            return WifiApState.WIFI_AP_STATE_FAILED;
        }
    }


    public boolean setWifiApState(WifiConfiguration config, boolean enabled) {
        try {

            if (enabled) {
//                int res = wifiManager.addNetwork(config);
//                wifiManager.enableNetwork(res, true);
                wifiManager.setWifiEnabled(!enabled);
//                return true;
            }


            return (Boolean) wifiControlMethod.invoke(wifiManager, config, enabled);
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return false;
        }
    }


    public WifiConfiguration getWifiApConfiguration() {
        try {
            return (WifiConfiguration) wifiApConfigurationMethod.invoke(wifiManager, null);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Gets a list of the clients connected to the Hotspot, reachable timeout is 300
     *
     * @param onlyReachables {@code false} if the list should contain unreachable (probably
     *                       disconnected) clients, {@code true} otherwise
     * @return ArrayList of {@link ClientScanResult}
     */
    public ArrayList<ClientScanResult> getClientList(boolean onlyReachables) {
        return getClientList(onlyReachables, 300);
    }


    /**
     * Gets a list of the clients connected to the Hotspot
     *
     * @param onlyReachables   {@code false} if the list should contain unreachable (probably
     *                         disconnected) clients, {@code true} otherwise
     * @param reachableTimeout Reachable Timout in miliseconds
     * @return ArrayList of {@link ClientScanResult}
     */
    public ArrayList<ClientScanResult> getClientList(boolean onlyReachables, int reachableTimeout) {
        BufferedReader reader = null;
        ArrayList<ClientScanResult> result = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split(" +");

                if ((splitted != null) && (splitted.length >= 4)) {
                    // Basic sanity check
                    String mac = splitted[3];

                    if (mac.matches("..:..:..:..:..:..")) {
                        boolean isReachable = InetAddress.getByName(splitted[0]).isReachable(reachableTimeout);

                        if (!onlyReachables || isReachable) {
                            result.add(new ClientScanResult(splitted[0], splitted[3], splitted[5], isReachable));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "" + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }
}