package com.ems.dingdong.functions.login;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;

public class LoginActivity extends DingDongActivity {


    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new LoginPresenter(this).getFragment();
    }
}
