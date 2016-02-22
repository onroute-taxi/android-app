package com.enamakel.backseattester.data.models;

import com.google.gson.annotations.Expose;

import lombok.Getter;


public abstract class BaseModel {
    protected @Expose @Getter long id;
}
