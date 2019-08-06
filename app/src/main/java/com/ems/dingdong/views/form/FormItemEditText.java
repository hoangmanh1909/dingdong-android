package com.ems.dingdong.views.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;


import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.redmadrobot.inputmask.PolyMaskTextChangedListener;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class FormItemEditText extends FormItemText {

    @BindView(R.id.clear_btn)
    protected ImageView btnClear;

    @BindView(R.id.item_edittext_devider)
    View mDevider;
    @BindView(R.id.ll_edit)
    View llEdit;

    private boolean isDateTimeInput = false;

    public FormItemEditText(Context context) {
        super(context);
    }

    public FormItemEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initAttribute(TypedArray inputFormEditText) {
        super.initAttribute(inputFormEditText);
        if (inputFormEditText.hasValue(R.styleable.FormItem_android_inputType)) {
            int inputType = inputFormEditText.getInt(R.styleable.FormItem_android_inputType, EditorInfo.TYPE_NULL);
            mTextView.setInputType(inputType);
        }

        if (inputFormEditText.hasValue(R.styleable.FormItem_android_minLines)) {
            int minLines = inputFormEditText.getInt(R.styleable.FormItem_android_minLines, 1);
            mTextView.setMinLines(minLines);
        }
        if (inputFormEditText.hasValue(R.styleable.FormItem_formShowUnderline)) {
            boolean vi = inputFormEditText.getBoolean(R.styleable.FormItem_formShowUnderline, false);
            mDevider.setVisibility(vi ? VISIBLE : GONE);
        }
        if (inputFormEditText.hasValue(R.styleable.FormItem_formDrawablePadding)) {
            int gravity = inputFormEditText.getDimensionPixelSize(R.styleable.FormItem_formDrawablePadding, 0);
            LayoutParams params = (LayoutParams) llEdit.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, gravity, params.bottomMargin);
            llEdit.setLayoutParams(params);
        }

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.requestFocus();
            }
        });
        mTextView.setHorizontallyScrolling(false);
        btnClear.setVisibility(GONE);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("");
            }
        });

        editTextListener();

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.performClick();
            }
        });

        initTouchListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    ViewUtils.setBackgroundColor(mDevider, R.color.devider_focus, getContext());
                    if ("".equals(mTextView.getText().toString())) {
                        btnClear.setVisibility(GONE);
                    } else {
                        btnClear.setVisibility(VISIBLE);
                    }
                } else {
                    ViewUtils.setBackgroundColor(mDevider, R.color.grey, getContext());
                    btnClear.setVisibility(GONE);
                }
            }
        });
    }

    public void setInvalidate() {
        ViewUtils.setBackgroundColor(mDevider, android.R.color.holo_red_light, getContext());
    }

    private void editTextListener() {
        if (isDateTimeInput) {
            final List<String> affineFormats = new ArrayList<>();
            affineFormats.add("[00]/[00]/[0000]");
            mTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
            mTextView.setKeyListener(DigitsKeyListener.getInstance("0123456789 -./"));

            final MaskedTextChangedListener listener = new PolyMaskTextChangedListener(
                    "[00]/[00]/[0000]",
                    affineFormats,
                    true,
                    (EditText) mTextView,
                    null,
                    new MaskedTextChangedListener.ValueListener() {
                        @Override
                        public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                            afterTextChange(extractedValue);
                        }
                    }
            );
            mTextView.addTextChangedListener(listener);
            mTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b && showClear) {
                        btnClear.setVisibility(VISIBLE);
                    } else {
                        btnClear.setVisibility(GONE);
                    }
                    if (b) {
                        ViewUtils.setBackgroundColor(mDevider, R.color.devider_focus, getContext());
                    } else {
                        ViewUtils.setBackgroundColor(mDevider, R.color.grey, getContext());
                    }
                }
            });
        } else {
            mTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //beforeTextChanged
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //onTextChanged
                }

                @Override
                public void afterTextChanged(Editable s) {
                    afterTextChange(s.toString());
                }
            });
        }
    }

    private void afterTextChange(String s) {
        if (mListener != null) {
            mListener.handleEvent(FormEvent.TEXT_CHANGE);
        }
        if (mTextView.hasFocus()) {
            if ("".equals(s.toString())) {
                btnClear.setVisibility(GONE);
            } else if (showClear && !isDateTimeInput) {
                btnClear.setVisibility(VISIBLE);
            }
            ViewUtils.setBackgroundColor(mDevider, R.color.devider_focus, getContext());
        } else {
            ViewUtils.setBackgroundColor(mDevider, R.color.grey, getContext());
            btnClear.setVisibility(GONE);
        }


    }

    public void setMaxlinesEditText(int lines) {
        mTextView.setMaxLines(lines);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_form_item_edit_text;
    }

    public void requestFocusView() {
        // InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mTextView.requestFocus();
        // mgr.showSoftInput(mTextView, InputMethodManager.SHOW_IMPLICIT);
    }

    public void setHorizontallyScrolling(boolean value) {
        mTextView.setHorizontallyScrolling(value);
    }

    public void setImeOptions(int imeActionNext) {
        mTextView.setImeOptions(imeActionNext);
    }

    public boolean isDateTimeInput() {
        return isDateTimeInput;
    }

    public void setDateTimeInput(boolean dateTimeInput) {
        isDateTimeInput = dateTimeInput;
        editTextListener();
    }

    public void setSelection(int length) {
        ((EditText) mTextView).setSelection(length);
    }

    public EditText getEditText() {
        return (EditText) mTextView;
    }

    public void setValidate() {
        ViewUtils.setBackgroundColor(mDevider, R.color.grey, getContext());
    }

    public void setHintText(String hintText) {
        mTextView.setHint(hintText);
    }
}
