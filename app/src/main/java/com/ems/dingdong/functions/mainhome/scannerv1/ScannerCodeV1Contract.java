package com.ems.dingdong.functions.mainhome.scannerv1;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;

/**
 * The ScannerCode Contract
 */
interface ScannerCodeV1Contract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        BarCodeCallback getDelegate();
    }
}



