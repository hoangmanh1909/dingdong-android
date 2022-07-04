package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.SimpleResult;

import io.reactivex.Single;

/**
 * The Phone Contract
 */
interface PhoneContract {

    interface Interactor extends IInteractor<Presenter> {
        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, String PostmanId, String POcode, CommonCallback<SimpleResult> callback);

        void updateMobile(String code, String type, String mobileNumber, CommonCallback<SimpleResult> commonCallback);

        Single<SimpleResult> ddCall(CallLiveMode callLiveMode);
    }

    interface View extends PresentView<Presenter> {
        void showCallSuccess(String phone);

        void showError(String message);

        void showView(String phone, String message);

        void showCallLive(String phone);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        String getPhone();
        String getCode();
        void setNumberPhone(String phone);

        void callForward(String phone);

        void ddCall(CallLiveMode r);

        void updateMobile(String phone);
    }
}



