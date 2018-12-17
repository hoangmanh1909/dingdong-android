package com.ems.dingdong.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by namnh40 on 7/10/2015.
 */
public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextView(Context context) {
        super(context);
        init();

    }

    private void init() {
        setTypeFaceRobotoNormal();
    }

    public void setTypeFaceRobotoNormal() {
        Typeface typeface = Typefaces.getTypefaceRobotoNormal(getContext());
        if (typeface != null)
            setTypeface(typeface);
    }
}
