package com.ems.dingdong.functions.mainhome.callservice;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;


/**
 * Created by DieuPC on 3/5/2018.
 */

public class CallActivity extends DingDongActivity {

    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new CallServicePresenter(this).getFragment();
    }

}
