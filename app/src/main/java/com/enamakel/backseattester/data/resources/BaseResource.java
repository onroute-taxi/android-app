package com.enamakel.backseattester.data.resources;


import com.enamakel.backseattester.data.models.SessionModel;
import com.google.gson.Gson;

import javax.inject.Inject;


public abstract class BaseResource {
    @Inject Gson gson;


    /**
     * This function is called whenever a response is received from the server. The resource handler
     * is responsible for performing various tasks based on the data in the session object.
     *
     * @param session The session object from the server.
     */
    abstract void onSocketResponse(SessionModel session);
}