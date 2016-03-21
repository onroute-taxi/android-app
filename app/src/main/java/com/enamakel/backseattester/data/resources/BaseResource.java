package com.enamakel.backseattester.data.resources;


import android.util.Log;

import com.enamakel.backseattester.App;
import com.enamakel.backseattester.data.listeners.ResponseManager;
import com.google.gson.Gson;

import javax.inject.Inject;


public abstract class BaseResource implements ResponseManager.Listener {
    @Inject Gson gson;
    @Inject ResponseManager responseManager;


    public BaseResource() {
        App.getApplicationGraph().inject(this);
        Log.d("added", "yum " + this.getClass().getName());
        responseManager.addListener(this, this.getClass().getName());
    }
}