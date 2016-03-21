package com.enamakel.backseattester.data.models;


import com.enamakel.backseattester.App;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import javax.inject.Inject;

import lombok.Getter;


public abstract class BaseModel {
    protected @Expose @Getter long id;
    protected @Inject Gson gson;


    public BaseModel() {
//        App.getApplicationGraph().inject(this);
    }


    public String toJSON() {
        return gson.toJson(this);
    }
}