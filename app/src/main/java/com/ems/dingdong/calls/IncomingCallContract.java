package com.ems.dingdong.calls;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.stringee.call.StringeeCall;

public interface IncomingCallContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {

        StringeeCall getStringeeCall();

        String getCallId();

        int getCallType();

        String getCallerNumber();

        String getCalleeNumber();

    }
}
