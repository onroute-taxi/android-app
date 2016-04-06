package com.onroute.android.activities.dashboard.core;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onroute.android.R;
import com.onroute.android.views.gridview.items.ImageGridItem;

import java.util.ArrayList;


/**
 * Created by Eduard Albu on 18 03 2016
 * project ImageToXML
 *
 * @author eduard.albu@gmail.com
 */
public class BottomListAdapter extends RecyclerView.Adapter<BottomListAdapter.ViewHolder> {

    private ArrayList<DashboardTile> mSomeObjects;
    private OnBottomItemClickListener mOnBottomItemClickListener;


    public BottomListAdapter(ArrayList<DashboardTile> someObjects) {
        mSomeObjects = someObjects;
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
        DashboardTile object = mSomeObjects.get(position);
        holder.mImageView.setBackgroundImage(object.getImageResId());
    }


    @Override
    public int getItemCount() {
        return mSomeObjects.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageGridItem mImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = (ImageGridItem) itemView.findViewById(R.id.itemImage);
        }


        @Override
        public void onClick(View v) {
            if (mOnBottomItemClickListener != null) {
                mOnBottomItemClickListener.onBottomItemClickListener(mSomeObjects.get(getAdapterPosition()));
            }
        }
    }


    public interface OnBottomItemClickListener {
        void onBottomItemClickListener(DashboardTile object);
    }
}
