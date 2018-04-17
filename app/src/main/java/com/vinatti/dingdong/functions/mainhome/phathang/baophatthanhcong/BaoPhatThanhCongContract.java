package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.BarCodeCallback;

/**
 * The BaoPhatThanhCong Contract
 */
interface BaoPhatThanhCongContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);
    }
}



