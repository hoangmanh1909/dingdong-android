package com.vinatti.dingdong.functions.mainhome.gomhang;

import com.core.base.viper.Interactor;

/**
 * The GomHang interactor
 */
class GomHangInteractor extends Interactor<GomHangContract.Presenter>
        implements GomHangContract.Interactor {

    GomHangInteractor(GomHangContract.Presenter presenter) {
        super(presenter);
    }
}
