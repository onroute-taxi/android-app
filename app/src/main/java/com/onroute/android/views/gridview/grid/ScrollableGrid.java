package com.onroute.android.views.gridview.grid;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.onroute.android.R;
import com.onroute.android.views.gridview.OnGridItemClickListener;


/**
 * Created by Eduard Albu on 18 03 2016
 *
 * @author eduard.albu@gmail.com
 */
public class ScrollableGrid extends HorizontalScrollView {
    private Context mContext;
    private static GridView gridView;


    public ScrollableGrid(Context context) {
        super(context);
        initializeView(context);
    }


    public ScrollableGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }


    public ScrollableGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollableGrid(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView(context);
    }


    private void initializeView(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gridview_scrolable_layout, this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        gridView = (GridView) findViewById(R.id.grid_view);
    }


    /**
     * Add a new column to the screen
     *
     * @param columnId         new unique identifier for the column you want to add
     * @param columnItemsCount how many items you want to hold in this column,
     *                         it is used to compute the items height
     *                         (you can change it any time after, default is set to 1)
     */
    public void addNewColumn(@NonNull String columnId, int columnItemsCount) {
        if (gridView != null) {
            gridView.addNewColumn(columnId, columnItemsCount);
        } else {
            gridView = (GridView) inflate(mContext, R.layout.gridview, this);
            gridView.addNewColumn(columnId, columnItemsCount);
        }
    }


    /**
     * Remove a view from layout
     *
     * @param columnId for the column you want to remove
     */
    public void removeColumn(@NonNull String columnId) {
        if (gridView != null) {
            gridView.removeColumn(columnId);
        } else {
            gridView = (GridView) inflate(mContext, R.layout.gridview, this);
            gridView.removeColumn(columnId);
        }
    }


    /**
     * Add a new view to a column
     *
     * @param columnId for the column where you want to add the view
     * @param itemId   a new identifier for the item
     * @param layout   resource of the view you want to add
     */
    public void addViewToColumn(
            @NonNull String columnId, String itemId, @LayoutRes int layout, int weight) {
        if (gridView != null) {
            gridView.addViewToColumn(columnId, itemId, layout, weight);
        } else {
            gridView = (GridView) inflate(mContext, R.layout.gridview, this);
            gridView.addViewToColumn(columnId, itemId, layout, weight);
        }
    }


    /**
     * Refer to {@link ScrollableGrid#addViewToColumn(String, String, int, int)}
     *
     * @param view you want to add
     */
    public void addViewToColumn(@NonNull String columnId, String itemId, View view, int weight) {
        if (gridView != null) {
            gridView.addViewToColumn(columnId, itemId, view, weight);
        } else {
            gridView = (GridView) inflate(mContext, R.layout.gridview, this);
            gridView.addViewToColumn(columnId, itemId, view, weight);
        }
    }


    /**
     * Remove an item from the column
     *
     * @param columnId from where to remove the item
     * @param itemId   for the item you want to remove
     */
    public void removeItemFromColumn(@NonNull String columnId, @NonNull String itemId) {
        if (gridView != null) {
            gridView.removeItemFromColumn(columnId, itemId);
        } else {
            gridView = (GridView) inflate(mContext, R.layout.gridview, this);
            gridView.removeItemFromColumn(columnId, itemId);
        }
    }


    /**
     * Counts how many columns are in the layout
     *
     * @return actual columns count
     */
    public int getColumnCount() {
        if (gridView != null) {
            return gridView.getColumnCount();
        } else {
            gridView = (GridView) inflate(mContext, R.layout.gridview, this);
            return gridView.getColumnCount();
        }
    }


    /**
     * Counts the items within a cloumn
     *
     * @param columnId for which you want to count items
     * @return actual items count within the column
     */
    public int getColumnItemsCount(@NonNull String columnId) {
        if (gridView != null) {
            return gridView.getColumnItemsCount(columnId);
        } else {
            gridView = (GridView) inflate(mContext, R.layout.gridview, this);
            return gridView.getColumnItemsCount(columnId);
        }
    }


    /**
     * Set a listener which will be called when an item is clicked
     *
     * @param onGridItemClickListener for the grid items
     */
    public void setOnGridItemClickListener(OnGridItemClickListener onGridItemClickListener) {
        gridView.setOnGridItemClickListener(onGridItemClickListener);
    }
}
