package com.enamakel.backseattester.data.models;


import com.enamakel.backseattester.data.models.base.BaseModel;

import java.io.File;

import lombok.Data;


@Data
public class AdvertisementModel extends BaseModel {
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
}
