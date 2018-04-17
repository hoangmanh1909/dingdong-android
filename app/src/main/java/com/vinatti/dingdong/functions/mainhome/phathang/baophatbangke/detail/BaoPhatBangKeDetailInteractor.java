package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.detail;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The BaoPhatBangKeDetail interactor
 */
class BaoPhatBangKeDetailInteractor extends Interactor<BaoPhatBangKeDetailContract.Presenter>
        implements BaoPhatBangKeDetailContract.Interactor {

    BaoPhatBangKeDetailInteractor(BaoPhatBangKeDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getReasons(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasons(commonCallback);
    }


}
