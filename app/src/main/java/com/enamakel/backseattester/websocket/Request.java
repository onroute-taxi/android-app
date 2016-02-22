package com.enamakel.backseattester.websocket;

import com.enamakel.backseattester.App;
import com.enamakel.backseattester.data.models.SessionModel;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import lombok.Setter;


/**
 * This class represents a request. This is what we'll send to the websocket server.
 */
public class Request {
    @Expose @Setter double id;
    @Expose @Setter String resource = "";
    @Expose @Setter String function = "";
    @Expose @Setter SessionModel session;
    @Expose @Setter String data;

    static Gson gson = App.gson;


    public static Request create(String resource, String function) {
        return Request.create(resource, function, null);
    }


    public static Request create(String resource, String function, Object data) {
        Request request = new Request();
        request.setFunction(function);
        request.setResource(resource);

        // Gsonify the data object only if it is set.
        if (data != null) request.setData(gson.toJson(data));

        // Note: The static session object is attached to every request. This makes requests more
        // uniform.
        request.setSession(App.session);

        return request;
    }
}