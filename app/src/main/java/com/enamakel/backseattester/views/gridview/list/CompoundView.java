package com.enamakel.backseattester.views.gridview.list;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.views.gridview.grid.ScrollableGrid;


/**
 * Created by Eduard Albu on 18 03 2016
 * project ImageToXML
 *
 * @author eduard.albu@gmail.com
 */
public class CompoundView extends LinearLayout {

    private Context mContext;
    private static RecyclerView recyclerView;
    private static ScrollableGrid scrollableGrid;


    public CompoundView(Context context) {
        super(context);
        initializeView(context);
    }


    public CompoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }


    public CompoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CompoundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView(context);
    }


    private void initializeView(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.gridview_compound_layout, this);
        recyclerView = (RecyclerView) root.findViewById(R.id.compoundView_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL, false));
        scrollableGrid = (ScrollableGrid) root.findViewById(R.id.scrollable_gridView);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        recyclerView = (RecyclerView) this.findViewById(R.id.compoundView_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, HORIZONTAL, false));
        scrollableGrid = (ScrollableGrid) this.findViewById(R.id.scrollable_gridView);
    }


    public ScrollableGrid getScrollableGrid() {
        return scrollableGrid;
    }


    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
