package com.ems.dingdong.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ems.dingdong.R;

public class CustomImageView extends LinearLayout {
    Context context;
    private ImageView imgView;
    private CustomTextView lblView;
    private Drawable imgSrc;
    private String title;
    private boolean isItemSelected;

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }


    public void init(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, 0, 0);
        imgSrc = ta.getDrawable(R.styleable.CustomImageView_srcImg);
        title = ta.getString(R.styleable.CustomImageView_title);

        View v = LayoutInflater.from(context).inflate(R.layout.widget_customview_image, this);

        lblView = v.findViewById(R.id.lblView);
        imgView = v.findViewById(R.id.imgView);

        lblView.setText(title);
        imgView.setBackground(imgSrc);
    }

    public void setImageResource(int resource) {
        imgView.setImageResource(resource);
    }
}
