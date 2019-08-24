package com.ems.dingdong.views;

import android.content.Context;
import android.graphics.Typeface;

import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by namnh40 on 7/22/2015.
 */
public class CustomEditText extends AppCompatEditText {
    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
