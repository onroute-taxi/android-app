package com.onroute.android;


import android.app.Application;
import android.content.Context;

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


    public static <T> T get(Class<T> type) {
        return applicationGraph.get(type);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        refWatcher = LeakCanary.install(this);

        // Setup the application graph
        applicationGraph = ObjectGraph.create().plus(new ActivityModule(this));
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}