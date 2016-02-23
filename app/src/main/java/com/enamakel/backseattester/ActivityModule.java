package com.enamakel.backseattester;


import android.content.Context;
import android.util.Log;

import com.enamakel.backseattester.activities.SmsActivity_;
import com.enamakel.backseattester.activities.TabbedActivity;
import com.enamakel.backseattester.activities.TabbedActivity_;
import com.enamakel.backseattester.activities.WelcomeActivity;
import com.enamakel.backseattester.adapters.MovieListAdapter;
import com.enamakel.backseattester.data.models.PassengerModel;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.data.models.TabletModel;
import com.enamakel.backseattester.data.resources.JourneyResource;
import com.enamakel.backseattester.data.resources.MediaResource;
import com.enamakel.backseattester.data.resources.PassengerResource;
import com.enamakel.backseattester.data.resources.TabletResource;
import com.enamakel.backseattester.fragments.JourneyFragment_;
import com.enamakel.backseattester.fragments.PassengerFragment_;
import com.enamakel.backseattester.fragments.SessionFragment_;
import com.enamakel.backseattester.websocket.Websocket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(
        injects = {
                SmsActivity_.class,
                TabbedActivity_.class,
                WelcomeActivity.class,

                SessionFragment_.class,
//                DrawerActivity.class,
                JourneyFragment_.class,
                PassengerFragment_.class,


                JourneyResource.class,
                MediaResource.class,
                PassengerResource.class,
                TabletResource.class,
                Websocket.class,

                MovieListAdapter.class

        },
        library = true
)
public class ActivityModule {
    final static String socket_ip = "192.168.1.120";
    final Context context;
    final Gson gson;

    /**
     * This variable holds the session for the entire app! All resources will refer to this
     * object when dealing with the object's session.
     */
    final SessionModel session;


    public ActivityModule(Context context) {
        this.context = context;

        // Gson init
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        gson = builder.create();

        // Initialize the session variable
        session = new SessionModel();
        session.setPassenger(new PassengerModel());

        // Set the tablet variable!
        TabletModel tabletModel = TabletModel.getInstance();
        session.setTablet(tabletModel);
        tabletModel.initializeLocationListener(TabbedActivity.context);
    }


    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }


    @Provides
    @Singleton
    public Gson provideGson() {
        return gson;
    }


    @Provides
    @Singleton
    public SessionModel provideSessionModel() {
        return session;
    }


    @Provides
    @Singleton
    public MediaResource providesMediaResource() {
        return new MediaResource();
    }


    @Provides
    @Singleton
    public PassengerModel providesPassenger(SessionModel session) {
        return session.getPassenger();
    }


    @Provides
    @Singleton
    public Websocket providesWebsocket(Context context) {
        Log.d("ActivityModule", "providing websocket");
        try {
            URI uri = new URI("ws://" + socket_ip + ":1414");
            return new Websocket(uri, context);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
