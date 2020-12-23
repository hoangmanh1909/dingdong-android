package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.ParcelCodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * The CommonObject Contract
 */
interface XacNhanDiaChiContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate, CommonCallback<CommonObjectListResult> callback);
        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String route,
                                   String order, CommonCallback<CommonObjectListResult> callback);

        void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<ConfirmAllOrderPostmanResult> callback);
        void confirmParcelCode(ArrayList<ConfirmOrderPostman> request, CommonCallback<ConfirmAllOrderPostmanResult> callback);

    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<CommonObject> list);

        void showError(String message);

        void showResult(ConfirmAllOrderPostman allOrderPostman);

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

        void confirmAllOrderPostman(ArrayList<CommonObject> list);
        void confirmParcelCode(ArrayList<ParcelCodeInfo> list);//

        void showBarcode(BarCodeCallback barCodeCallback);

        void showDetailView(CommonObject commonObject);

        void showConfirmAddress(ArrayList<ConfirmOrderPostman> confirmOrderPostmen);
    }
}



