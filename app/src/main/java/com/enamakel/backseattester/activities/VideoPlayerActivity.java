package com.enamakel.backseattester.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.activities.dashboard.DashboardActivity_;
import com.enamakel.backseattester.data.models.media.MediaModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_video_player)
public class VideoPlayerActivity extends Activity {
    public static String EXTRA_MEDIA_MODEL = VideoPlayerActivity.class.getName() + ".media";

    @ViewById VideoView mainVideo;
    @ViewById TextView videoTitle;
    @ViewById SeekBar videoSeekBar;
    @ViewById SeekBar volumeSeekBar;
    @ViewById TextView timeLeft;

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
    void afterViews() {
        Intent intent = getIntent();
        MediaModel media = intent.getParcelableExtra(EXTRA_MEDIA_MODEL);
        mainVideo.setVideoPath(media.getVideoPath());
        mainVideo.start();

        videoTitle.setText(media.getTitle());

        setupVolumeBar();
        setupVideoSeekBar();

        mainVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoSeekBar.setMax(mainVideo.getDuration());
                videoSeekBar.postDelayed(onEverySecond, 1000);
            }
        });
    }


    void setupVolumeBar() {
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volumeSeekBar.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeSeekBar.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));
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


    void setupVideoSeekBar() {
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


    @Click void playButtonClicked() {
        /* TODO implement this */
    }


    @Click void backButtonClicked() {
        /* TODO implement this */
    }


    @Click void nextButtonClicked() {
        /* TODO implement this */
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(this, DashboardActivity_.class));
    }
}