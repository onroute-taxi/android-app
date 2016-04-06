package com.onroute.android.views.dashboard;


import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.onroute.android.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.dashboard_tile)
public class DashboardTileView extends RelativeLayout {
    @ViewById VideoView videoView;
    AttributeSet attributes;


    public DashboardTileView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        attributes = attributeSet;
    }


    @AfterViews
    void afterViews() {
//        String path = "android.resource://" + getContext().getPackageName() + "/" + R.raw.thrones;
//        videoView.setVideoURI(Uri.parse(path));
//        videoView.setOnPreparedListener(PreparedListener);

        View root = getRootView();
        ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
        layoutParams.height = attributes.getAttributeIntValue("app", "heightUnit", 0);
        root.setLayoutParams(layoutParams);
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