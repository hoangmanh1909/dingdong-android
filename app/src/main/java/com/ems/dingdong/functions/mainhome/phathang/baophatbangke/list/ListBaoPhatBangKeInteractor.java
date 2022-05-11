package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.SMLRequest;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;
import retrofit2.Call;

/**
 * The CommonObject interactor
 */
class ListBaoPhatBangKeInteractor extends Interactor<ListBaoPhatBangKeContract.Presenter>
        implements ListBaoPhatBangKeContract.Interactor {

    ListBaoPhatBangKeInteractor(ListBaoPhatBangKeContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }

    @Override
    public Call<SimpleResult> searchDeliveryPostman(String postmanID, String fromDate, String toDate, String routeCode, Integer searchType, CommonCallback<SimpleResult> callback) {
        return NetWorkController.searchDeliveryPostman(postmanID, fromDate, toDate, routeCode, searchType, callback);
    }

    @Override
    public Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, CommonCallback<SimpleResult> callback) {
        return NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, callback);
    }

    @Override
    public Call<SimpleResult> updateMobile(String code,String type, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback) {
        return NetWorkController.updateMobile(code,type, phone, simpleResultCommonCallback);
    }

    @Override
    public Call<SimpleResult> updateMobileSender(String code, String type, String phoneSender, CommonCallback<SimpleResult> simpleResultCommonCallback) {
        return NetWorkController.updateMobile(code,type, phoneSender, simpleResultCommonCallback);
    }

    @Override
    public Single<SimpleResult> _phatSml(SMLRequest smlRequest) {
        return NetWorkController.phatSml(smlRequest);
    }

    @Override
    public Single<SimpleResult> _huySml(SMLRequest smlRequest) {
        return NetWorkController.huySml(smlRequest);
    }

}
