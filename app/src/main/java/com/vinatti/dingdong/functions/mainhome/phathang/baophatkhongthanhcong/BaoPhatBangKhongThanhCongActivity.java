package com.vinatti.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;

public class BaoPhatBangKhongThanhCongActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        return (ViewFragment) new BaoPhatKhongThanhCongPresenter(this).getFragment();
    }
}
