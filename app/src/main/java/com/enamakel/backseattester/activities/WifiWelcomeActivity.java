package com.enamakel.backseattester.activities;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.activities.base.InjectableActivity;
import com.enamakel.backseattester.activities.dashboard.DashboardActivity_;
import com.enamakel.backseattester.network.hotspot.ClientScanResult;
import com.enamakel.backseattester.network.hotspot.Webserver;
import com.enamakel.backseattester.network.hotspot.WifiHotspot;
import com.enamakel.backseattester.services.DatabaseService;
import com.enamakel.backseattester.util.EaseInOutQuintInterpolator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;


@EActivity(R.layout.activity_wifi_welcome)
public class WifiWelcomeActivity extends InjectableActivity {
    final static String TAG = WifiWelcomeActivity.class.getSimpleName();

    @ViewById FrameLayout contentFrame;
    @ViewById LinearLayout promptFrame;
    @ViewById VideoView videoView;
    @ViewById LinearLayout wifiInfo;
    @ViewById TextView welcomeText;
    @ViewById TextView instructionText;
    @ViewById TextView smsText;
    @ViewById View loginAdvanced;

    @Inject WifiHotspot wifiHotspot;

    Handler handler = new Handler();
    boolean continueChecking = true;
    final int interval = 1000;
    boolean hasAnimated = false;

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
        videoView.setVideoURI(Uri.parse("/sdcard/raymond-s1e1.avi"));
        videoView.setOnPreparedListener(PreparedListener);

        String welcomeHtmlText = "<font color=#FFFFFF>Connect to the </font> <font color=#FF9800>OnRoute</font> " +
                "<font color=#FFFFFF>Wifi to start</font>";
        welcomeText.setText(Html.fromHtml(welcomeHtmlText));


        String instHtmlText = "<font color=#FFFFFF>Connect to the</font> <font color=#FF9800>OnRoute</font> " +
                "<font color=#FFFFFF>wifi from your phone to start</font>";
        instructionText.setText(Html.fromHtml(instHtmlText));

        String smsHtmlText = "<font color=#FFFFFF>SMS</font> <font color=#FF9800>\"OnRoute\"</font> " +
                "<font color=#FFFFFF>to 9819254358</font>";
        smsText.setText(Html.fromHtml(smsHtmlText));
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Start the database service!
        if (!DatabaseService.isRunning(this))
            startService(new Intent(this, DatabaseService.class));

        handler.removeCallbacks(statusChecker);

        // Start the Wifi htotspot
        startHotspot();
        statusChecker.run();

        // Start the webserver
        try {
            Webserver.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Click
    void contentFrameClicked() {
        Log.d(TAG, "show animation");
//        onUserConnected("AA:BB:CC:DD:EE:FF");
        if (hasAnimated) return;
        hasAnimated = true;

        animateBarFullscreen();
//        fadeWifiInfoOut();
    }


    @Click(R.id.sms_text)
    void skip() {
        onUserConnected("AA:BB:CC:DD:EE:FF");
    }


    void fadeWifiInfoOut() {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);
        welcomeText.startAnimation(fadeOut);
    }


    void animateBarFullscreen() {
        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)
                promptFrame.getLayoutParams();

        final int oldMargin = params.bottomMargin;
        final int oldHeight = promptFrame.getMeasuredHeight();
        final int parentHeight = contentFrame.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                params.bottomMargin = (int) (oldMargin * (1f - interpolatedTime));
                params.height = (int) (oldHeight + (parentHeight - oldHeight) * interpolatedTime);
                promptFrame.setLayoutParams(params);
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }


            @Override
            public void onAnimationEnd(Animation animation) {
                wifiInfo.setVisibility(View.VISIBLE);
                promptFrame.setVisibility(View.GONE);

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(250);
                loginAdvanced.startAnimation(fadeIn);
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(500);
        animation.setInterpolator(new EaseInOutQuintInterpolator(3));
        promptFrame.startAnimation(animation);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);
        welcomeText.startAnimation(fadeOut);

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
            for (ClientScanResult client : clients) {
                onUserConnected(client.getHWAddr());

                continueChecking = false;
                wifiHotspot.stop();
                // stop the loop immediately and return. We only need one client.
                return;
            }
        } //else if (wifiHotspot.isInactive() && !wifiHotspot.isStarting()) wifiHotspot.start();
    }


    @UiThread
    void onUserConnected(String macAddress) {
        Log.d(TAG, macAddress);

        continueChecking = false;
        handler.removeCallbacks(statusChecker);
        passengerResource.checkin(macAddress);

        // Register client here and goto next screen
        Intent intent = new Intent(this, DashboardActivity_.class);
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
