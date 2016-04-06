package com.onroute.android.data.resources;


import android.util.Log;

import com.onroute.android.data.models.SessionModel;
import com.onroute.android.data.models.media.MediaModel;
import com.onroute.android.network.websocket.Request;
import com.onroute.android.network.websocket.Websocket;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;


public class MediaResource extends BaseResource {
    @Getter List<MediaModel> movies = new ArrayList();
    @Inject Websocket websocket;


    public void watchMovie(MediaModel movie) {
        Request request = new Request("media", "watch_movie", movie);
        websocket.send(request);
    }


    public void updateMovieList() {
        Request request = new Request("media", "get_recommended_movies");
        websocket.send(request);
    }


    @Override
    public void onServerResponse(SessionModel session) {
        for (SessionModel.Command command : session.getCommands()) {
            Log.d("media", command.getOpcode());
            switch (command.getOpcode()) {
                case "UPDATE_MOVIE_LIST":
                    movies = gson.fromJson(command.getJson(), new TypeToken<List<MediaModel>>() {
                    }.getType());
                    if (movies == null) movies = new ArrayList<>();
            }
        }
    }
}