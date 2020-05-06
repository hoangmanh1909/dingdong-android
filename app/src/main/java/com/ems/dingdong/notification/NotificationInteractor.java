package com.ems.dingdong.notification;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.NotificationResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The Notification interactor
 */
class NotificationInteractor extends Interactor<NotificationContract.Presenter>
        implements NotificationContract.Interactor {

    NotificationInteractor(NotificationContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getNotifications(String mobileNumber, CommonCallback<NotificationResult> callback) {
        NetWorkController.getNotifications(mobileNumber, callback);
    }

    @Override
    public void updateRead(String mobileNumber, CommonCallback<SimpleResult> callback) {
        NetWorkController.updateRead(mobileNumber, callback);
    }
}
