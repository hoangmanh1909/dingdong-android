package com.vinatti.dingdong.functions.mainhome.main;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;

public class MainActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new MainPresenter(this).getFragment();
    }
}
