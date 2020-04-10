package com.ems.dingdong.functions.mainhome.profile.prepaid.register;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;

public interface RegisterContract {
    interface Interactor extends IInteractor<RegisterContract.Presenter> {
        void register(String name, String id, String mobileNumber, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<RegisterContract.Presenter> {
        void showSuccess(boolean isSuccess);
    }

    interface Presenter extends IPresenter<RegisterContract.View, RegisterContract.Interactor> {
        void register(String name, String id, String mobileNumber);
    }
}
