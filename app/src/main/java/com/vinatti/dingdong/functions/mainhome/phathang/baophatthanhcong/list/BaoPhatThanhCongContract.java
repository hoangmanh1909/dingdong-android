package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.CommonObjectResult;

import java.util.List;

/**
 * The BaoPhatThanhCong Contract
 */
interface BaoPhatThanhCongContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchParcelCodeDelivery(String parcelCode, CommonCallback<CommonObjectResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showData(CommonObject commonObject);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);

        void searchParcelCodeDelivery(String parcelCode);

        void showDetail(CommonObject commonObject);

        void pushViewConfirmAll(List<CommonObject> list);
    }
}



