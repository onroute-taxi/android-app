package com.onroute.android;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onroute.android.activities.VideoPlayerActivity_;
import com.onroute.android.activities.WelcomeActivity_;
import com.onroute.android.activities.dashboard.DashboardActivity_;
import com.onroute.android.data.listeners.ResponseManager;
import com.onroute.android.data.models.PassengerModel;
import com.onroute.android.data.models.SessionModel;
import com.onroute.android.data.models.TabletModel;
import com.onroute.android.data.models.base.BaseModel;
import com.onroute.android.data.resources.BaseResource;
import com.onroute.android.data.resources.JourneyResource;
import com.onroute.android.data.resources.MediaResource;
import com.onroute.android.data.resources.PassengerResource;
import com.onroute.android.data.resources.TabletResource;
import com.onroute.android.fragments.DrawerFragment;
import com.onroute.android.fragments.PassengerFragment_;
import com.onroute.android.fragments.SessionFragment_;
import com.onroute.android.fragments.base.BaseFragment;
import com.onroute.android.network.hotspot.WifiHotspot;
import com.onroute.android.network.websocket.Request;
import com.onroute.android.network.websocket.Response;
import com.onroute.android.network.websocket.WebSocketClient;
import com.onroute.android.network.websocket.Websocket;
import com.onroute.android.services.DatabaseService;

import javax.inject.Singleton;

import auto.parcelgson.gson.AutoParcelGsonTypeAdapterFactory;
import dagger.Module;
import dagger.Provides;


@Module(
        injects = {
                // Activities
                WelcomeActivity_.class,
                DashboardActivity_.class,
                VideoPlayerActivity_.class,

                // Fragments
                BaseFragment.class,
                SessionFragment_.class,
                PassengerFragment_.class,
                DrawerFragment.class,

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
                WifiHotspot.class,
                Gson.class
        },
        library = true
)
public class ActivityModule {
    public final static String socket_ip = "162.208.10.149";
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
        final GsonBuilder builder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory());
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