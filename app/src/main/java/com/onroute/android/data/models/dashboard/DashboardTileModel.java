package com.onroute.android.data.models.dashboard;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.google.gson.Gson;
import com.onroute.android.App;
import com.onroute.android.data.models.base.BaseParcelableModel;

import lombok.Data;


@Data
public class DashboardTileModel extends BaseParcelableModel {
    String localBackgroundImagePath;
    @DrawableRes int iconRes;
    String title;
    String description;
    OnClickListener onClickListener;


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DashboardTileModel createFromParcel(Parcel in) {
            String json = in.readString();
            Gson gson = App.get(Gson.class);
            return gson.fromJson(json, DashboardTileModel.class);
        }


        public DashboardTileModel[] newArray(int size) {
            return new DashboardTileModel[size];
        }
    };


    public interface OnClickListener {
        void onClick(View view);
    }
}