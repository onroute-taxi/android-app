package com.enamakel.backseattester.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.enamakel.backseattester.AppUtils;


public class TintableTextView extends TextView {
    final int textColor;


    public TintableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TintableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int defaultTextColor = ContextCompat.getColor(getContext(),
                AppUtils.getThemedResId(getContext(), android.R.attr.textColorTertiary));

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                new int[]{android.R.attr.textAppearance, android.R.attr.textColor});

        int ap = typedArray.getResourceId(0, 0);

        if (ap == 0) textColor = typedArray.getColor(1, defaultTextColor);
        else {
            TypedArray tap = context.obtainStyledAttributes(ap, new int[]{android.R.attr.textColor});
            textColor = tap.getColor(0, defaultTextColor);
            tap.recycle();
        }

        typedArray.recycle();
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
    }


    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        tint(left);
        tint(top);
        tint(right);
        tint(bottom);
        super.setCompoundDrawables(left, top, right, bottom);
    }


    void tint(@Nullable Drawable drawable) {
        if (drawable == null) return;

        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, textColor);
    }
}
