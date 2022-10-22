package com.ems.dingdong.utiles;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class EditTextUtils {
    private static String character = "•π¶{}\\%[]@#_&-()/*\"':;!?.,-*/,.  %,";

    public static void editTextListener(final EditText edtMoneyNumber) {
        edtMoneyNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtMoneyNumber.removeTextChangedListener(this);
                if (!TextUtils.isEmpty(s.toString())) {
                    try {
                        edtMoneyNumber.setText(NumberUtils.formatVinatti(Long.parseLong(s.toString().replace(".", ""))));
                    } catch (Exception ex) {
                        Logger.w(ex);
                    }
                }
                edtMoneyNumber.addTextChangedListener(this);
                edtMoneyNumber.setSelection(edtMoneyNumber.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static InputFilter EMOJI_FILTER = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int index = start; index < end; index++) {
                char c = source.charAt(index);
                if (Character.isHighSurrogate(c) || Character.isLowSurrogate(c)) {
                    return "";
                }
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL || type == Character.CURRENCY_SYMBOL
                        || type == Character.MATH_SYMBOL || type == Character.MODIFIER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    };

    public static InputFilter SPECIAL_FILTER = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            if (source != null && character.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    public static void editHintError(final TextView editText, final TextView editTextError) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    editTextError.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
