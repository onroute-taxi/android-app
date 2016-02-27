package com.enamakel.backseattester.activities;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.activities.base.InjectableActivity;
import com.enamakel.backseattester.hotspot.ClientScanResult;
import com.enamakel.backseattester.hotspot.Webserver;
import com.enamakel.backseattester.hotspot.WifiHotspot;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;


@EActivity(R.layout.activity_wifi_welcome)
public class WifiWelcomeActivity extends InjectableActivity {
    final static String TAG = WifiWelcomeActivity.class.getSimpleName();

    @ViewById FrameLayout contentFrame;
    @ViewById VideoView videoView;

    @Inject WifiHotspot wifiHotspot;

    Handler handler = new Handler();
    boolean continueChecking = true;
    final int interval = 1000;

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


    @AfterViews
    void afterViews() {
        String path = "android.resource://" + getPackageName() + "/" + R.raw.thrones;
        videoView.setVideoURI(Uri.parse(path));
        videoView.setOnPreparedListener(PreparedListener);

        // Start the Wifi htotspot
        startHotspot();
//        statusChecker.run();

        // Start the webserver
        try {
            Webserver.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Click
    void contentFrameClicked() {
        startHotspot();
    }


    @Background
    void startHotspot() {
        continueChecking = true;
        wifiHotspot.start();
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
        } //else if (wifiHotspot.isInactive() && !wifiHotspot.isStarting()) wifiHotspot.start();
    }


    void onUserConnected(String macAddress) {
        Log.d(TAG, macAddress);

        continueChecking = false;
        handler.removeCallbacks(statusChecker);

        // Register client here and goto next screen
        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TabbedActivity_.class);
        intent.putExtra(TabbedActivity.PASSENGER_MAC, macAddress);
        startActivity(intent);
    }


    MediaPlayer.OnPreparedListener PreparedListener = new MediaPlayer.OnPreparedListener() {

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = new MediaPlayer();
                }

                mediaPlayer.setVolume(0f, 0f);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
