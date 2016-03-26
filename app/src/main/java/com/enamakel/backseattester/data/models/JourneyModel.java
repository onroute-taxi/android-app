package com.enamakel.backseattester.data.models;

import com.google.gson.annotations.Expose;

import lombok.Setter;


public class JourneyModel extends BaseModel {
    @Expose @Setter long created_at;
    @Expose @Setter long finished_at;
    @Expose @Setter Location source;
    @Expose @Setter Location destination;
}
