package com.onroute.android.activities.base.dashboard;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.onroute.android.R;
import com.onroute.android.activities.base.DrawerActivity;


public abstract class BaseDashboardActivity extends DrawerActivity {
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideSystemUI();
        super.onCreate(savedInstanceState);
    }


    protected void initializeToolbar(int id) {
        // Get toolbar from layout
        toolbar = (Toolbar) findViewById(id);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Menu clicked", Toast.LENGTH_SHORT).show();
            }
        });
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.toolbar);
    }
}