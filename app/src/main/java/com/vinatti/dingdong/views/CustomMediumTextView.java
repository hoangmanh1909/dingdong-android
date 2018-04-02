package com.vinatti.dingdong.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by namnh40 on 7/10/2015.
 */
public class CustomMediumTextView extends AppCompatTextView {

    public CustomMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomMediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomMediumTextView(Context context) {
        super(context);
        init();

    }

    private void init() {
        setTypefaceRobotoMedium();
    }

    public void setTypefaceRobotoMedium(){
        Typeface typeface = Typefaces.getTypefaceRobotoMedium(getContext());
        if(typeface!=null){
            setTypeface(typeface);
        }
    }
}
