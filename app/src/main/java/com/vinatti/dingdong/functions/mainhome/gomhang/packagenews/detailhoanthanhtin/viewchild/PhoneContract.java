package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;

/**
 * The Phone Contract
 */
interface PhoneContract {

    interface Interactor extends IInteractor<Presenter> {
        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showCallSuccess();

        void showError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        String getPhone();

        void callForward(String phone);
    }
}



