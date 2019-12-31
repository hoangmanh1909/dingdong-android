package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;

/**
 * The XacNhanTinDetail Contract
 */
interface XacNhanTinDetailContract {

    interface Interactor extends IInteractor<Presenter> {
       // void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> commonCallback);

        void confirmOrderPostmanCollect(String orderPostmanID, String employeeID,
                                        String statusCode, String confirmReason, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
      //  void showErrorAndBack(String message);

        void showView(CommonObject commonObject);

        void showMessage(String message);

        void showError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void confirmOrderPostmanCollect(String orderPostmanID, String employeeID, String statusCode, String reason);

       // void searchOrderPostman();
        CommonObject getCommonObject();
    }
}



