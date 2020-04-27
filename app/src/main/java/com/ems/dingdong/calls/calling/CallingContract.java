package com.ems.dingdong.calls.calling;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.stringee.call.StringeeCall;

public interface CallingContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {

        StringeeCall getStringeeCall();

        String getCallId();

        int getCallType();

        void setCallType(int type);

        String getCallerNumber();

        String getCalleeNumber();

        void openDiapadScreen();

    }
}
