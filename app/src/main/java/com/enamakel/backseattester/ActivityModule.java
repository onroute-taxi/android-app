package com.enamakel.backseattester;


import android.content.Context;

import com.enamakel.backseattester.activities.SmsActivity_;
import com.enamakel.backseattester.activities.TabbedActivity_;
import com.enamakel.backseattester.activities.WelcomeActivity_;
import com.enamakel.backseattester.activities.WifiWelcomeActivity_;
import com.enamakel.backseattester.adapters.MovieListAdapter;
import com.enamakel.backseattester.data.listeners.ResponseManager;
import com.enamakel.backseattester.data.models.BaseModel;
import com.enamakel.backseattester.data.models.PassengerModel;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.data.models.TabletModel;
import com.enamakel.backseattester.data.resources.BaseResource;
import com.enamakel.backseattester.data.resources.JourneyResource;
import com.enamakel.backseattester.data.resources.MediaResource;
import com.enamakel.backseattester.data.resources.PassengerResource;
import com.enamakel.backseattester.data.resources.TabletResource;
import com.enamakel.backseattester.fragments.BaseFragment;
import com.enamakel.backseattester.fragments.JourneyFragment_;
import com.enamakel.backseattester.fragments.PassengerFragment_;
import com.enamakel.backseattester.fragments.SessionFragment_;
import com.enamakel.backseattester.network.hotspot.WifiHotspot;
import com.enamakel.backseattester.network.websocket.Request;
import com.enamakel.backseattester.network.websocket.Response;
import com.enamakel.backseattester.network.websocket.WebSocketClient;
import com.enamakel.backseattester.network.websocket.Websocket;
import com.enamakel.backseattester.services.DatabaseService;
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
                JourneyFragment_.class,

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
                PassengerModel.class,
                BaseModel.class,

                // Services
                DatabaseService.class,

                // Others
                MovieListAdapter.class,
                WifiHotspot.class,
        },
        library = true
)
public class ActivityModule {
    public final static String socket_ip = "192.168.1.124";
    final Context context;

    /**
     * This variable holds the session for the entire app! All resources will refer to this
     * object when dealing with the object's session.
     */
    SessionModel session;


    public ActivityModule(Context context) {
        this.context = context;
    }


    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }


    @Provides
    @Singleton
    public Gson provideGson() {
        // Gson init
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        return builder.create();
    }


    @Provides
    @Singleton
    public SessionModel provideSessionModel() {
        // Initialize the session variable
        session = new SessionModel();

        // Set the passenger!
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
        return new Websocket();
    }


    @Provides
    @Singleton
    public ResponseManager providesResponseManager() {
        return new ResponseManager();
    }
}