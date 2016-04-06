package com.onroute.android.activities.base;


import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.Menu;


public abstract class ThemedActivity extends BaseActivity {
//    final MenuTintDelegate menuTintDelegate = new MenuTintDelegate();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        menuTintDelegate.onActivityCreated(this);
    }


    @CallSuper
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menuTintDelegate.onOptionsMenuCreated(menu);
        return super.onCreateOptionsMenu(menu);
    }


    protected boolean isDialogTheme() {
        return false;
    }
}
