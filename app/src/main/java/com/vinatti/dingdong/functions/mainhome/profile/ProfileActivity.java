package com.vinatti.dingdong.functions.mainhome.profile;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;


/**
 * Created by DieuPC on 3/5/2018.
 */

public class ProfileActivity extends DingDongActivity {
    private ProfileFragment mFragment;

    @Override
    public ViewFragment onCreateFirstFragment() {
        mFragment = (ProfileFragment) new ProfilePresenter(this).getFragment();
        return mFragment;
    }

}
