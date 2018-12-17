package com.ems.dingdong.notification;

import com.core.base.viper.Interactor;

/**
 * The Notification interactor
 */
class NotificationInteractor extends Interactor<NotificationContract.Presenter>
        implements NotificationContract.Interactor {

    NotificationInteractor(NotificationContract.Presenter presenter) {
        super(presenter);
    }
}
