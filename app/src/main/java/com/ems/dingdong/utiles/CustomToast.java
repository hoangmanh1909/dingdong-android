package com.ems.dingdong.utiles;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ems.dingdong.R;


public class CustomToast extends Toast {

    public static long SHORT = 2000;
    public static int LONG = 2000;

    public CustomToast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, int duration, String content, int type) {
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        View layout = LayoutInflater.from(context).inflate(R.layout.customtoast_success, null, false);
        TextView toast_text = layout.findViewById(R.id.toast_text);
//        ImageView img = layout.findViewById(R.id.toast_icon);
        if (type == Constants.SUCCESS) {
//            img.setImageResource(R.drawable.ic_shield_checkmark_sharp);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast_text.setText(content);
        toast.setView(layout);
        return toast;
    }
}