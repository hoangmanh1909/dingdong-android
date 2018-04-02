package com.vinatti.dingdong.utiles;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by namnh40 on 8/4/2015.
 */
public class Toast {

    public static final int TIME_SHOW = 2500;

    public static void showToast(Context context, int stringResourceId) {
        if (context == null)
            return;
        android.widget.Toast.makeText(context,context.getText(stringResourceId), android.widget.Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message))
            return;

        android.widget.Toast.makeText(context,message, android.widget.Toast.LENGTH_LONG).show();
    }
}
