package com.enamakel.backseattester.util;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;


public class FontCache {
    static final ArrayMap<String, Typeface> typefaceMap;


    static {
        typefaceMap = new ArrayMap<>();
    }


    /**
     * Returns the typeface instance of the given font. The function caches each font as it is being
     * called, to avoid loading it multiple times.
     *
     * @param context      The context of the application
     * @param typefaceName The font name to load.
     * @return A {@link Typeface} instance of the font. null if the font isin't in the assets
     * folder.
     */
    public static Typeface get(Context context, String typefaceName) {
        if (TextUtils.isEmpty(typefaceName)) return null;

        // sometimes the typeface name contains the '.ttf' part twice. This fixes that.
        String fontName = typefaceName.replaceAll(".ttf", "") + ".ttf";

        // Check if the typeface is in our cache.
        if (!typefaceMap.containsKey(fontName)) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            typefaceMap.put(fontName, typeface);
        }

        return typefaceMap.get(fontName);
    }


    /**
     * Returns the 'bold' version of the given font.
     *
     * @param context      The context of the application
     * @param typefaceName The font name to load.
     * @return A {@link Typeface} instance of the font. null if the font isin't in the assets
     * folder.
     */
    public static Typeface getBold(Context context, String typefaceName) {
        if (TextUtils.isEmpty(typefaceName)) return null;

        // Remove the -bold component if it exists.
        typefaceName = typefaceName.replace("-bold", "");

        // Append '-bold' to the font name.
        typefaceName = typefaceName.replace(".ttf", "") + "-bold.ttf";

        return get(context, typefaceName);
    }
}