package com.enamakel.backseattester;


import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.util.FontCache;
import com.enamakel.backseattester.util.Preferences;
import com.google.android.gms.analytics.Tracker;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import dagger.ObjectGraph;


public class App extends Application {
    public static String HOST_ADDRESS = "192.168.1.120";
    public static Typeface TYPE_FACE = null;
    public static Typeface TYPE_FACE_BOLD = null;
    RefWatcher refWatcher;
    Tracker tracker;
    ObjectGraph applicationGraph;
    static Context context;


//    public static Gson gson;

//    /* Instances of the different resources */
//    public static TabletResource tabletResource;
//    public static JourneyResource journeyResource;
//    public static PassengerResource passengerResource;
//    public static MediaResource mediaResource;


    /**
     * This static variable holds the session for the entire app! All resources will refer to this
     * object when dealing with the object's session.
     */
    public static SessionModel session;


    static {
        // Gson init
//        final GsonBuilder builder = new GsonBuilder();
//        builder.excludeFieldsWithoutExposeAnnotation();
//        gson = builder.create();

        // Initialize the session variable
//        session = new SessionModel();
//
//         Set the tablet variable!
//        TabletModel tabletModel = TabletModel.getInstance();
//        session.setTablet(tabletModel);
//        tabletModel.initializeLocationListener(TabbedActivity.context);
    }


    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//
//            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//            tracker = analytics.newTracker(R.xml.global_tracker);
//
//            // Enable Display Features.
//            tracker.enableAdvertisingIdCollection(true);
        }
        return tracker;
    }


    public static RefWatcher getRefWatcher(Context context) {
        App applicationContext = (App) context.getApplicationContext();
        return applicationContext.refWatcher;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        refWatcher = LeakCanary.install(this);
        applicationGraph = ObjectGraph.create();
        Preferences.migrate(this);

        TYPE_FACE = FontCache.get(this, Preferences.Theme.getTypeface(this));
        TYPE_FACE_BOLD = FontCache.getBold(this, Preferences.Theme.getTypeface(this));

//        AppUtils.registerAccountsUpdatedListener(this);
    }


    public static Context getContext() {
        return context;
    }


    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
