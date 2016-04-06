package com.onroute.android.views.gridview.items;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onroute.android.R;


/**
 * Created by Eduard Albu on 18 03 2016
 * project ImageToXML
 *
 * @author eduard.albu@gmail.com
 */
public class ButtonsGridItem extends LinearLayout {

    private Context mContext;
    private RelativeLayout mLeftButton;
    private RelativeLayout mRightButton;
    private ImageView mLeftButtonHolder;
    private ImageView mRightButtonHolder;
    private TextView mLeftButtonTitle;
    private ImageView mLeftButtonImage;
    private TextView mRightButtonTitle;
    private ImageView mRightButtonImage;


    public ButtonsGridItem(Context context) {
        super(context);
        initializeView(context);
    }


    public ButtonsGridItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }


    public ButtonsGridItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ButtonsGridItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView(context);
    }


    private void initializeView(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.gridview_buttons_layout, this);
        mLeftButton = (RelativeLayout) root.findViewById(R.id.buttons_item_leftButton);
        mLeftButtonTitle = (TextView) root.findViewById(R.id.buttons_item_leftButton_text);
        mLeftButtonImage = (ImageView) root.findViewById(R.id.buttons_item_leftButton_image);
        mLeftButtonHolder = (ImageView) root.findViewById(R.id.buttons_item_leftButton_background);

        mRightButton = (RelativeLayout) root.findViewById(R.id.buttons_item_rightButton);
        mRightButtonTitle = (TextView) root.findViewById(R.id.buttons_item_rightButton_text);
        mRightButtonImage = (ImageView) root.findViewById(R.id.buttons_item_rightButton_image);
        mRightButtonHolder = (ImageView) root.findViewById(R.id.buttons_item_rightButton_background);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeftButton = (RelativeLayout) this.findViewById(R.id.buttons_item_leftButton);
        mLeftButtonTitle = (TextView) this.findViewById(R.id.buttons_item_leftButton_text);
        mLeftButtonImage = (ImageView) this.findViewById(R.id.buttons_item_leftButton_image);
        mLeftButtonHolder = (ImageView) this.findViewById(R.id.leftButtonBackground);

        mRightButton = (RelativeLayout) this.findViewById(R.id.buttons_item_rightButton);
        mRightButtonTitle = (TextView) this.findViewById(R.id.buttons_item_rightButton_text);
        mRightButtonImage = (ImageView) this.findViewById(R.id.buttons_item_rightButton_image);
        mRightButtonHolder = (ImageView) this.findViewById(R.id.rightButtonBackground);
    }

    //---------------- Left Button ----------------//


    /**
     * Background
     */
    public void setLeftButtonBackground(@DrawableRes int buttonBackground) {
        mLeftButtonHolder.setBackgroundResource(buttonBackground);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setLeftButtonBackground(Drawable drawable) {
        mLeftButtonHolder.setBackground(drawable);
    }


    public void setLeftButtonBackgroundColor(@ColorRes int buttonBackground) {
        mLeftButtonHolder.setBackgroundColor(mContext.getResources().getColor(buttonBackground));
    }


    /**
     * Text
     */
    public void setLeftButtonTitle(String title) {
        mLeftButtonTitle.setText(title);
    }


    public void setLeftButtonTitleTypeface(Typeface titleTypeface) {
        mLeftButtonTitle.setTypeface(titleTypeface);
    }


    public void setLeftButtonTitleColor(@ColorRes int colorRes) {
        mLeftButtonTitle.setTextColor(mContext.getResources().getColor(colorRes));
    }


    public void setLeftButtonTitleColorHex(@ColorInt int colorHex) {
        mLeftButtonTitle.setTextColor(colorHex);
    }


    /**
     * Icon
     */
    public void setLeftButtonImage(@DrawableRes int leftButtonImage) {
        mLeftButtonImage.setImageResource(leftButtonImage);
    }


    public void setLeftButtonImage(Drawable drawable) {
        mLeftButtonImage.setImageDrawable(drawable);
    }


    public void setLeftButtonImage(Bitmap buttonImage) {
        mLeftButtonImage.setImageBitmap(buttonImage);
    }


    /**
     * Click listener
     */
    public void setLeftButtonClickListener(OnClickListener onClickListener) {
        mLeftButton.setOnClickListener(onClickListener);
    }


    //---------------- Left Button ----------------//


    /**
     * Background
     */
    public void setRightButtonBackground(@DrawableRes int buttonBackground) {
        mRightButtonHolder.setBackgroundResource(buttonBackground);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setRightButtonBackground(Drawable drawable) {
        mRightButtonHolder.setBackground(drawable);
    }


    public void setRightButtonBackgroundColor(@ColorRes int buttonBackground) {
        mRightButtonHolder.setBackgroundColor(mContext.getResources().getColor(buttonBackground));
    }


    /**
     * Text
     */
    public void setRightButtonTitle(String title) {
        mRightButtonTitle.setText(title);
    }


    public void setRightButtonTitleTypeface(Typeface titleTypeface) {
        mRightButtonTitle.setTypeface(titleTypeface);
    }


    public void setRightButtonTitleColor(@ColorRes int colorRes) {
        mRightButtonTitle.setTextColor(mContext.getResources().getColor(colorRes));
    }


    public void setRightButtonTitleColorHex(@ColorInt int colorHex) {
        mRightButtonTitle.setTextColor(colorHex);
    }


    /**
     * Icon
     */
    public void setRightButtonImage(@DrawableRes int leftButtonImage) {
        mRightButtonImage.setImageResource(leftButtonImage);
    }


    public void setRightButtonImage(Drawable drawable) {
        mRightButtonImage.setImageDrawable(drawable);
    }


    public void setRightButtonImage(Bitmap buttonImage) {
        mRightButtonImage.setImageBitmap(buttonImage);
    }


    /**
     * Click listener
     */
    public void setRightButtonClickListener(OnClickListener onClickListener) {
        mRightButton.setOnClickListener(onClickListener);
    }
}
