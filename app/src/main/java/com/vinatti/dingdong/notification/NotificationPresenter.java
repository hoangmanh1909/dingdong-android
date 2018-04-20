package com.vinatti.dingdong.notification;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

/**
 * The Notification Presenter
 */
public class NotificationPresenter extends Presenter<NotificationContract.View, NotificationContract.Interactor>
        implements NotificationContract.Presenter {

    public NotificationPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public NotificationContract.View onCreateView() {
        return NotificationFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public NotificationContract.Interactor onCreateInteractor() {
        return new NotificationInteractor(this);
    }
}
