package com.enamakel.backseattester.util;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.enamakel.backseattester.AppUtils;


/**
 * Helper to tint menu items for activities and fragments
 */
public class MenuTintDelegate {
    int mTextColorPrimary;


    /**
     * Callback that should be triggered after activity has been created
     *
     * @param context activity context
     */
    public void onActivityCreated(Context context) {
        mTextColorPrimary = ContextCompat.getColor(context,
                AppUtils.getThemedResId(context, android.R.attr.textColorPrimary));
    }


    /**
     * Callback that should be triggered after menu has been inflated
     *
     * @param menu inflated menu
     */
    public void onOptionsMenuCreated(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable == null) {
                continue;
            }
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, mTextColorPrimary);
        }
    }


    public void setIcon(MenuItem item, @DrawableRes int icon) {
        item.setIcon(icon);
        Drawable drawable = item.getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, mTextColorPrimary);
    }
}
