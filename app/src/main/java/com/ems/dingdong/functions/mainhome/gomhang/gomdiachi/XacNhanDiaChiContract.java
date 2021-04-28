package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ParcelCodeInfo;

import java.util.ArrayList;

interface XacNhanDiaChiContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate, CommonCallback<CommonObjectListResult> callback);

    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<CommonObject> list);

        void showError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate);

        XacNhanDiaChiPresenter setType(int type);

        int getType();

        void confirmAllOrderPostman(ArrayList<CommonObject> list,String tenkhachhang);

        void showBarcode(BarCodeCallback barCodeCallback);

        void showConfirmParcelAddress(CommonObject commonObject, ArrayList<ParcelCodeInfo> confirmOrderPostmen);

        void showConfirmParcelAddressNoPostage(CommonObject commonObject);

        void showChiTietHoanThanhTin(CommonObject commonObject);

    }
}



