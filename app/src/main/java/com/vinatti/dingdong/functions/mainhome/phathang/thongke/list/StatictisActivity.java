package com.vinatti.dingdong.functions.mainhome.phathang.thongke.list;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;

public class StatictisActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        return (ViewFragment) new StatisticPresenter(this).getFragment();
    }
}
