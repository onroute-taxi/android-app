package com.enamakel.backseattester.activities.base;


import android.os.Bundle;

import com.enamakel.backseattester.ActivityModule;
import com.enamakel.backseattester.App;
import com.enamakel.backseattester.util.Injectable;

import dagger.ObjectGraph;


public abstract class InjectableActivity extends ThemedActivity implements Injectable {
    ObjectGraph activityGraph;
    boolean isDestroyed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityGraph = ((App) getApplication()).getApplicationGraph()
                .plus(new ActivityModule(this));
        activityGraph.inject(this);
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
