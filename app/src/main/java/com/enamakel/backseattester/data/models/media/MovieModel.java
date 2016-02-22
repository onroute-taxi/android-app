package com.enamakel.backseattester.data.models.media;

import com.enamakel.backseattester.data.models.BaseModel;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

public class MovieModel extends BaseModel {
    @Expose @Getter @Setter String title;
    @Expose @Getter @Setter String studio;
    @Expose @Getter @Setter String rating;
    @Expose @Getter @Setter int year;
    @Expose @Getter @Setter String genre;
}