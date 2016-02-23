package com.enamakel.backseattester.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.wifi.WifiConfiguration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.data.resources.PassengerResource;
import com.enamakel.backseattester.hotspot.ClientScanResult;
import com.enamakel.backseattester.hotspot.WifiApManager;
import com.enamakel.backseattester.views.CustomViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;


@EActivity(R.layout.activity_welcome)
public class WelcomeActivity extends AppCompatActivity {
    static final String TAG = WelcomeActivity.class.getSimpleName();

    @ViewById Button startButton;
    @ViewById Button stopButton;

    @Inject PassengerResource passengerResource;

    WifiApManager manager;
    WifiConfiguration configuration;
    Handler handler = new Handler();
    boolean continueChecking = true;

    final int interval = 500;
    final Runnable statusChecker = new Runnable() {
        @Override
        public void run() {
            if (!continueChecking) {
                stopHotspot();
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


    @AfterViews
    void afterViews() {
//        manager = new WifiApManager(getApplicationContext());
//
//        configuration = getConfiguration();
//        manager.setWifiApState(configuration, true);

        disableStatusBar();

        onUserConnected("AA:BB:CC:DD:EE:FF");
//        startButtonClicked();
    }


    /**
     * Get the height of the status bar. Either from programatically or a hard-coded default value.
     *
     * @return The height of the status bar.
     */
    float getStatusBarHeight() {
        float result = 50 * getResources().getDisplayMetrics().scaledDensity;

        // Attempt to get the height via default resources..
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }


    /**
     * Helper function to disable the status bar. This function does not disable notifications but
     * it disables access to the status bar by drawing a transparent box on it.
     */
    void disableStatusBar() {
        WindowManager manager = ((WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));

        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |

                // this is to enable the notification to receive touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = (int) (getStatusBarHeight());
        localLayoutParams.format = PixelFormat.TRANSPARENT;

        CustomViewGroup view = new CustomViewGroup(this);

        manager.addView(view, localLayoutParams);
    }


    WifiConfiguration getConfiguration() {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = getResources().getString(R.string.wifi_ssid);
        configuration.status = WifiConfiguration.Status.ENABLED;
        configuration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        return configuration;
    }


    @Click
    void startButtonClicked() {
        startHotspot();
        statusChecker.run();
    }


    @Click
    void stopButtonClicked() {
        stopHotspot();
    }


    @Background
    void startHotspot() {
        continueChecking = true;
        manager.setWifiApState(configuration, true);
    }


    @Background
    void stopHotspot() {
        continueChecking = false;
        handler.removeCallbacks(statusChecker);
        manager.setWifiApState(configuration, false);
    }


    @Background
    void refreshHotspot() {
        List<ClientScanResult> clients = manager.getClientList(true);

        if (clients.size() > 0) {
            Log.d(TAG, "got clients");

            for (ClientScanResult client : clients) {
                onUserConnected(client.getHWAddr());
                return;
            }

        } else startHotspot();
    }


    void onUserConnected(String macAddress) {
        Log.d(TAG, macAddress);

        // Stop checking the wifi, for now!
//        stopHotspot();

        // Register client here and goto next screen
//        passengerResource.checkin(macAddress);
        Intent intent = new Intent(this, TabbedActivity_.class);
        intent.putExtra(TabbedActivity.PASSENGER_MAC, macAddress);
        startActivity(intent);
    }
}