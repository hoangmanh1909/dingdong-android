package com.vinatti.dingdong.functions.login;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;

/**
 * The Login Fragment
 */
public class LoginFragment extends ViewFragment<LoginContract.Presenter> implements LoginContract.View {

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }
}
