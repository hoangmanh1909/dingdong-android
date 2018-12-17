package com.ems.dingdong.functions.login.validation;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;

/**
 * The Validation Contract
 */
interface ValidationContract {

    interface Interactor extends IInteractor<Presenter> {
        void validationAuthorized(String mobileNumber, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {

        void showError(String message);



    }

    interface Presenter extends IPresenter<View, Interactor> {
        void validationAuthorized(String mobileNumber);

    }
}



