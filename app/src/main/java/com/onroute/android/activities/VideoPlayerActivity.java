package com.onroute.android.activities;


import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.onroute.android.R;
import com.onroute.android.data.models.AdvertisementModel;
import com.onroute.android.data.models.media.MediaModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;


/**
 * This Activity simply is responsible for playing a video. It has two advertisement slots. One
 * before the video is played and one after the video is finished.
 * <p/>
 * Ideally the ad before the video is played should be a short video and the ad after the video
 * should be an interactive ad.
 */
@EActivity(R.layout.activity_video_player)
public class VideoPlayerActivity extends Activity {
    public static String EXTRA_MEDIA_MODEL = "MEDIA";

    @ViewById VideoView mainVideo;
    @ViewById TextView videoTitle;
    @ViewById SeekBar videoSeekBar;
    @ViewById SeekBar volumeSeekBar;
    @ViewById TextView timeLeft;

    // For this activity there are two advertisement slots.
    AdvertisementModel preVideoAdvertisement;
    AdvertisementModel postVideoAdvertisement;

    private List<AdvertisementModel> mAdvertisementSlots;

    // This runnable will keep on updating the progressbar.
    // see http://stackoverflow.com/questions/7731354/how-to-use-a-seekbar-in-android-as-a-seekbar-as-well-as-a-progressbar-simultaneo
    private Runnable onEverySecond = new Runnable() {
        @Override
        public void run() {
            if (videoSeekBar != null) videoSeekBar.setProgress(mainVideo.getCurrentPosition());
            if (mainVideo.isPlaying()) videoSeekBar.postDelayed(onEverySecond, 1000);
        }
    };


    @AfterViews
    protected void afterViews() {
        MediaModel media = getIntent().getParcelableExtra(EXTRA_MEDIA_MODEL);

        // TODO: Figure out if we should play the pre-video ad here or not

        mainVideo.setVideoPath(media.getVideoPath());
        mainVideo.start();
        videoTitle.setText(media.getTitle());

        setupVolumeBar();
        setupVideoSeekBar();

        // TODO: Setup other parts of the activity (animation, timer etc..)

        mainVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoSeekBar.setMax(mainVideo.getDuration());
                videoSeekBar.postDelayed(onEverySecond, 1000);
            }
        });

        // TODO: Once the video end, go back to the previous activity.

    }


    /**
     * Setup the volume bar to control the device's volume
     */
    private void setupVolumeBar() {
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volumeSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }


            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }


            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }
        });
    }


    /**
     * Initialize the video seek bar to scroll through the video when the seek bar's position has
     * changed.
     */
    private void setupVideoSeekBar() {
        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // this is when actually seekbar has been seeked to a new position
                if (fromUser) mainVideo.seekTo(progress);
            }
        });
    }


    @Click
    protected void playButtonClicked() {
        /* TODO implement this */
    }


    @Click
    protected void backButtonClicked() {
        /* TODO implement this */
    }


    @Click
    protected void nextButtonClicked() {
        /* TODO implement this */
    }


    /**
     * Calculate and decide if we should show an ad after the video is over/nearing completion.
     * <p/>
     * TODO: implement this
     *
     * @return A {@link Boolean} if an ad should be shown.
     */
    private boolean shouldShowAdAfterVideo() {
        return false;
    }


    /**
     * Calculate and decide if we should show an ad before the video even start.
     * <p/>
     * TODO: implement this
     *
     * @return A {@link Boolean} if an ad should be shown.
     */
    private boolean shouldShowAdBeforeVideo() {
        return false;
    }


    /**
     * Override the back functionality, so that we show an ad (if needed) before the user exits.
     */
    @Override
    public void onBackPressed() {
        if (shouldShowAdAfterVideo()) {

        } else super.onBackPressed();
    }
}