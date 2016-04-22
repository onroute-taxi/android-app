package com.onroute.android.activities.base;


import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.WindowManager;

import com.onroute.android.views.CustomViewGroup;


public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*// Obtain the shared Tracker instance.
        App application = (App) getApplication();
        tracker = application.getDefaultTracker();*/
        disableStatusBar();
    }


    @Override
    protected void onResume() {
        super.onResume();

        /*tracker.setScreenName(getTrackingName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());*/
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


    /**
     * Helper function to hide the system bars
     */
    protected void hideSystemUI() {
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN      // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);*/
    }
}