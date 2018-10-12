package com.vinatti.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.StringUtils;
import com.vinatti.dingdong.utiles.ViewUtils;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class BaoPhatBangKhongThanhCongActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        return (ViewFragment) new BaoPhatKhongThanhCongPresenter(this).getFragment();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String text1 = "Báo phát không thành công";
        CharSequence finalText = StringUtils.getCharSequence(text1, this);
        ViewUtils.viewActionBar(getSupportActionBar(), getLayoutInflater(), finalText);
    }
}
