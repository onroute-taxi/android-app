package com.enamakel.backseattester.websocket;


import com.enamakel.backseattester.App;
import com.enamakel.backseattester.data.models.BaseModel;
import com.enamakel.backseattester.data.models.SessionModel;
import com.google.gson.annotations.Expose;

import javax.inject.Inject;

import lombok.Data;


/**
 * This class represents a request. This is what we'll send to the websocket server.
 */
@Data
public class Request extends BaseModel {
    @Expose String resource = "";
    @Expose String function = "";
    @Expose @Inject SessionModel session;
    @Expose String data;


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
        App.getApplicationGraph().inject(this);
//        setSession(App.getApplicationGraph().get(SessionModel.class));
    }
}