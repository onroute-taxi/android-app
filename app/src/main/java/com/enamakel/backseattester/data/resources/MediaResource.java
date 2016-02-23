package com.enamakel.backseattester.data.resources;


import android.util.Log;

import com.enamakel.backseattester.activities.TabbedActivity;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.data.models.media.MovieModel;
import com.enamakel.backseattester.websocket.Request;
import com.enamakel.backseattester.websocket.Websocket;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MediaResource extends BaseResource {
    public List<MovieModel> movies = new ArrayList();
    @Inject Websocket websocket;


    public void watchMovie(MovieModel movie) {
        Request request = new Request("media", "watch_movie", movie);
        websocket.send(request);
    }


    public void updateMovieList() {
        TabbedActivity.info("fetching new movies from the server");
        Request request = new Request("media", "get_recommended_movies");
        websocket.send(request);
    }


    @Override
    public void onSocketResponse(SessionModel session) {
        for (SessionModel.Command command : session.getCommands()) {
            Log.d("media", command.getOpcode());
            switch (command.getOpcode()) {
                case "UPDATE_MOVIE_LIST":
                    TabbedActivity.debug("updating movie list");
                    movies = gson.fromJson(command.getJson(), new TypeToken<List<MovieModel>>() {
                    }.getType());
            }
        }
    }
}