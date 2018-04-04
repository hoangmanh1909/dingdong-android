package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.XacNhanTinResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The XacNhanTin interactor
 */
class XacNhanTinInteractor extends Interactor<XacNhanTinContract.Presenter>
        implements XacNhanTinContract.Interactor {

    XacNhanTinInteractor(XacNhanTinContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<XacNhanTinResult> callback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }
}
