package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

import java.util.ArrayList;

/**
 * The CommonObject interactor
 */
class ListCommonInteractor extends Interactor<ListCommonContract.Presenter>
        implements ListCommonContract.Interactor {

    ListCommonInteractor(ListCommonContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String route, String order, CommonCallback<CommonObjectListResult> callback) {
        // NetWorkController.searchDeliveryPostman(postmanID, fromDate, route, order,callback);
    }

    @Override
    public void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<SimpleResult> callback) {
        NetWorkController.confirmAllOrderPostman(request, callback);
    }
}
