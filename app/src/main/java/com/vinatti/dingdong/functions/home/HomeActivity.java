package com.vinatti.dingdong.functions.home;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;

public class HomeActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new HomePresenter(this).getFragment();
    }
}
