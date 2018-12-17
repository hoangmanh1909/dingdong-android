package com.ems.dingdong.functions.mainhome.phathang.baophatthanhcong.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.CommonObject;

/**
 * The BaoPhatThanhCongDetail Contract
 */
interface BaoPhatThanhCongDetailContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        CommonObject getBaoPhatThanhCong();
    }
}



