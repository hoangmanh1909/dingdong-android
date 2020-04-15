package com.ems.dingdong.calls;

import com.core.base.viper.Interactor;

public class IncomingCallInteractor extends Interactor<IncomingCallContract.Presenter> implements IncomingCallContract.Interactor {
    public IncomingCallInteractor(IncomingCallContract.Presenter presenter) {
        super(presenter);
    }
}
