package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.XacNhanTinResult;

/**
 * The XacNhanTinDetail Contract
 */
interface XacNhanTinDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<XacNhanTinResult> commonCallback);

        void confirmOrderPostmanCollect(String orderPostmanID, String employeeID,
                                        String statusCode, String confirmReason, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showErrorAndBack(String message);

        void showView(CommonObject commonObject);

        void showMessage(String message);

        void showError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void confirmOrderPostmanCollect(String orderPostmanID, String employeeID, String statusCode, String reason);

        void searchOrderPostman();
        CommonObject getCommonObject();
    }
}



