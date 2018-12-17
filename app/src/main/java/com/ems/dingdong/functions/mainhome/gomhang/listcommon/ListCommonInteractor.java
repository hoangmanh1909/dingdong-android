package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The CommonObject interactor
 */
class ListCommonInteractor extends Interactor<ListCommonContract.Presenter>
        implements ListCommonContract.Interactor {

    ListCommonInteractor(ListCommonContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> callback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String route, String order, CommonCallback<CommonObjectListResult> callback) {
       // NetWorkController.searchDeliveryPostman(postmanID, fromDate, route, order,callback);
    }
}
