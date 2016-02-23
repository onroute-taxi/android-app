package com.enamakel.backseattester.websocket;


import com.enamakel.backseattester.App;
import com.enamakel.backseattester.data.models.BaseModel;
import com.enamakel.backseattester.data.models.SessionModel;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import javax.inject.Inject;

import lombok.Setter;


/**
 * This class represents a request. This is what we'll send to the websocket server.
 */
public class Request extends BaseModel {
    @Expose @Setter double id;
    @Expose @Setter String resource = "";
    @Expose @Setter String function = "";
    @Expose @Setter SessionModel session;
    @Expose @Setter String data;

    @Inject static Gson gson;


    public Request(String resource, String function) {
        this(resource, function, null);
    }


    public Request(String resource, String function, Object data) {
        setFunction(function);
        setResource(resource);

        // Gsonify the data object only if it is set.
        if (data != null) setData(gson.toJson(data));

        // Note: The static session object is attached to every request. This makes requests more
        // uniform.
        setSession(App.session);
    }
}