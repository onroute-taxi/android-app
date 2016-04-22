package com.onroute.android.activities.base.dashboard;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onroute.android.R;
import com.onroute.android.data.models.dashboard.DashboardTileModel;
import com.onroute.android.views.gridview.items.IconGridItem;
import com.onroute.android.views.gridview.items.ImageGridItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Eduard Albu on 18 03 2016
 * project ImageToXML
 *
 * @author eduard.albu@gmail.com
 */
public class BottomListAdapter extends RecyclerView.Adapter<BottomListAdapter.ViewHolder> {

    private ArrayList<DashboardTileModel> mSomeObjects;
    private OnBottomItemClickListener mOnBottomItemClickListener;
    Context context;


    public BottomListAdapter(Context context, ArrayList<DashboardTileModel> someObjects) {
        mSomeObjects = someObjects;
        this.context = context;
    }


    public void setOnBottomItemClickListener(OnBottomItemClickListener listener) {
        mOnBottomItemClickListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_bottom_list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DashboardTileModel model = mSomeObjects.get(position);
        holder.iconGridItem.setTile(model);
    }


    @Override
    public int getItemCount() {
        return mSomeObjects.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        IconGridItem iconGridItem;


        public ViewHolder(View itemView) {
            super(itemView);
            iconGridItem = (IconGridItem) itemView.findViewById(R.id.itemImage);
        }
    }


    public interface OnBottomItemClickListener {
        void onBottomItemClickListener(DashboardTile object);
    }
}
