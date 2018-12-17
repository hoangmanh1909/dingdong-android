package com.ems.dingdong.functions.mainhome.location;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectResult;

/**
 * The Location Contract
 */
interface LocationContract {

    interface Interactor extends IInteractor<Presenter> {
        void findLocation(String ladingCode, CommonCallback<CommonObjectResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showFindLocationSuccess(CommonObject commonObject);

        void showEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);
        void findLocation(String ladingCode);
    }
}



