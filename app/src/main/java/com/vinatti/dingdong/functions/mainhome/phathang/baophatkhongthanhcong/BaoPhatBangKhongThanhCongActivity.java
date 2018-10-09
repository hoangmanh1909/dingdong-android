package com.vinatti.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.MenuItem;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.utiles.Constants;
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
        int textSize18 = getResources().getDimensionPixelSize(R.dimen.text_size_18sp);
        int textSize14 = getResources().getDimensionPixelSize(R.dimen.text_size_14sp);
        String text1 = "Báo phát thành công";
        String text2 = "";

        if (TextUtils.isEmpty(Constants.SHIFT)) {
            text2 = ("Bạn chưa chọn ca làm việc");
        } else {
            text2 = ("Ca làm việc: Ca " + Constants.SHIFT);
        }

        SpannableString span1 = new SpannableString(text1);
        span1.setSpan(new AbsoluteSizeSpan(textSize18), 0, text1.length(), SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString span2 = new SpannableString(text2);
        span2.setSpan(new AbsoluteSizeSpan(textSize14), 0, text2.length(), SPAN_INCLUSIVE_INCLUSIVE);

// let's put both spans together with a separator and all
        CharSequence finalText = TextUtils.concat(span1, "\n", span2);
        ViewUtils.viewActionBar(getSupportActionBar(), getLayoutInflater(), finalText);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }
}
