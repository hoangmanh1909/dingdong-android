package com.ems.dingdong.notification;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.NotificationInfo;
import com.ems.dingdong.model.NotificationResult;
import com.ems.dingdong.model.SimpleResult;

import java.util.List;

/**
 * The Notification Contract
 */
interface NotificationContract {

    interface Interactor extends IInteractor<Presenter> {
        void getNotifications(String mobileNumber, CommonCallback<NotificationResult> callback);

        void updateRead(String mobileNumber, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showListNotifications(List<NotificationInfo> list);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getNotifications(String mobileNumber);

        void updateRead(String mobileNumber);

        void showCallHistory();

        void showCollectFragment();

        void showDeliveryFragment();

    }
}



