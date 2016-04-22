package com.onroute.android.services;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import com.onroute.android.R;
import com.onroute.android.data.models.AdvertisementModel;

import java.io.File;


/**
 * This service is what is responsible for playing the interactive video ads. It takes in an
 * Advertisement model and ensures that the ad is displayed properly.
 */
public class InteractiveVideoAdService extends Service {
    public static final String EXTRA_AD = ".AD";
    private static final String TAG = InteractiveVideoAdService.class.getName();
    public static final String AD_FINISH_INTENT = InteractiveVideoAdService.class.getName() +
            ".AD_FINISH";

    private ViewState mCurrentViewState = ViewState.HIDDEN;
    private WindowManager mWindowManager;
    private View mRootView;

    private AdvertisementModel mAdvertisement;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if (intent == null) return START_NOT_STICKY;

        // Get the ad model.
        mAdvertisement = intent.getParcelableExtra(EXTRA_AD);

        // Attempt to see if the video has already been downloaded or not.
        if (mAdvertisement.getVideoFile() == null || !mAdvertisement.getVideoFile().exists()) {
            /* If the video was not downloaded, then we won't be able to give
             * a smooth HD experience (since another option for us would be to
             * stream the video). So bail... */
            return START_NOT_STICKY;
        }


        /*
         * Only if the view is hidden do we start showing the ad. This ensures that any previous
         * version of this service isin't interrupted.
         */
        if (mCurrentViewState == ViewState.HIDDEN) showView();

        return START_NOT_STICKY;
    }


    /**
     * Used to see if a video has been downloaded or not.
     *
     * @param path The url of the YouTube video.
     * @return True iff the video has been downloaded.
     */
    private static boolean isDownloaded(String path) {
        File file = new File(path);
        return file.exists();
    }


    /**
     * Hides the view from the window, and stops the service. Use this when you
     * are done displaying the ad.
     */
    private void finish() {
        // If the view is not yet removed, then hide it and remove it from the window.
        if (mCurrentViewState != ViewState.HIDDEN) {
            mCurrentViewState = ViewState.HIDDEN;
            if (mRootView != null && mWindowManager != null) mWindowManager.removeView(mRootView);
        }

        // Let any broadcast listeners know that the video has been played.
        Intent intent = new Intent(AD_FINISH_INTENT);
        sendBroadcast(intent);

        // With stopSelf there is a problem with the rotation. If this isn't in, the ad view will
        // not load
        this.stopSelf();
    }


    /**
     * Helper function to initialize the root view. The root view is what
     * contains the main content. This function basically populates it properlly
     * closes it too.
     * <p/>
     * If you want to modify what gets displayed on the view, then this is the
     * function you are looking for.
     *
     * @return A view object that should be placed in the new window.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("InflateParams")
    private View getRootView() {
        LayoutInflater li = LayoutInflater.from(this);

        // Setup the root view which will hold the video.
        View root = li.inflate(R.layout.item_video_ad, null);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        root.setSystemUiVisibility(uiOptions);
        root.setFocusable(true);
        root.setFocusableInTouchMode(true);
        root.setEnabled(false);

        // TODO: Inform the server that we are starting the video.

        // Initialize the VideoView with our video and attach a listen for when the video is done.
        VideoView videoview = (VideoView) root.findViewById(R.id.ad_video_view);
        videoview.setVideoPath(mAdvertisement.getVideoFile().getPath());
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO: Inform the server that the video has been played & send a broadcast

                // and close the view
                finish();
            }
        });

        // Start playing the video
        videoview.start();

        // Return the rootview so that it can be added to the window manager.
        return root;
    }


    /**
     * This helper function returns the layout parameters for the view. Modify
     * the parameters here if you want to change how the view appears.
     *
     * @return The parameters for the WindowManager.
     */
    private WindowManager.LayoutParams getLayoutParameters() {
        WindowManager.LayoutParams layoutParameters = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        // Give a fadeIn animation! Just like Toast notifications.
        layoutParameters.windowAnimations = android.R.style.Animation_Toast;

        // Always force in landscape mode!
        layoutParameters.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        return layoutParameters;
    }


    /**
     * This is the main function that is used to display the Ad. This function
     * setups different components and each of their initialization is placed in
     * their own functions.
     */
    private void showView() {
        // showView was called view was not hiddem. So it must still be in the
        // window hence remove it,
        if (this.mCurrentViewState != ViewState.HIDDEN && this.mWindowManager != null) {
            this.mWindowManager.removeView(this.mRootView);
        }

        // Set the state to 'SHOWING'
        this.mCurrentViewState = ViewState.SHOWING;

        // Create the view
        this.mRootView = this.getRootView();

        // Prepare the window manager and show the view
        this.mWindowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        this.mWindowManager.addView(this.mRootView, this.getLayoutParameters());

        // Now set the state of the view as 'SHOWN'
        this.mCurrentViewState = ViewState.SHOWN;
    }


    /**
     * Keeping track of the state of the view is a great way to avoid errors due
     * to multi-threading (if that's ever going to happen). This enum provides
     * the different states a view will have.
     */
    private enum ViewState {
        // The view is visible but not yet completely shown
        SHOWING,
        // The view has been completely displayed and the user is ready to
        // interact with it
        SHOWN,
        // The view is not visible to the user
        HIDDEN
    }
}