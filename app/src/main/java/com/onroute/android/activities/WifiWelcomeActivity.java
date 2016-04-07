package com.onroute.android.activities;


import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.onroute.android.R;
import com.onroute.android.activities.base.InjectableActivity;
import com.onroute.android.activities.dashboard.DashboardActivity_;
import com.onroute.android.network.hotspot.ClientScanResult;
import com.onroute.android.network.hotspot.Webserver;
import com.onroute.android.network.hotspot.WifiHotspot;
import com.onroute.android.services.DatabaseService;
import com.onroute.android.util.EaseInOutQuintInterpolator;

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
    private final static String TAG = WifiWelcomeActivity.class.getSimpleName();

    @ViewById FrameLayout contentFrame;
    @ViewById LinearLayout promptFrame;
    @ViewById VideoView videoView;
    @ViewById LinearLayout advancedFrame;
    @ViewById TextView welcomeText;
    @ViewById TextView instructionText;
    @ViewById TextView smsText;
    @ViewById View loginAdvanced;
    @ViewById View loginPersonas;
    @ViewById View personas;
    @ViewById ImageView welcomeIcon;

    @Inject WifiHotspot wifiHotspot;

    private BluetoothAdapter adapter;

    static String wifiName;

    private Handler handler = new Handler();
    private boolean continueChecking = true;
    private final int interval = 1000;
    private boolean hasAnimated = false;
    private final Runnable statusChecker = new Runnable() {
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

    static {
        wifiName = "Login to Onroute - " + Math.floor(Math.random() * 100);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        super.onCreate(savedInstanceState);
    }


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

        // Start the Wifi hotspot
        startHotspot();
        statusChecker.run();
        wifiHotspot.setName(wifiName);

        // Start the web-server
        try {
            Webserver.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Click
    void contentFrameClicked() {
        Log.d(TAG, "show animation");
        if (hasAnimated) return;
        hasAnimated = true;

        animateBarFullscreen();
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
                advancedFrame.setVisibility(View.VISIBLE);
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
        welcomeIcon.startAnimation(fadeOut);

    }


    @Click(R.id.sms_text)
    protected void onPhoneVerified() {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(250);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }


            @Override
            public void onAnimationEnd(Animation animation) {
                loginPersonas.setVisibility(View.VISIBLE);
                loginAdvanced.setVisibility(View.GONE);

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(250);
                loginPersonas.startAnimation(fadeIn);
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        loginAdvanced.startAnimation(fadeOut);
    }


    @Click({
            R.id.login_personas,
            R.id.persona_airport,
            R.id.persona_bar,
            R.id.persona_camera,
            R.id.persona_office,
            R.id.persona_other,
            R.id.persona_rest
    })
    protected void onPersonasCollected() {
        Toast.makeText(this, "hit", Toast.LENGTH_LONG);
        // Register client here and goto next screen
        Intent intent = new Intent(this, DashboardActivity_.class);
        startActivity(intent);
    }


    @Background
    protected void startHotspot() {
        continueChecking = true;
        wifiHotspot.start();
    }


    @Background
    protected void refreshHotspot() {
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
    protected void onUserConnected(String macAddress) {
        Log.d(TAG, macAddress);

        continueChecking = false;
        handler.removeCallbacks(statusChecker);
        passengerResource.checkin(macAddress);

        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }


            @Override
            public void onAnimationEnd(Animation animation) {
                advancedFrame.setVisibility(View.VISIBLE);
                promptFrame.setVisibility(View.GONE);

                loginAdvanced.setVisibility(View.GONE);
                loginPersonas.setVisibility(View.VISIBLE);

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(250);
                loginPersonas.startAnimation(fadeIn);
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        promptFrame.startAnimation(animation);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);
        welcomeText.startAnimation(fadeOut);
        welcomeIcon.startAnimation(fadeOut);
    }


    private MediaPlayer.OnPreparedListener PreparedListener = new MediaPlayer.OnPreparedListener() {

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
