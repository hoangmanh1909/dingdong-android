package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;

public class CancelBD13Activity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (CancelBD13TabFragment)new CancelBD13TabPresenter(this).getFragment();
    }
}
