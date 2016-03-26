package com.enamakel.backseattester.activities.dashboard;


import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.activities.base.DrawerActivity;


public abstract class BaseDashboardActivity extends DrawerActivity {
    Toolbar toolbar;


    protected void initializeToolbar(int id) {
        // Get toolbar from layout
        toolbar = (Toolbar) findViewById(id);
//        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Menu clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
//                ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.toolbar);
    }
}
