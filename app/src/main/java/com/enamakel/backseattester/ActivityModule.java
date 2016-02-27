package com.enamakel.backseattester;


import android.content.Context;

import com.enamakel.backseattester.activities.SmsActivity_;
import com.enamakel.backseattester.activities.TabbedActivity_;
import com.enamakel.backseattester.activities.WelcomeActivity_;
import com.enamakel.backseattester.activities.WifiWelcomeActivity_;
import com.enamakel.backseattester.adapters.MovieListAdapter;
import com.enamakel.backseattester.data.models.PassengerModel;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.data.models.TabletModel;
import com.enamakel.backseattester.data.resources.BaseResource;
import com.enamakel.backseattester.data.resources.JourneyResource;
import com.enamakel.backseattester.data.resources.MediaResource;
import com.enamakel.backseattester.data.resources.PassengerResource;
import com.enamakel.backseattester.data.resources.TabletResource;
import com.enamakel.backseattester.fragments.BaseFragment;
import com.enamakel.backseattester.fragments.PassengerFragment_;
import com.enamakel.backseattester.fragments.SessionFragment_;
import com.enamakel.backseattester.hotspot.WifiHotspot;
import com.enamakel.backseattester.websocket.Request;
import com.enamakel.backseattester.websocket.Response;
import com.enamakel.backseattester.websocket.WebSocketClient;
import com.enamakel.backseattester.websocket.Websocket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(
        injects = {
                // Activities
                SmsActivity_.class,
                TabbedActivity_.class,
                WelcomeActivity_.class,
                WifiWelcomeActivity_.class,

                // Fragments
                BaseFragment.class,
                SessionFragment_.class,
                PassengerFragment_.class,

                // Resources
                BaseResource.class,
                JourneyResource.class,
                MediaResource.class,
                PassengerResource.class,
                TabletResource.class,

                // Websocket
                Websocket.class,
                WebSocketClient.class,
                Request.class,
                Response.class,

                // Models
                SessionModel.class,
                TabletModel.class,

                MovieListAdapter.class,
                WifiHotspot.class,
        },
        library = true
)
public class ActivityModule {
    public final static String socket_ip = "192.168.1.120";
    final Context context;
    final Gson gson;
    final Websocket websocket;

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

        // Setup the websocket
        websocket = new Websocket(context, gson);
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
        // Initialize the session variable
        session.setPassenger(new PassengerModel());

        // Set the tablet variable!
        TabletModel tabletModel = new TabletModel(context);
        session.setTablet(tabletModel);
        tabletModel.initializeLocationListener(context);
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
    public TabletModel providesTablet(SessionModel session) {
        return session.getTablet();
    }


    @Provides
    @Singleton
    public Websocket providesWebsocket() {
        return websocket;
    }
}
