package com.onroute.android.network.websocket;


import com.onroute.android.App;
import com.onroute.android.data.models.base.BaseModel;
import com.onroute.android.data.models.PassengerModel;
import com.onroute.android.data.models.SessionModel;
import com.google.gson.annotations.Expose;

import javax.inject.Inject;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * This class represents a request. This is what we'll send to the websocket server.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Request extends BaseModel {
    @Expose String resource = "";
    @Expose String function = "";
    @Expose String data;
    @Expose @Inject SessionModel session;
    @Inject PassengerModel passenger;


    public Request(String resource, String function) {
        this(resource, function, null);
    }


    public Request(String resource, String function, Object data) {
        super();
        setFunction(function);
        setResource(resource);

        App.getApplicationGraph().inject(this);

        // Note: The session object is attached to every request. This makes requests more
        // uniform.
        session.setPassenger(passenger);
        setSession(session);

        // Gsonify the data object only if it is set.
        if (data != null) setData(gson.toJson(data));
    }
}