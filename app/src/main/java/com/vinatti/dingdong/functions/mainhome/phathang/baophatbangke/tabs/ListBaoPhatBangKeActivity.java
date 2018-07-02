package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.tabs;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;

public class ListBaoPhatBangKeActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        return (ViewFragment) new BangKeBaoPhatContainerPresenter(this).getFragment();
    }
}
