package com.ems.dingdong.functions.mainhome.gomhang.gomnhieu;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;

import java.util.List;

/**
 * The CommonObject interactor
 */
class ListHoanTatNhieuTinInteractor extends Interactor<ListHoanTatNhieuTinContract.Presenter>
        implements ListHoanTatNhieuTinContract.Interactor {

    ListHoanTatNhieuTinInteractor(ListHoanTatNhieuTinContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchAllOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> callback) {
        NetWorkController.searchAllOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }
    @Override
    public void getReasonsHoanTat(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasonsHoanTat(commonCallback);
    }

    @Override
    public void collectAllOrderPostman(List<HoanTatTinRequest> list, CommonCallback<SimpleResult> callback) {
        NetWorkController.collectAllOrderPostman(list,callback);
    }

}
