package com.vinatti.dingdong.functions.mainhome.phathang.thongke.tabs;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.thongke.list.StatisticPresenter;

public class StatictisActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        return (ViewFragment) new ThongKeTabsPresenter(this).getFragment();
    }
}
