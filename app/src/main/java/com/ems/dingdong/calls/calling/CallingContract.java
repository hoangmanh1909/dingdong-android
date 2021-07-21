package com.ems.dingdong.calls.calling;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CallHistoryVHT;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.response.ResponseObject;

import retrofit2.Call;

public interface CallingContract {

    interface Interactor extends IInteractor<Presenter> {
        Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber,
                                                 String callForwardType, String hotlineNumber,
                                                 String ladingCode, CommonCallback<SimpleResult> callback);
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

        Long getSessionId();

        void createCallHistoryVHTOut();

        void createCallHistoryVHTIn();

        void callForward(String phone, String parcelCode);

    }
}
