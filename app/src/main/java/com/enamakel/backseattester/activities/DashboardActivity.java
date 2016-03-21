package com.enamakel.backseattester.activities;


import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.views.dashboard.DashboardTileView;
import com.enamakel.backseattester.views.masonry.MasonryGridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_dashboard)
public class DashboardActivity extends AppCompatActivity {
//    @ViewById LinearLayout dashboardContainer;


    @AfterViews
    void afterViews() {
        DashboardTileView tile = new DashboardTileView(this, null);

        MasonryGridView gridView = new MasonryGridView(this, 6);
        gridView.addView(tile);

//        dashboardContainer.addView(gridView);
    }
}
