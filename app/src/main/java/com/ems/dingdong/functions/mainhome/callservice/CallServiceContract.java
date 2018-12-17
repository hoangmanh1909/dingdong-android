package com.ems.dingdong.functions.mainhome.callservice;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;

/**
 * The CallService Contract
 */
interface CallServiceContract {

    interface Interactor extends IInteractor<Presenter> {
        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showCallSuccess();

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void callForward(String phone);

        void pushHistory();
    }
}



