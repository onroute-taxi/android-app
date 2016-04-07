package com.onroute.android.data.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.onroute.android.App;
import com.onroute.android.data.models.base.BaseParcelableModel;

import java.io.File;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class AdvertisementModel extends BaseParcelableModel {
    String data;


    public File getVideoFile() {
        return new File("/sdcard/ad.mp4");
    }


    public enum Type {
        SHORT_VIDEO,
        INTERACTIVE,
        TICKER
    }


    public enum Slots {
        PRE_VIDEO,
        POST_VIDEO
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AdvertisementModel createFromParcel(Parcel in) {
            String json = in.readString();
            Gson gson = App.get(Gson.class);
            return gson.fromJson(json, AdvertisementModel.class);
        }


        public AdvertisementModel[] newArray(int size) {
            return new AdvertisementModel[size];
        }
    };
}
