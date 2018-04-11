package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.XacNhanTinResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The XacNhanTinDetail interactor
 */
class HoanThanhTinDetailInteractor extends Interactor<HoanThanhTinDetailContract.Presenter>
        implements HoanThanhTinDetailContract.Interactor {

    HoanThanhTinDetailInteractor(HoanThanhTinDetailContract.Presenter presenter) {
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
