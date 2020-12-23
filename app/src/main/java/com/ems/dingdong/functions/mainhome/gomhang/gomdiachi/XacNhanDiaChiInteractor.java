package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.network.NetWorkController;

import java.util.ArrayList;

/**
 * The CommonObject interactor
 */
class XacNhanDiaChiInteractor extends Interactor<XacNhanDiaChiContract.Presenter>
        implements XacNhanDiaChiContract.Interactor {

    XacNhanDiaChiInteractor(XacNhanDiaChiContract.Presenter presenter) {
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

    @Override
    public void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<ConfirmAllOrderPostmanResult> callback) {
        NetWorkController.confirmAllOrderPostman(request, callback);
    }

    @Override
    public void confirmParcelCode(ArrayList<ConfirmOrderPostman> request, CommonCallback<ConfirmAllOrderPostmanResult> callback) {
        NetWorkController.confirmAllOrderPostman(request, callback);
    }


}
