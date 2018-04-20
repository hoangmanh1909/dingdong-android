package com.vinatti.dingdong.notification;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;

public class NotificationActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        return (ViewFragment) new NotificationPresenter(this).getFragment();
    }
}
