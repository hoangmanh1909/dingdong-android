package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank;

import com.core.base.viper.ViewFragment;

public class SeabankFragment extends ViewFragment<SeabankContract.Presenter> implements SeabankContract.View {

    public static SeabankFragment getInstance() {
        return new SeabankFragment();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
