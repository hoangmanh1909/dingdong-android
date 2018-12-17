package com.ems.dingdong.functions.mainhome.phathang.thongke.tabs;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;

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
