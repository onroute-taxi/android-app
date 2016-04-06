package com.onroute.android.activities.base.dashboard;


import android.support.annotation.DrawableRes;


/**
 * Created by Eduard Albu on 18 03 2016
 * project ImageToXML
 *
 * @author eduard.albu@gmail.com
 */
public class DashboardTile {
    private int imageResId;


    public DashboardTile(@DrawableRes int imageResId) {
        this.imageResId = imageResId;
    }


    public int getImageResId() {
        return imageResId;
    }
}
