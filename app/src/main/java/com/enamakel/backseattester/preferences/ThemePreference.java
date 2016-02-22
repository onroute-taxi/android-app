package com.enamakel.backseattester.preferences;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.util.ArrayMap;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.views.ThemeView;

import java.util.ArrayList;


public class ThemePreference extends Preference {
    public static final @StyleRes int THEME_DEFAULT = R.style.AppTheme;
    public static final @StyleRes int DIALOG_THEME_DEFAULT = R.style.AppAlertDialog;

    static final String VALUE_LIGHT = "light";
    static final String VALUE_DARK = "dark";
    static final String VALUE_SEPIA = "sepia";
    static final String VALUE_GREEN = "green";
    static final String VALUE_SOLARIZED = "solarized";
    static final String VALUE_SOLARIZED_DARK = "solarized_dark";
    static final String VALUE_BLUE = "blue";
    static final String VALUE_ORANGE = "orange";
    static final ArrayMap<Integer, String> BUTTON_VALUE = new ArrayMap<>();
    static final ArrayMap<String, ThemeSpec> VALUE_THEME = new ArrayMap<>();
    static final ArrayList<ThemeSpec> THEMES = new ArrayList<>();


    static {
        BUTTON_VALUE.put(R.id.theme_light, VALUE_LIGHT);
        BUTTON_VALUE.put(R.id.theme_dark, VALUE_DARK);
        BUTTON_VALUE.put(R.id.theme_sepia, VALUE_SEPIA);
        BUTTON_VALUE.put(R.id.theme_green, VALUE_GREEN);
        BUTTON_VALUE.put(R.id.theme_solarized, VALUE_SOLARIZED);
        BUTTON_VALUE.put(R.id.theme_solarized_dark, VALUE_SOLARIZED_DARK);
        BUTTON_VALUE.put(R.id.theme_blue, VALUE_BLUE);
        BUTTON_VALUE.put(R.id.theme_orange, VALUE_ORANGE);

        VALUE_THEME.put(VALUE_LIGHT,
                new ThemeSpec(R.string.theme_light, R.style.AppTheme, R.style.AppAlertDialog));
        VALUE_THEME.put(VALUE_DARK,
                new ThemeSpec(R.string.theme_dark, R.style.AppTheme_Dark, R.style.AppAlertDialog_Dark));
        VALUE_THEME.put(VALUE_SEPIA,
                new ThemeSpec(R.string.theme_sepia, R.style.AppTheme_Sepia, R.style.AppAlertDialog_Sepia));
        VALUE_THEME.put(VALUE_GREEN,
                new ThemeSpec(R.string.theme_green, R.style.AppTheme_Green, R.style.AppAlertDialog_Green));
        VALUE_THEME.put(VALUE_SOLARIZED,
                new ThemeSpec(R.string.theme_solarized, R.style.AppTheme_Solarized, R.style.AppAlertDialog_Solarized));
        VALUE_THEME.put(VALUE_SOLARIZED_DARK,
                new ThemeSpec(R.string.theme_solarized_dark, R.style.AppTheme_Dark_Solarized, R.style.AppAlertDialog_Dark_Solarized));
        VALUE_THEME.put(VALUE_BLUE,
                new ThemeSpec(R.string.theme_blue, R.style.AppTheme_Blue, R.style.AppAlertDialog_Dark_Solarized));
        VALUE_THEME.put(VALUE_ORANGE,
                new ThemeSpec(R.string.theme_orange, R.style.AppTheme_Orange, R.style.AppAlertDialog_Dark_Solarized));

        for (String key : VALUE_THEME.keySet()) {
            THEMES.add(VALUE_THEME.get(key));
        }
    }


    public static void registerTheme(@StringRes int summary, @StyleRes int theme, @StyleRes int dialogTheme) {

    }


    public static
    @StyleRes
    int getTheme(String value) {
        if (!VALUE_THEME.containsKey(value)) return THEME_DEFAULT;
        return VALUE_THEME.get(value).theme;
    }


    public static
    @StyleRes
    int getDialogTheme(String value) {
        if (!VALUE_THEME.containsKey(value)) return DIALOG_THEME_DEFAULT;
        return VALUE_THEME.get(value).dialogTheme;
    }


    public ThemePreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ThemePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.preference_theme);
    }


    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return VALUE_LIGHT;
    }


    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);
        String value = restorePersistedValue ? getPersistedString(null) : (String) defaultValue;

        if (TextUtils.isEmpty(value)) value = VALUE_LIGHT;
        setSummary(VALUE_THEME.get(value).summary);
    }


    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.itemView.setClickable(false);

//        LinearLayout layout = (LinearLayout) holder.findViewById(R.id.theme_container);
//        populateLinks(layout, THEMES);

        for (int i = 0; i < BUTTON_VALUE.size(); i++) {
            final int buttonId = BUTTON_VALUE.keyAt(i);
            final String value = BUTTON_VALUE.valueAt(i);
            View button = holder.findViewById(buttonId);
            button.setClickable(true);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSummary(VALUE_THEME.get(value).summary);
                    persistString(value);
                }
            });
        }

    }


    private void populateLinks(LinearLayout linearLayout, ArrayList<ThemeSpec> collection) {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();

        int maxWidth = display.getWidth() - 10;

        if (collection.size() > 0) {
            LinearLayout llAlso = new LinearLayout(getContext());
            llAlso.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            llAlso.setOrientation(LinearLayout.HORIZONTAL);

//            TextView txtSample = new TextView(getContext());
//            txtSample.setText(header);

//            llAlso.addView(txtSample);
//            txtSample.measure(0, 0);

            int widthSoFar = 0;//txtSample.getMeasuredWidth();
            for (ThemeSpec samItem : collection) {
                ThemeView themeView = new ThemeView(getContext(), null);
//                TextView txtSamItem = new TextView(this, null,
//                        android.R.attr.textColorLink);
////                txtSamItem.setText(samItem.Sample);
                themeView.setPadding(10, 0, 0, 0);

                themeView.measure(0, 0);
                widthSoFar += themeView.getMeasuredWidth();

                if (widthSoFar >= maxWidth) {
                    linearLayout.addView(llAlso);

                    llAlso = new LinearLayout(getContext());
                    llAlso.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    llAlso.setOrientation(LinearLayout.HORIZONTAL);

                    llAlso.addView(themeView);
                    widthSoFar = themeView.getMeasuredWidth();
                } else {
                    llAlso.addView(themeView);
                }
            }

            linearLayout.addView(llAlso);
        }
    }


    static class ThemeSpec {
        public final @StringRes int summary;
        public final @StyleRes int theme;
        public final @StyleRes int dialogTheme;


        ThemeSpec(@StringRes int summary, @StyleRes int theme, @StyleRes int dialogTheme) {
            this.summary = summary;
            this.theme = theme;
            this.dialogTheme = dialogTheme;
        }
    }
}