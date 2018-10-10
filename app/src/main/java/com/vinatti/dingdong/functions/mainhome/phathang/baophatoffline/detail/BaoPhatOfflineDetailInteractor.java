package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.detail;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The BaoPhatBangKeDetail interactor
 */
class BaoPhatOfflineDetailInteractor extends Interactor<BaoPhatOfflineDetailContract.Presenter>
        implements BaoPhatOfflineDetailContract.Interactor {

    BaoPhatOfflineDetailInteractor(BaoPhatOfflineDetailContract.Presenter presenter) {
        super(presenter);
    }


}
