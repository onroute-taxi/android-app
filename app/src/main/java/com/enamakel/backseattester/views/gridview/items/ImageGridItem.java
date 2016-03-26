package com.enamakel.backseattester.views.gridview.items;


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

import com.enamakel.backseattester.R;


/**
 * Created by Eduard Albu on 18 03 2016
 * project ImageToXML
 *
 * @author eduard.albu@gmail.com
 */
public class ImageGridItem extends FrameLayout {

    private Bitmap mOffscreenBitmap;
    private Canvas mOffscreenCanvas;
    private BitmapShader mBitmapShader;
    private Paint mPaint;
    private RectF mRectF;

    private Handler mHandler;
    private Context mContext;
    private ImageView mBackgroundImage;
    private ImageView mBottomIconImage;
    private TextView mBottomTitleText;
    private TextView mBottomDescription;


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
        mContext = context;
        setWillNotDraw(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.gridview_image_item, this);
        mBackgroundImage = (ImageView) root.findViewById(R.id.image_item_background);
        mBottomIconImage = (ImageView) root.findViewById(R.id.bottom_icon_imageView);
        mBottomTitleText = (TextView) root.findViewById(R.id.bottom_item_title);
        mBottomTitleText.setSingleLine(true);
        mBottomTitleText.setEllipsize(TextUtils.TruncateAt.END);
        mBottomDescription = (TextView) root.findViewById(R.id.bottom_item_description);
        mBottomDescription.setSingleLine(true);
        mBottomDescription.setEllipsize(TextUtils.TruncateAt.END);
        mHandler = new Handler();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackgroundImage = (ImageView) this.findViewById(R.id.image_item_background);
        mBottomIconImage = (ImageView) this.findViewById(R.id.bottom_icon_imageView);
        mBottomTitleText = (TextView) this.findViewById(R.id.bottom_item_title);
        mBottomTitleText.setSingleLine(true);
        mBottomTitleText.setEllipsize(TextUtils.TruncateAt.END);
        mBottomDescription = (TextView) this.findViewById(R.id.bottom_item_description);
        mBottomDescription.setSingleLine(true);
        mBottomDescription.setEllipsize(TextUtils.TruncateAt.END);
    }

//    @Override
//    public void draw(Canvas canvas) {
//        if (mOffscreenBitmap == null) {
//            mOffscreenBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
//            mOffscreenCanvas = new Canvas(mOffscreenBitmap);
//            mBitmapShader = new BitmapShader(mOffscreenBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            mPaint.setShader(mBitmapShader);
//            mRectF = new RectF(0f, 0f, canvas.getWidth(), canvas.getHeight());
//        }
//        super.draw(mOffscreenCanvas);
//        canvas.drawRoundRect(mRectF, 20, 20, mPaint);
//    }


    /**
     * Set background image
     */
    public void setBackgroundImage(@DrawableRes int imageRes) {
        mBackgroundImage.setImageResource(imageRes);
    }


    public void setBackgroundImage(Bitmap backgroundImage) {
        mBackgroundImage.setImageBitmap(backgroundImage);
    }


    public void setBackgroundImage(Drawable drawable) {
        mBackgroundImage.setImageDrawable(drawable);
    }


    /**
     * Set image for icon
     */
    public void setIconImage(@DrawableRes int imageRes) {
        mBottomIconImage.setImageResource(imageRes);
    }


    public void setIconImage(Bitmap backgroundImage) {
        mBottomIconImage.setImageBitmap(backgroundImage);
    }


    public void setIconImage(Drawable drawable) {
        mBottomIconImage.setImageDrawable(drawable);
    }


    /**
     * Set title text
     */
    public void setTitle(String title) {
        mBottomTitleText.setText(title);
    }


    /**
     * Set title type face
     */
    public void setTitleTypeface(Typeface typeface) {
        mBottomTitleText.setTypeface(typeface);
    }


    /**
     * Set title text color
     */
    public void setTitleTextColor(@ColorRes int color) {
        mBottomTitleText.setTextColor(mContext.getResources().getColor(color));
    }


    public void setTitleTextHexColor(@ColorInt int color) {
        mBottomTitleText.setTextColor(color);
    }


    /**
     * Set description text
     */
    public void setDescription(String title) {
        mBottomDescription.setText(title);
    }


    /**
     * Set description type face
     */
    public void setDescriptionTypeface(Typeface typeface) {
        mBottomDescription.setTypeface(typeface);
    }


    /**
     * Set title text color
     */
    public void setDescriptionTextColor(@ColorRes int color) {
        mBottomDescription.setTextColor(mContext.getResources().getColor(color));
    }


    public void setDescriptionTextHexColor(@ColorInt int color) {
        mBottomDescription.setTextColor(color);
    }
}
