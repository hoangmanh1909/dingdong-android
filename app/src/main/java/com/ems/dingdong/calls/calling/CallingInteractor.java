package com.ems.dingdong.calls.calling;

import com.core.base.viper.Interactor;

public class CallingInteractor extends Interactor<CallingContract.Presenter> implements CallingContract.Interactor {
    public CallingInteractor(CallingContract.Presenter presenter) {
        super(presenter);
    }
}
