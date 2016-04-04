package com.enamakel.backseattester.data.models.base;


import com.enamakel.backseattester.App;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import lombok.Getter;


public abstract class BaseModel {
    protected @Expose @Getter long id;
    protected Gson gson;


    public BaseModel() {
        gson = App.get(Gson.class);
    }


    /**
     * Get the JSON representation of this object.
     *
     * @return The JSON string
     */
    public String toJSON() {
        return gson.toJson(this);
    }
}