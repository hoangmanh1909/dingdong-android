package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

class XacNhanDiaChiInteractor extends Interactor<XacNhanDiaChiContract.Presenter> implements XacNhanDiaChiContract.Interactor {

    XacNhanDiaChiInteractor(XacNhanDiaChiContract.Presenter presenter) {
        super(presenter);
    }

        @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }
//    @Override
//    public Single<SimpleResult> searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate,
//                                                          String toAssignDate) {
//        return NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate);
//    }

}
