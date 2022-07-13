package com.ems.dingdong.functions.mainhome.gomhang.gomnhieu;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import java.util.List;

import io.reactivex.Single;

/**
 * The CommonObject interactor
 */
class ListHoanTatNhieuTinInteractor extends Interactor<ListHoanTatNhieuTinContract.Presenter>
        implements ListHoanTatNhieuTinContract.Interactor {

    ListHoanTatNhieuTinInteractor(ListHoanTatNhieuTinContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchAllOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.searchAllOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }

    @Override
    public void getReasonsHoanTat(CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.getReasonsHoanTat(commonCallback);
    }

    @Override
    public void collectAllOrderPostman(List<HoanTatTinRequest> list, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.collectAllOrderPostman(list,callback);
    }

    @Override
    public Single<DecodeDiaChiResult> vietmapSearchDecode(String Decode) {
        return NetWorkController.vietmapSearchDecode(Decode);
    }

}
