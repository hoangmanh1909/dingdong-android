package com.vinatti.dingdong.functions.home;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;

/**
 * The Home Fragment
 */
public class HomeFragment extends ViewFragment<HomeContract.Presenter> implements HomeContract.View {

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
}
