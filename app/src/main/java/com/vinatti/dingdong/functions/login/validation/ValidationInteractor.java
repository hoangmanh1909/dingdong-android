package com.vinatti.dingdong.functions.login.validation;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The Validation interactor
 */
class ValidationInteractor extends Interactor<ValidationContract.Presenter>
        implements ValidationContract.Interactor {

    ValidationInteractor(ValidationContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void validationAuthorized(String mobileNumber, CommonCallback<SimpleResult> callback) {
        NetWorkController.validationAuthorized(mobileNumber, callback);
    }
}
