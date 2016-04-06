package com.onroute.android.data.models.media;


import android.os.Parcel;
import android.os.Parcelable;

import com.onroute.android.App;
import com.onroute.android.data.models.base.BaseParcelableModel;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class MediaModel extends BaseParcelableModel {
    @Expose String title;
    @Expose String studio;
    @Expose String rating;
    @Expose int year;
    @Expose String genre;
    @Expose String description;
    @Expose String videoPath;
    @Expose String imagePath;


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MediaModel createFromParcel(Parcel in) {
            String json = in.readString();
            Gson gson = App.get(Gson.class);
            return gson.fromJson(json, MediaModel.class);
        }


        public MediaModel[] newArray(int size) {
            return new MediaModel[size];
        }
    };
}