package com.ems.dingdong.views;

import android.content.Context;
import android.graphics.Typeface;


import com.ems.dingdong.utiles.Log;

import java.util.Hashtable;

/**
 * Created by namnh40 on 7/10/2015.
 */
public class Typefaces {

    private static final String TAG = "Typefaces";

    private static final Hashtable<String, Typeface> CACHE = new Hashtable<String, Typeface>();

    public static Typeface get(String fontFamily) {
        Typeface t = get(fontFamily, Typeface.NORMAL);
        return cachedTypeface(fontFamily, t);
    }

    public static Typeface get(String familyName, int style) {
        Typeface t = Typeface.create(familyName, style);
        return cachedTypeface(familyName, t);
    }

    public static Typeface get(Typeface typeface, String id) {
        return cachedTypeface(id, typeface);
    }

    public static Typeface get(Context context, String assetPath) {
        Typeface t = Typeface.createFromAsset(context.getAssets(), assetPath);
        return cachedTypeface(assetPath, t);
    }

    private static Typeface cachedTypeface(String assetPath, Typeface t) {
        synchronized (CACHE) {
            if (!CACHE.containsKey(assetPath)) {
                try {
                    CACHE.put(assetPath, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get setTypeface '" + assetPath, e);
                    return null;
                }
            }
            return CACHE.get(assetPath);
        }
    }

    public static Typeface getTypefaceRobotoNormal(Context context) {
        return get(context, "fonts/Roboto-Regular.ttf");
    }

    public static Typeface getTypefaceRobotoMedium(Context context){
        return get(context,"fonts/roboto_medium.ttf");
    }

    public static Typeface getTypefaceRobotoBold(Context context) {
        return get(context, "fonts/Roboto-Bold.ttf");
    }

    public static Typeface getTypefaceRobotoItalic(Context context) {
        return get(context, "fonts/Roboto-Italic.ttf");
    }

    public static Typeface getTypefaceTahomaNormal(Context context) {
        return get(context, "fonts/tahoma.ttf");
    }

    public static Typeface getTypefaceTahomaBold(Context context) {
        return get(context, "fonts/tahomabd.ttf");
    }
    public static Typeface getTypefaceHelvetica(Context context) {
        return get(context, "fonts/Helvetica.otf");
    }
    public static Typeface getTypefaceHelveticaBold(Context context) {
        return get(context, "fonts/Helvetica-Bold.otf");
    }
}


