package com.enamakel.backseattester.data.models;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import javax.inject.Inject;

import lombok.Getter;


public abstract class BaseModel {
    protected @Expose @Getter long id;

    @Expose @Inject Gson gson;


    public String toJSON() {
        return gson.toJson(this);
    }
}