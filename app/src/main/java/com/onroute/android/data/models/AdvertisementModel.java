package com.onroute.android.data.models;


import com.onroute.android.data.models.base.BaseModel;

import java.io.File;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
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
