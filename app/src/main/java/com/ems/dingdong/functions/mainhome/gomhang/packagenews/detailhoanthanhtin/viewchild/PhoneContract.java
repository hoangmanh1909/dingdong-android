package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;

/**
 * The Phone Contract
 */
interface PhoneContract {

    interface Interactor extends IInteractor<Presenter> {
        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, CommonCallback<SimpleResult> callback);
        void updateMobile(String code, String mobileNumber, CommonCallback<SimpleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showCallSuccess();

        void showError(String message);

        void showView();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        String getPhone();

        void callForward(String phone);

        void updateMobile(String phone);
    }
}



