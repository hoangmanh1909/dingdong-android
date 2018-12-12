package com.vinatti.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import android.os.Bundle;
import android.view.MenuItem;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.utiles.StringUtils;
import com.vinatti.dingdong.utiles.ViewUtils;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
