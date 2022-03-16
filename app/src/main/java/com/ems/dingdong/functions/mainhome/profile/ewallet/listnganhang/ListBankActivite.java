package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.functions.mainhome.profile.ProfileFragment;
import com.ems.dingdong.functions.mainhome.profile.ProfilePresenter;

public class ListBankActivite extends DingDongActivity {
    private ListBankFragment mFragment;

    @Override
    public ViewFragment onCreateFirstFragment() {
        mFragment = (ListBankFragment) new ListBankPresenter(this).getFragment();
        return mFragment;
    }
}
