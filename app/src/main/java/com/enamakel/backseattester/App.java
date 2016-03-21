package com.enamakel.backseattester;


import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.Tracker;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import dagger.ObjectGraph;
import lombok.Getter;


public class App extends Application {
    RefWatcher refWatcher;

    @Getter static ObjectGraph applicationGraph;


    public static RefWatcher getRefWatcher(Context context) {
        App applicationContext = (App) context.getApplicationContext();
        return applicationContext.refWatcher;
    }


    public static <T> T inject(T instance) {
        if (applicationGraph == null) return null;
        return applicationGraph.inject(instance);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        refWatcher = LeakCanary.install(this);

        // Setup the application graph
        applicationGraph = ObjectGraph.create().plus(new ActivityModule(this));
    }
}