package com.ems.dingdong.functions.login.validation;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

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
