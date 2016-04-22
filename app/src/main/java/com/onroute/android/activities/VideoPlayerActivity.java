package com.onroute.android.activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.onroute.android.R;
import com.onroute.android.activities.base.InjectableActivity;
import com.onroute.android.data.models.AdvertisementModel;
import com.onroute.android.data.models.media.MediaModel;
import com.onroute.android.services.InteractiveVideoAdService;

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
public class VideoPlayerActivity extends InjectableActivity {
    public static String EXTRA_MEDIA_MODEL = "MEDIA";

    @ViewById VideoView mainVideo;
    @ViewById TextView videoTitle;
    @ViewById SeekBar videoSeekBar;
    @ViewById SeekBar volumeSeekBar;
    @ViewById TextView timeLeft;

    private MediaModel media;

    private AdvertisementStatus advertisementStatus = AdvertisementStatus.NOT_PLAYED;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        media = getIntent().getParcelableExtra(EXTRA_MEDIA_MODEL);

        if (shouldShowAdBeforeVideo() &&
                advertisementStatus == AdvertisementStatus.NOT_PLAYED) {
            // Create an intent to display the ad!
            Intent intent = new Intent(this, InteractiveVideoAdService.class);
            intent.putExtra(InteractiveVideoAdService.EXTRA_AD, new AdvertisementModel());
            // Start the ad!
            startService(intent);

            advertisementStatus = AdvertisementStatus.PLAYING;

            registerReceiver(AdFinishReciever, new IntentFilter(InteractiveVideoAdService.AD_FINISH_INTENT));
        }
    }


    @AfterViews
    protected void afterViews() {
        // TODO this should not be null
        if (media != null) {
            // TODO: Figure out if we should play the pre-video ad here or not

            mainVideo.setVideoPath(media.getVideoPath());
            videoTitle.setText(media.getTitle());

            // If the advertisement is not playing, then we start the video automatically.
            if (advertisementStatus != AdvertisementStatus.PLAYING) mainVideo.start();

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

        }
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
        return true;
    }


    /**
     * Override the back functionality, so that we show an ad (if needed) before the user exits.
     */
    @Override
    public void onBackPressed() {
        if (shouldShowAdAfterVideo()) {

        } else super.onBackPressed();
    }


    private enum AdvertisementStatus {
        NOT_PLAYED,
        PLAYING,
        FINISHED
    }


    private final BroadcastReceiver AdFinishReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            advertisementStatus = AdvertisementStatus.FINISHED;
            mainVideo.start();
        }
    };
}