package com.vinatti.dingdong.notification;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;

/**
 * The Notification Fragment
 */
public class NotificationFragment extends ViewFragment<NotificationContract.Presenter> implements NotificationContract.View {

    public static NotificationFragment getInstance() {
        return new NotificationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification;
    }
}
