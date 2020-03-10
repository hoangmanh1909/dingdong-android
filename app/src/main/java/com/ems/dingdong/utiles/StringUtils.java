package com.ems.dingdong.utiles;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.ems.dingdong.R;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class StringUtils {

    public static CharSequence getCharSequence(String text1, Context context) {
        int textSize18 = context.getResources().getDimensionPixelSize(R.dimen.text_size_16sp);
        int textSize14 = context.getResources().getDimensionPixelSize(R.dimen.text_size_14sp);
        String text2;
        if (TextUtils.isEmpty(Constants.SHIFT)) {
            text2 = ("Bạn chưa chọn ca làm việc");

        } else {
            text2 = ("Ca làm việc: Ca " + Constants.SHIFT);
        }

        SpannableString span1 = new SpannableString(text1);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        span1.setSpan(boldSpan, 0, text1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new AbsoluteSizeSpan(textSize18), 0, text1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)), 0, text1.length(), 0);// set color

        SpannableString span2 = new SpannableString(text2);
        span2.setSpan(new AbsoluteSizeSpan(textSize14), 0, text2.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.sub_title_white)), 0, text2.length(), 0);// set color

        CharSequence finalText = TextUtils.concat(span1, "\n", span2);
        return finalText;
    }

    public static CharSequence getCharSequence(String text1, String text2, Context context) {
        int textSize18 = context.getResources().getDimensionPixelSize(R.dimen.text_size_16sp);
        int textSize14 = context.getResources().getDimensionPixelSize(R.dimen.text_size_14sp);
        SpannableString span1 = new SpannableString(text1);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        span1.setSpan(boldSpan, 0, text1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new AbsoluteSizeSpan(textSize18), 0, text1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)), 0, text1.length(), 0);// set color
        CharSequence finalText;
        if (!TextUtils.isEmpty(text2)) {
            SpannableString span2 = new SpannableString(text2);
            span2.setSpan(new AbsoluteSizeSpan(textSize14), 0, text2.length(), SPAN_INCLUSIVE_INCLUSIVE);
            span2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.sub_title_white)), 0, text2.length(), 0);// set color
            finalText = TextUtils.concat(span1, "\n", span2);
        } else {
            finalText = span1;
        }
        return finalText;
    }

    public static String getLadingCode(String source) {
        return source.substring(8, 21);
    }
}
