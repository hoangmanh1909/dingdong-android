package com.vinatti.dingdong.functions.login.active;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ActiveResult;

/**
 * The Active Contract
 */
interface ActiveContract {

    interface Interactor extends IInteractor<Presenter> {
        void activeAuthorized(String mobileNumber, String activeCode, String codeDeviceActive, CommonCallback<ActiveResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showError(String message);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void activeAuthorized(String mobileNumber, String activeCode, String codeDeviceActive);
        String getMobileNumber();
    }
}



