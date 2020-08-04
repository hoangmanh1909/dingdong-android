package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.model.CommonObject;

interface CreateBD13OfflineContract {
    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {

        /**
         * Save image to local.
         *
         * @param request
         */
        void saveLocal(CommonObject request);

        /**
         * Show barcode scan screen.
         */
        void showBarcode(BarCodeCallback barCodeCallback);
    }
}
