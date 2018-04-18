package com.vinatti.dingdong.functions.mainhome.location;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;

/**
 * The Location Fragment
 */
public class LocationFragment extends ViewFragment<LocationContract.Presenter> implements LocationContract.View {

    public static LocationFragment getInstance() {
        return new LocationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_location;
    }
}
