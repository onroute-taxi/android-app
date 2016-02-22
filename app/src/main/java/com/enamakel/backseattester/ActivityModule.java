package com.enamakel.backseattester;


import android.content.Context;

import com.enamakel.backseattester.activities.SmsActivity_;
import com.enamakel.backseattester.adapters.MovieListAdapter;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.data.models.TabletModel;
import com.enamakel.backseattester.data.resources.JourneyResource;
import com.enamakel.backseattester.data.resources.MediaResource;
import com.enamakel.backseattester.data.resources.PassengerResource;
import com.enamakel.backseattester.data.resources.TabletResource;
import com.enamakel.backseattester.websocket.Websocket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(
        injects = {
                SmsActivity_.class,

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
}
