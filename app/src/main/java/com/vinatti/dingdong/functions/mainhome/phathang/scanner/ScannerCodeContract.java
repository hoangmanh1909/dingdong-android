package com.vinatti.dingdong.functions.mainhome.phathang.scanner;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.BarCodeCallback;

/**
 * The ScannerCode Contract
 */
interface ScannerCodeContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        BarCodeCallback getDelegate();
    }
}



