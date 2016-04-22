package com.onroute.android.data.models.media;


import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.onroute.android.App;
import com.onroute.android.activities.VideoPlayerActivity_;
import com.onroute.android.data.models.base.BaseParcelableModel;
import com.onroute.android.data.models.dashboard.DashboardTile;
import com.onroute.android.data.models.dashboard.DashboardTileModel;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class MediaModel extends BaseParcelableModel implements DashboardTile {
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


    @Override
    public DashboardTileModel getDashboardTile() {
        DashboardTileModel tile = new DashboardTileModel();
        tile.setLocalBackgroundImagePath(imagePath);
        tile.setTitle("Watch " + title);
        tile.setDescription(description);

        tile.setOnClickListener(new DashboardTileModel.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VideoPlayerActivity_.class);
                intent.putExtra(VideoPlayerActivity_.EXTRA_MEDIA_MODEL,
                        MediaModel.this);
                view.getContext().startActivity(intent);
            }
        });

        return tile;
    }
}