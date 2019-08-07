package com.ems.dingdong.views.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import com.ems.dingdong.utiles.KeyboardUtil;
import com.ems.dingdong.R;

import butterknife.BindView;


public abstract class FormItemText extends FormItem {

    @BindView(R.id.form_item_value_text)
    protected TextView mTextView;
    private TextWatcher textWatcher;

    public FormItemText(Context context) {
        super(context);
    }

    public FormItemText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initAttribute(TypedArray input) {
        String text = input.getString(R.styleable.FormItem_formValue);
        mTextView.setText(text);
        text = input.getString(R.styleable.FormItem_formHint);
        mTextView.setHint(text);

        if (input.hasValue(R.styleable.FormItem_android_maxLines)) {
            int maxLines = input.getInt(R.styleable.FormItem_android_maxLines, 1);
            mTextView.setMaxLines(maxLines);
        } else {
            mTextView.setLines(1);
        }
        if (input.hasValue(R.styleable.FormItem_android_gravity)) {
            int gravity = input.getInt(R.styleable.FormItem_android_gravity, 5);
            mTextView.setGravity(gravity);
        }
        if (input.hasValue(R.styleable.FormItem_android_textColor)) {
            int color = input.getColor(R.styleable.FormItem_android_textColor, Color.parseColor("#253854"));
            mTextView.setTextColor(color);
        }
        if (input.hasValue(R.styleable.FormItem_formTextStyle)) {
            int textStyle = input.getInt(R.styleable.FormItem_formTextStyle, 0);
            mTextView.setTypeface(mTextView.getTypeface(),textStyle);
        }
    }

    public String getText() {
        return mTextView.getText().toString().trim();
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    protected final void initTouchListener(final OnFocusChangeListener listener) {
        mTextView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (listener != null) {
                    listener.onFocusChange(v, hasFocus);
                }
                if (!hasFocus && mListener != null) {
                    mListener.handleEvent(FormEvent.TEXT_LOST_FOCUS);
                }
                if (hasFocus && mListener != null) {
                    mListener.handleEvent(FormEvent.TEXT_FOCUS);
                }

            }
        });
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //beforeTextChanged
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mListener != null) {
                    mListener.handleEvent(FormEvent.TEXT_CHANGGING);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mListener != null) {
                    mListener.handleEvent(FormEvent.TEXT_CHANGE);
                }
            }
        };
        mTextView.addTextChangedListener(textWatcher);

  /*  mIcon.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        v.getParent().requestDisallowInterceptTouchEvent(true);
        mTextView.requestFocus();
        KeyboardUtil.show(mTextView);
      }
    });*/

        mTextView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
    }

    public void removeTextChangedListener() {
        mTextView.removeTextChangedListener(textWatcher);
    }

    public void addTextChangedListener() {
        mTextView.addTextChangedListener(textWatcher);
    }
    public void addTextChangedListener(TextWatcher tWatcher) {
        mTextView.addTextChangedListener(tWatcher);
    }

    public void chageText() {
        initTouchListener(null);

    }

    public void setInputType(int inputType) {
        mTextView.setInputType(inputType);
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener onEditorActionListener) {
        mTextView.setOnEditorActionListener(onEditorActionListener);
    }
}
