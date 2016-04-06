package com.onroute.android.views.gridview.grid;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.onroute.android.views.gridview.OnGridItemClickListener;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Eduard Albu on 17 03 2016
 *
 * @author eduard.albu@gmail.com
 */
public class ColumnView extends LinearLayout {
    private int mMaxRows = 1;
    private Context mContext;
    private HashMap<String, View> mItems;
    private static OnGridItemClickListener onGridItemClickListener;
    private String mColumnId;

    private static int divider = 1;

    private int mDefaultBackgroundWidth;


    public ColumnView(Context context) {
        super(context);
        initializeView(context);
    }


    public ColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }


    public ColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColumnView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView(context);
    }


    /**
     * Initialize this column
     *
     * @param context from the constructors
     */
    private void initializeView(Context context) {
        // asign context to a global variable
        mContext = context;
        // instantiate a new list of items for this column
        mItems = new HashMap<>();
        // get screen size and orientation
        int screenLayout = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        int orientation = context.getResources().getConfiguration().orientation;
        // check the screen size and orientation to give a default value for the divider
        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                Log.d(this.getClass().getSimpleName(), "Screen size: Large");
                divider = 2;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                Log.d(this.getClass().getSimpleName(), "Screen size: Normal");
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    divider = 3;
                } else {
                    divider = 2;
                }
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                Log.d(this.getClass().getSimpleName(), "Screen size: Small");
                divider = 1;
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                Log.d(this.getClass().getSimpleName(), "Screen size: XLarge");
                divider = 3;
                break;
        }

        // create a display metrics object
        DisplayMetrics displayMetrics = new DisplayMetrics();
        // obtain a link to the device display
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        // get device display data and write them to the displa metrics object
        display.getMetrics(displayMetrics);
        // get the width and height of the screen in pixels
        mDefaultBackgroundWidth = displayMetrics.widthPixels;
        setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    /**
     * Set a listener which will be called when an item is clicked
     *
     * @param onGridItemClickListener for the grid items
     */
    public void addOnGridItemClickListener(OnGridItemClickListener onGridItemClickListener) {
        ColumnView.onGridItemClickListener = onGridItemClickListener;
    }


    /**
     * Set the id for current column
     *
     * @param columnId for this column
     */
    public void setColumnId(@NonNull String columnId) {
        mColumnId = columnId;
    }


    /**
     * Add a new item to this column
     *
     * @param id     new unque identifier for the item
     * @param layout resource for the item
     */
    public void addItemToColumn(final String id, @LayoutRes int layout, int weight) {
        View view = inflate(mContext, layout, null);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGridItemClickListener != null) {
                    onGridItemClickListener.onGridItemClick(v, id, mColumnId);
                }
            }
        });
        if (weight == 0) weight = 1;
        LinearLayout.LayoutParams layoutParams = getItemLayoutParams();
        layoutParams.weight = weight;
        addView(view, layoutParams);
        mItems.put(id, view);
    }


    /**
     * Refer to {@link ColumnView#addItemToColumn(String, int, int)}
     *
     * @param view you want to add to this column
     */
    public void addItemToColumn(final String id, View view, int weight) {

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGridItemClickListener != null) {
                    onGridItemClickListener.onGridItemClick(v, id, mColumnId);
                }
            }
        });
        if (weight == 0) weight = 1;
        LinearLayout.LayoutParams layoutParams = getItemLayoutParams();
        layoutParams.weight = weight;
        addView(view, layoutParams);
        mItems.put(id, view);
    }


    /**
     * Sets the row number
     *
     * @param rowsCount needed to compute the height for each item
     */
    public void setMaxRows(int rowsCount) {
        mMaxRows = rowsCount;
    }


    public int getMaxRows() {
        return mMaxRows;
    }


    /**
     * Counts the items within this column
     *
     * @return actual items count
     */
    public int getItemsCount() {
        return mItems.size();
    }


    /**
     * Set the divider variable (used to divide the screen in to it's value)
     *
     * @param columnsCount how many columns to displat on one screen
     *                     by default is set to 3 in landscape and 2 in portrait for 10" and 7"
     *                     for for 5" and bellow it is 1 in portrait and 2 in landscape
     */
    public void setDivider(int columnsCount) {
        divider = columnsCount;
    }


    public int getDivider() {
        return divider;
    }


    /**
     * Create a new instance of LinearLayout.LayoutParams
     * (used to set layout params for each view within the column)
     *
     * @return new LinearLayout.LayoutParams
     */
    private LinearLayout.LayoutParams getItemLayoutParams() {
        int height = getHeight() / mMaxRows;
        int width = mDefaultBackgroundWidth / divider;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        setOrientation(VERTICAL);
        layoutParams.gravity = Gravity.CENTER;
        return layoutParams;
    }


    /**
     * Remove an item from current column
     *
     * @param itemId for the item you want to remove
     */
    public void removeItemFromColumn(@NonNull String itemId) {
        if (!mItems.containsKey(itemId)) {
            throw new RuntimeException("Can't find item with id: " + itemId);
        } else {
            View view = mItems.get(itemId);
            view.setVisibility(GONE);
            removeView(view);
            mItems.remove(itemId);

            for (Map.Entry<String, View> set : mItems.entrySet()) {
                set.getValue().setLayoutParams(new LinearLayout.LayoutParams(getWidth(), getHeight() / mItems.size()));
                set.getValue().invalidate();
                set.getValue().requestLayout();
            }
        }
    }


    public boolean itemIdExists(@NonNull String itemId) {
        return mItems.containsKey(itemId);
    }
}
