package com.ems.dingdong.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by namnh40 on 7/10/2015.
 */
public class CustomBoldTextView extends AppCompatTextView {

    public CustomBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomBoldTextView(Context context) {
        super(context);
        init();

    }

    private void init() {
        setTypeFaceHelvetica();
    }

    public void setTypeFaceHelvetica(){
        Typeface typeface = Typefaces.getTypefaceRobotoBold(getContext());
        if (typeface != null)
            setTypeface(typeface);
    }
}
