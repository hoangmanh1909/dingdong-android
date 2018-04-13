package com.vinatti.dingdong.functions.mainhome.listcommon;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.XacNhanTinResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The XacNhanTin interactor
 */
class ListCommonInteractor extends Interactor<ListCommonContract.Presenter>
        implements ListCommonContract.Interactor {

    ListCommonInteractor(ListCommonContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<XacNhanTinResult> callback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String route, String order, CommonCallback<XacNhanTinResult> callback) {
        NetWorkController.searchDeliveryPostman(postmanID, fromDate, route, order,callback);
    }
}
