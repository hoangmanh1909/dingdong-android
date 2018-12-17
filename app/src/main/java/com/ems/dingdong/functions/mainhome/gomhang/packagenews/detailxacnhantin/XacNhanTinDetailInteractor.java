package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The XacNhanTinDetail interactor
 */
class XacNhanTinDetailInteractor extends Interactor<XacNhanTinDetailContract.Presenter>
        implements XacNhanTinDetailContract.Interactor {

    XacNhanTinDetailInteractor(XacNhanTinDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> commonCallback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, commonCallback);
    }

    @Override
    public void confirmOrderPostmanCollect(String orderPostmanID, String employeeID, String statusCode, String confirmReason, CommonCallback<SimpleResult> callback) {
        NetWorkController.confirmOrderPostmanCollect(orderPostmanID, employeeID, statusCode, confirmReason, callback);
    }
}
