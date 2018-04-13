package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.XacNhanTin;
import com.vinatti.dingdong.model.XacNhanTinResult;

/**
 * The XacNhanTinDetail Contract
 */
interface HoanThanhTinDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<XacNhanTinResult> commonCallback);

        void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,
                                        String statusCode, String quantity, String collectReason, String pickUpDate,
                                        String pickUpTime, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showErrorAndBack(String message);

        void showView(XacNhanTin xacNhanTin);

        void showMessage(String message);

        void showError(String message);

        void controlViews();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,
                                        String statusCode, String quantity, String collectReason, String pickUpDate,
                                        String pickUpTime);

        void searchOrderPostman();

        XacNhanTin getXacNhanTin();
    }
}



