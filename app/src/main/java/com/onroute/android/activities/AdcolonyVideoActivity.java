package com.onroute.android.activities;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.onroute.android.R;
import com.onroute.android.activities.base.InjectableActivity;
import com.onroute.android.data.models.media.MediaModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_adcolony_video)
public class AdcolonyVideoActivity extends InjectableActivity {
    private static final String TAG = "MainActivity";
    public static String EXTRA_MEDIA_MODEL = AdcolonyVideoActivity.class.getName() + ".media";

    private int videoAdTouchCount = 0;
    private Thread backgroundThread;
    MediaPlayer mediaPlayer;

    @ViewById
    VideoView videoViewAd;

    @ViewById(R.id.relativelayout_video_minimized)
    RelativeLayout relativeLayout_video_minimized;

    @ViewById
    VideoView videoViewMinimized;

    @ViewById(R.id.linearlayout_skip)
    LinearLayout buttonSkipAd;

    @ViewById(R.id.relativelayout_main)
    RelativeLayout relativeLayout_main;

    /*@ViewById(R.id.relativelayout_progress)
    RelativeLayout relativeLayout_progress;*/

    @ViewById(R.id.cta_textview)
    TextView cta_textview;

    /*@ViewById(R.id.progress_bar)
    ProgressBar progressBar;*/

//    String video_name = "android.resource://" + getPackageName() + "/" + R.raw.advideo;

    MediaModel mediaModel;

    static int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (count++ % 3 != 0) {
            mediaModel = getIntent().getParcelableExtra(EXTRA_MEDIA_MODEL);

            Intent intent = new Intent(AdcolonyVideoActivity.this, VideoPlayerActivity_.class);
            intent.putExtra(VideoPlayerActivity.EXTRA_MEDIA_MODEL, mediaModel);
            startActivity(intent);
        }
    }


    @Click(R.id.linearlayout_skip)
    protected void buttonSkipAdClicked() {
        mediaModel = getIntent().getParcelableExtra(EXTRA_MEDIA_MODEL);

        Intent intent = new Intent(AdcolonyVideoActivity.this, VideoPlayerActivity_.class);
        intent.putExtra(VideoPlayerActivity.EXTRA_MEDIA_MODEL, mediaModel);
        startActivity(intent);
//        SkipAd();
    }


//    @Click(R.id.cta_textview)
    protected void cta_textviewClicked() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://in.bookmyshow.com/mumbai/movies/batman-v-superman-dawn-of-justice-3d/ET00030143"));
        startActivity(browserIntent);
    }


//    @Click(R.id.relativelayout_video_minimized)
    protected void replayVideoAd() {
        videoAdTouchCount = 0;
        videoViewMinimized.setVisibility(View.GONE);
        relativeLayout_video_minimized.setVisibility(View.GONE);
        cta_textview.setVisibility(View.GONE);
        buttonSkipAd.setVisibility(View.VISIBLE);
        relativeLayout_main.setBackgroundColor(getResources().getColor(R.color.black));
//        relativeLayout_progress.setVisibility(View.VISIBLE);
        videoViewAd.setVisibility(View.VISIBLE);
        videoViewAd.start();
    }


    @AfterViews
    protected void afterViews() {
        mediaModel = getIntent().getParcelableExtra(EXTRA_MEDIA_MODEL);
        String video_name = "android.resource://" + getPackageName() + "/" + R.raw.advideo;

        // To hide status and navigation bars
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        relativeLayout_video_minimized.setVisibility(View.GONE);
        videoViewMinimized.setVisibility(View.GONE);
        cta_textview.setVisibility(View.GONE);

        videoViewAd.setVideoPath(video_name);
        videoViewMinimized.setVideoPath(video_name);

        videoViewAd.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                videoViewAd.start();
                /*progressBar.setMax(mp.getDuration());
                final int max_duration = mp.getDuration();

                backgroundThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (mp.isPlaying()){
                            progressBar.setProgress(mp.getCurrentPosition());
//                            int countdown = max_duration - mp.getCurrentPosition();
//                            progress_text_view.setText(countdown);
//                            Log.d(TAG, String.valueOf(countdown));
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                backgroundThread.start();*/
            }
        });

        videoViewAd.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.reset();

                Intent intent = new Intent(AdcolonyVideoActivity.this, VideoPlayerActivity_.class);
                intent.putExtra(VideoPlayerActivity.EXTRA_MEDIA_MODEL, mediaModel);
                startActivity(intent);
//                SkipAd();
            }
        });

//
//        videoViewAd.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                /*videoViewAd.setEnabled(false);
//                buttonSkipAd.setVisibility(View.VISIBLE);
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        YoYo.with(Techniques.FadeOut).duration(500).playOn(findViewById(R.id.linearlayout_skip));
//                        videoViewAd.setEnabled(true);
//                    }
//                }, 3000);*/
//
//                if (videoAdTouchCount % 2 == 0) {
//                    YoYo.with(Techniques.FadeOutRight).duration(500).playOn(findViewById(R.id.linearlayout_skip));
//                    buttonSkipAd.setVisibility(View.GONE);
//                } else {
//                    buttonSkipAd.setVisibility(View.VISIBLE);
//                    YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.linearlayout_skip));
//
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            YoYo.with(Techniques.FadeOut).duration(500).playOn(findViewById(R.id.linearlayout_skip));
//                            videoAdTouchCount++;
//                        }
//                    }, 3000);
//
//                }
//                videoAdTouchCount++;
////                progressBar.setProgress(videoAdTouchCount*25);
//                return false;
//            }
//        });
    }


    protected void SkipAd() {
        videoViewAd.stopPlayback();
//        videoViewAd.suspend();
//        YoYo.with(Techniques.FadeOut)
//                .duration(5000)
//                .playOn(findViewById(R.id.video_view_ad));
        videoViewAd.setVisibility(VideoView.GONE);
        buttonSkipAd.setVisibility(View.GONE);
//        relativeLayout_progress.setVisibility(View.GONE);
        videoViewMinimized.setVisibility(View.VISIBLE);
        relativeLayout_video_minimized.setVisibility(View.VISIBLE);
        cta_textview.setVisibility(View.VISIBLE);
        relativeLayout_main.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.bvs_poster));
//        YoYo.with(Techniques.FadeIn)
//                .duration(5000)
//                .playOn(findViewById(R.id.video_view_minimized));

        videoViewMinimized.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoViewMinimized.start();
                mp.setLooping(true);
                mp.setVolume(0f, 0f);
                mp.start();
            }
        });
    }
}