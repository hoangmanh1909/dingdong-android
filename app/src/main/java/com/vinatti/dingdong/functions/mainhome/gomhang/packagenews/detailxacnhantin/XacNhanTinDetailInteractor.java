package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.XacNhanTinResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The XacNhanTinDetail interactor
 */
class XacNhanTinDetailInteractor extends Interactor<XacNhanTinDetailContract.Presenter>
        implements XacNhanTinDetailContract.Interactor {

    XacNhanTinDetailInteractor(XacNhanTinDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<XacNhanTinResult> commonCallback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, commonCallback);
    }

    @Override
    public void confirmOrderPostmanCollect(String orderPostmanID, String employeeID, String statusCode, String confirmReason, CommonCallback<SimpleResult> callback) {
        NetWorkController.confirmOrderPostmanCollect(orderPostmanID, employeeID, statusCode, confirmReason, callback);
    }
}
