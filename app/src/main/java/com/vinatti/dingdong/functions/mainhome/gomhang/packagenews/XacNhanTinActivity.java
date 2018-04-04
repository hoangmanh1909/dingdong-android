package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.functions.mainhome.main.MainPresenter;

public class XacNhanTinActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new XacNhanTinPresenter(this).getFragment();
    }
}
