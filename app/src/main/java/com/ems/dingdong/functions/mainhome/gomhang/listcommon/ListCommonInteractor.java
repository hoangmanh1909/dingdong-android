package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import java.util.ArrayList;

import io.reactivex.Single;

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
        NetWorkControllerGateWay.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String route, String order, CommonCallback<CommonObjectListResult> callback) {
        // NetWorkController.searchDeliveryPostman(postmanID, fromDate, route, order,callback);
    }

    @Override
    public void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.confirmAllOrderPostman(request, callback);
    }

    @Override
    public Single<SimpleResult> ddGetDichVuMpit() {
        return NetWorkControllerGateWay.ddGetDichVuMpit();
    }

    @Override
    public Single<SimpleResult> ddQueuChat(RequestQueuChat request) {
        return NetWorkControllerGateWay.ddQueuChat(request);
    }
}
