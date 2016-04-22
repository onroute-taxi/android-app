package com.onroute.android.views.gridview.items;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.onroute.android.R;
import com.onroute.android.data.models.dashboard.DashboardTileModel;
import com.squareup.picasso.Picasso;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by Eduard Albu on 18 03 2016
 * project ImageToXML
 *
 * @author eduard.albu@gmail.com
 */
public class IconGridItem extends FrameLayout implements View.OnClickListener {
    private Context context;
    private @Getter @Setter ImageView backgroundImage;
    private @Getter @Setter TextView title;
    private @Getter DashboardTileModel tileModel;


    public IconGridItem(Context context) {
        super(context);
        initializeView(context);
    }


    public IconGridItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }


    public IconGridItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }


    public void initializeView(Context context) {
        this.context = context;

        setWillNotDraw(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.gridview_image_icon, this);
        backgroundImage = (ImageView) root.findViewById(R.id.image_item_background);
        title = (TextView) root.findViewById(R.id.bottom_item_title);

        root.setOnClickListener(this);
        setOnClickListener(this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        backgroundImage = (ImageView) this.findViewById(R.id.image_item_background);
    }


    public void setTile(DashboardTileModel model) {
        tileModel = model;
        title.setText(model.getTitle());
        Picasso.with(getContext()).load(model.getLocalBackgroundImagePath()).into(backgroundImage);
    }


    @Override
    public void onClick(View v) {
        Log.d("fuck", "you");
        if(tileModel != null && tileModel.getOnClickListener() != null)
            tileModel.getOnClickListener().onClick(this);
    }
}
