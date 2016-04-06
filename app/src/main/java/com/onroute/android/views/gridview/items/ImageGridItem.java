package com.onroute.android.views.gridview.items;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.onroute.android.R;
import com.onroute.android.data.models.media.MediaModel;
import com.squareup.picasso.Picasso;

import lombok.Getter;


/**
 * Created by Eduard Albu on 18 03 2016
 * project ImageToXML
 *
 * @author eduard.albu@gmail.com
 */
public class ImageGridItem extends FrameLayout {
    private Bitmap offscreenBitmap;
    private Canvas offscreenCanvas;
    private BitmapShader bitmapShader;
    private Paint paint;
    private RectF rectF;

    private Handler handler;
    private Context context;
    private @Getter ImageView backgroundImage;
    private @Getter ImageView bottomIconImage;
    private TextView bottomTitleText;
    private TextView bottomDescription;
    private @Getter MediaModel mediaModel;


    public ImageGridItem(Context context, MediaModel model) {
        super(context);
        initializeView(context);
        setMediaModel(model);
    }


    public ImageGridItem(Context context) {
        super(context);
        initializeView(context);
    }


    public ImageGridItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }


    public ImageGridItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageGridItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView(context);
    }


    public void initializeView(Context context) {
        this.context = context;
        setWillNotDraw(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.gridview_image_item, this);
        backgroundImage = (ImageView) root.findViewById(R.id.image_item_background);
        bottomIconImage = (ImageView) root.findViewById(R.id.bottom_icon_imageView);
        bottomTitleText = (TextView) root.findViewById(R.id.bottom_item_title);
        bottomTitleText.setSingleLine(true);
        bottomTitleText.setEllipsize(TextUtils.TruncateAt.END);
        bottomDescription = (TextView) root.findViewById(R.id.bottom_item_description);
        bottomDescription.setSingleLine(true);
        bottomDescription.setEllipsize(TextUtils.TruncateAt.END);
        handler = new Handler();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        backgroundImage = (ImageView) this.findViewById(R.id.image_item_background);
        bottomIconImage = (ImageView) this.findViewById(R.id.bottom_icon_imageView);
        bottomTitleText = (TextView) this.findViewById(R.id.bottom_item_title);
        bottomTitleText.setSingleLine(true);
        bottomTitleText.setEllipsize(TextUtils.TruncateAt.END);
        bottomDescription = (TextView) this.findViewById(R.id.bottom_item_description);
        bottomDescription.setSingleLine(true);
        bottomDescription.setEllipsize(TextUtils.TruncateAt.END);
    }


    public void setMediaModel(MediaModel model) {
        mediaModel = model;

        Picasso.with(getContext()).load(model.getImagePath()).into(backgroundImage);

        setIconImage(R.drawable.ic_favorite_white_24dp);
        setTitle(model.getTitle());
        setDescription(model.getDescription());
    }

//    @Override
//    public void draw(Canvas canvas) {
//        if (offscreenBitmap == null) {
//            offscreenBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
//            offscreenCanvas = new Canvas(offscreenBitmap);
//            bitmapShader = new BitmapShader(offscreenBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            paint.setShader(bitmapShader);
//            rectF = new RectF(0f, 0f, canvas.getWidth(), canvas.getHeight());
//        }
//        super.draw(offscreenCanvas);
//        canvas.drawRoundRect(rectF, 20, 20, paint);
//    }


    /**
     * Set background image
     */
    public void setBackgroundImage(@DrawableRes int imageRes) {
        backgroundImage.setImageResource(imageRes);
    }


    public void setBackgroundImage(Bitmap backgroundImage) {
        this.backgroundImage.setImageBitmap(backgroundImage);
    }


    public void setBackgroundImage(Drawable drawable) {
        backgroundImage.setImageDrawable(drawable);
    }


    /**
     * Set image for icon
     */
    public void setIconImage(@DrawableRes int imageRes) {
        bottomIconImage.setImageResource(imageRes);
    }


    public void setIconImage(Bitmap backgroundImage) {
        bottomIconImage.setImageBitmap(backgroundImage);
    }


    public void setIconImage(Drawable drawable) {
        bottomIconImage.setImageDrawable(drawable);
    }


    /**
     * Set title text
     */
    public void setTitle(String title) {
        bottomTitleText.setText(title);
    }


    /**
     * Set title type face
     */
    public void setTitleTypeface(Typeface typeface) {
        bottomTitleText.setTypeface(typeface);
    }


    /**
     * Set title text color
     */
    public void setTitleTextColor(@ColorRes int color) {
        bottomTitleText.setTextColor(context.getResources().getColor(color));
    }


    public void setTitleTextHexColor(@ColorInt int color) {
        bottomTitleText.setTextColor(color);
    }


    /**
     * Set description text
     */
    public void setDescription(String title) {
        bottomDescription.setText(title);
    }


    /**
     * Set description type face
     */
    public void setDescriptionTypeface(Typeface typeface) {
        bottomDescription.setTypeface(typeface);
    }


    /**
     * Set title text color
     */
    public void setDescriptionTextColor(@ColorRes int color) {
        bottomDescription.setTextColor(context.getResources().getColor(color));
    }


    public void setDescriptionTextHexColor(@ColorInt int color) {
        bottomDescription.setTextColor(color);
    }
}
