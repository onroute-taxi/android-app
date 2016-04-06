package com.onroute.android.activities.base;


import android.os.Bundle;

import com.onroute.android.App;
import com.onroute.android.data.resources.JourneyResource;
import com.onroute.android.data.resources.MediaResource;
import com.onroute.android.data.resources.PassengerResource;
import com.onroute.android.data.resources.TabletResource;
import com.onroute.android.network.websocket.Websocket;
import com.onroute.android.util.Injectable;

import javax.inject.Inject;

import dagger.ObjectGraph;


public abstract class InjectableActivity extends ThemedActivity implements Injectable {
    ObjectGraph activityGraph;
    boolean isDestroyed;

    protected @Inject Websocket websocket;

    protected @Inject PassengerResource passengerResource;
    protected @Inject MediaResource mediaResource;
    protected @Inject JourneyResource journeyResource;
    protected @Inject TabletResource tabletResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGraph = ((App) getApplication()).getApplicationGraph();
        activityGraph.inject(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
        activityGraph = null;
    }


    @Override
    public void onBackPressed() {
        // TODO http://b.android.com/176265
        try {
            super.onBackPressed();
        } catch (IllegalStateException e) {
            supportFinishAfterTransition();
        }
    }


    @Override
    public void inject(Object object) {
        activityGraph.inject(object);
    }


    public boolean isActivityDestroyed() {
        return isDestroyed;
    }
}
