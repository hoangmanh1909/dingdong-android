package com.ems.dingdong.calls.calling;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CallHistoryVHT;
import com.ems.dingdong.model.response.ResponseObject;

public interface CallingContract {

    interface Interactor extends IInteractor<Presenter> {
//        void createCallHistoryVHT(String code, String data, String signature, CommonCallback<ResponseObject> callback);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {

        String getCallId();

        int getCallType();

        void setCallType(int type);

        String getCallerNumber();

        String getCalleeNumber();

        Long getSessionId();

        void createCallHistoryVHTOut();

        void createCallHistoryVHTIn();

    }
}
