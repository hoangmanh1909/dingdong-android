package com.ems.dingdong.views.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;


import com.ems.dingdong.utiles.ViewUtils;
import com.ems.dingdong.R;

import butterknife.BindView;


public class FormItemTextView extends FormItemText {
    @BindView(R.id.item_edittext_devider)
    View mDevider;

    public FormItemTextView(Context context) {
        super(context);
    }

    public FormItemTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initAttribute(TypedArray input) {
        super.initAttribute(input);
        if (input.hasValue(R.styleable.FormItem_android_inputType)) {
            int inputType = input.getInt(R.styleable.FormItem_android_inputType, EditorInfo.TYPE_NULL);
            mTextView.setInputType(inputType);
        }

        if (input.hasValue(R.styleable.FormItem_android_minLines)) {
            int minLines = input.getInt(R.styleable.FormItem_android_minLines, 1);
            mTextView.setMinLines(minLines);
        }
        if (input.hasValue(R.styleable.FormItem_formShowUnderline)) {
            boolean vi = input.getBoolean(R.styleable.FormItem_formShowUnderline, false);
            mDevider.setVisibility(vi ? VISIBLE : GONE);
        }
        if (input.hasValue(R.styleable.FormItem_formDrawablePadding)) {
            int gravity = input.getDimensionPixelSize(R.styleable.FormItem_formDrawablePadding, 0);
            LayoutParams params = (LayoutParams) mTextView.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, gravity, params.bottomMargin);
            mTextView.setLayoutParams(params);
        }
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.requestFocus();
            }
        });

        chageText();
        initTouchListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    ViewUtils.setBackgroundColor(mDevider, R.color.devider_focus, getContext());
                } else {
                    ViewUtils.setBackgroundColor(mDevider, R.color.grey, getContext());
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_form_item_text_view;
    }

    public void setInvalidate() {
        ViewUtils.setBackgroundColor(mDevider, android.R.color.holo_red_light, getContext());
    }

    public void setValidate() {
        ViewUtils.setBackgroundColor(mDevider, R.color.grey, getContext());
    }

    public TextView getTextView() {
        return mTextView;
    }
   /* public ImageView getImageViewLeft() {
        return mIcon;
    }*/
}
