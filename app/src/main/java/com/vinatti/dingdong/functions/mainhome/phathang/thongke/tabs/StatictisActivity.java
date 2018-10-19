package com.vinatti.dingdong.functions.mainhome.phathang.thongke.tabs;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.thongke.list.StatisticPresenter;

public class StatictisActivity extends DingDongActivity {
    ThongKeTabsFragment thongKeTabsFragment;

    @Override
    public ViewFragment onCreateFirstFragment() {

        thongKeTabsFragment = (ThongKeTabsFragment) new ThongKeTabsPresenter(this).getFragment();
        return thongKeTabsFragment;
    }

    public void setShift(String shiftName, int position) {
        thongKeTabsFragment.setShift(shiftName, position);
    }
}
