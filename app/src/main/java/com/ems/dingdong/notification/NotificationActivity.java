package com.ems.dingdong.notification;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;

public class NotificationActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        return (ViewFragment) new NotificationPresenter(this).getFragment();
    }
}
