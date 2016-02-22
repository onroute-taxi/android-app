package com.enamakel.backseattester.activities.base;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public abstract class BaseActivity extends AppCompatActivity {
    static String TAG = "BaseActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // Obtain the shared Tracker instance.
//        NewsApplication application = (NewsApplication) getApplication();
//        tracker = application.getDefaultTracker();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        Log.i(TAG, "Setting screen name: " + getTrackingName());
//        tracker.setScreenName(getTrackingName());
//        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}