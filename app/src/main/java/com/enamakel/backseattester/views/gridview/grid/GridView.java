package com.enamakel.backseattester.views.gridview.grid;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.enamakel.backseattester.views.gridview.OnGridItemClickListener;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Eduard Albu on 16 03 2016
 *
 * @author eduard.albu@gmail.com
 */
public class GridView extends LinearLayout {
    private HashMap<String, ColumnView> mColumns;
    private Context mContext;


    public GridView(Context context) {
        super(context);
        initialize(context);
    }


    public GridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }


    public GridView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }


    public void initialize(Context context) {
        mContext = context;
        mColumns = new HashMap<>();
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
        if (mColumns.containsKey(columnId)) {
            throw new RuntimeException("Column with id: " + columnId + " already exists");
        } else {
            ColumnView columnView = new ColumnView(mContext);
            columnView.setMaxRows(columnItemsCount);
            columnView.setColumnId(columnId);
            addView(columnView);
            mColumns.put(columnId, columnView);
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
            @NonNull String columnId, @NonNull String itemId, @LayoutRes int layout, int weight) {

        if (!mColumns.containsKey(columnId))
            throw new RuntimeException("Can't find column with id: " + columnId);

        ColumnView columnView = mColumns.get(columnId);

        if (columnView.itemIdExists(itemId)) {
            throw new RuntimeException("Item id \"" + itemId + "\" already exists");
        }

        if (columnView.getItemsCount() == columnView.getMaxRows()) {
            columnView.setMaxRows(columnView.getMaxRows() + 1);
        }
        columnView.addItemToColumn(itemId, layout, weight);
    }


    /**
     * Refer to {@link GridView#addViewToColumn(String, String, int, int)}
     *
     * @param view you want to add
     */
    public void addViewToColumn(
            @NonNull String columnId, @NonNull String itemId, View view, int weight) {

        if (!mColumns.containsKey(columnId))
            throw new RuntimeException("Can't find column with id: " + columnId);

        ColumnView columnView = mColumns.get(columnId);

        if (columnView.itemIdExists(itemId)) {
            throw new RuntimeException("item id \"" + itemId + "\" already exists");
        }

        if (columnView.getItemsCount() == columnView.getMaxRows()) {
            columnView.setMaxRows(columnView.getMaxRows() + 1);
        }
        columnView.addItemToColumn(itemId, view, weight);
    }


    /**
     * Remove a column from layout
     *
     * @param columnId for the column you want to remove
     */
    public void removeColumn(@NonNull String columnId) {
        if (!mColumns.containsKey(columnId)) {
            throw new RuntimeException("Can't find column with id: " + columnId);
        } else {
            ColumnView columnView = mColumns.get(columnId);
            columnView.setVisibility(GONE);
            removeView(columnView);
            mColumns.remove(columnId);
        }
    }


    /**
     * Remove an item from the column
     *
     * @param columnId from where to remove the item
     * @param itemId   for the item you want to remove
     */
    public void removeItemFromColumn(@NonNull String columnId, @NonNull String itemId) {
        if (!mColumns.containsKey(columnId)) {
            throw new RuntimeException("Can't find column with id: " + columnId);
        } else {
            ColumnView columnView = mColumns.get(columnId);
            columnView.removeItemFromColumn(itemId);
        }
    }


    /**
     * Counts how many columns are in the layout
     *
     * @return actual columns count
     */
    public int getColumnCount() {
        return mColumns.size();
    }


    /**
     * Counts the items within a cloumn
     *
     * @param columnId for which you want to count items
     * @return actual items count within the column
     */
    public int getColumnItemsCount(String columnId) {
        if (!mColumns.containsKey(columnId)) {
            throw new RuntimeException("Can't find column with id: " + columnId);
        } else {
            ColumnView columnView = mColumns.get(columnId);
            return columnView.getItemsCount();
        }
    }


    /**
     * Set a listener which will be called when an item is clicked
     *
     * @param onGridItemClickListener for the grid items
     */
    public void setOnGridItemClickListener(OnGridItemClickListener onGridItemClickListener) {
        for (Map.Entry<String, ColumnView> set : mColumns.entrySet()) {
            set.getValue().addOnGridItemClickListener(onGridItemClickListener);
        }
    }
}
