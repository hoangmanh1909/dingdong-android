package com.ems.dingdong.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ems.dingdong.R;

public class CustomDiapadButton extends RelativeLayout {

    private Context mContext;
    private TextView digitText;
    private TextView charText;
    private String digit;
    private String content;

    public CustomDiapadButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public CustomDiapadButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray ta = mContext.obtainStyledAttributes(attributeSet, R.styleable.CustomDigitView, 0, 0);
        digit = ta.getString(R.styleable.CustomDigitView_tvDigit);
        content = ta.getString(R.styleable.CustomDigitView_tvContent);
        View v = LayoutInflater.from(mContext).inflate(R.layout.widget_custom_digit, this);
        digitText = v.findViewById(R.id.tv_digit);
        charText = v.findViewById(R.id.tv_content);
        if (!TextUtils.isEmpty(digit)) {
            digitText.setText(digit);
        }
        if (!TextUtils.isEmpty(content)) {
            charText.setText(content);
        } else {
            charText.setText("");
        }
    }

}
