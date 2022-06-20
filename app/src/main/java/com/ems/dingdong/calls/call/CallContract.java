package com.ems.dingdong.calls.call;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;

import retrofit2.Call;

public interface CallContract {

    interface Interactor extends IInteractor<Presenter> {
        Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber,
                                                 String callForwardType, String hotlineNumber,
                                                 String ladingCode,  String PostmanId, String POCode,CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showCallError(String mess);

        void showCallSuccess();
    }

    interface Presenter extends IPresenter<View, Interactor> {

        String getCallId();

        int getCallType();

        int getAppToApp();

        void setCallType(int type);

        String getCallerNumber();

        String getCalleeNumber();


        void callForward(String phone, String parcelCode, String PostmanId, String POCode);

    }
}
