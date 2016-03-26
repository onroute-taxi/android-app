package com.enamakel.backseattester.activities.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.enamakel.backseattester.R;


public abstract class DrawerActivity extends InjectableActivity {
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    View drawer;
    Class<? extends Activity> pendingNavigation;
    Bundle pendingNavigationExtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_drawer);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawer = findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (drawerView.equals(drawer) && pendingNavigation != null) {
                    final Intent intent = new Intent(DrawerActivity.this, pendingNavigation);
                    if (pendingNavigationExtras != null) {
                        intent.putExtras(pendingNavigationExtras);
                        pendingNavigationExtras = null;
                    }
                    // TODO M bug https://code.google.com/p/android/issues/detail?id=193822
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    pendingNavigation = null;
                }
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(drawer)) closeDrawers();
        else super.onBackPressed();
    }


    @Override
    public void setContentView(int layoutResID) {
        ViewGroup drawerLayout = (ViewGroup) findViewById(R.id.drawer_layout);
        View view = getLayoutInflater().inflate(layoutResID, drawerLayout, false);
        drawerLayout.addView(view, 0);
    }


    public void navigate(Class<? extends Activity> activityClass, @Nullable Bundle extras) {
        pendingNavigation = !getClass().equals(activityClass) ? activityClass : null;
        pendingNavigationExtras = extras;
        closeDrawers();
    }


    void closeDrawers() {
        drawerLayout.closeDrawers();
    }
}
