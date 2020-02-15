package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;

import java.util.List;

public class XacNhanBaoPhatInteractor extends Interactor<XacNhanBaoPhatContract.Presenter> implements XacNhanBaoPhatContract.Interactor {
    public XacNhanBaoPhatInteractor(XacNhanBaoPhatContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getReasons(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasons(commonCallback);
    }

    @Override
    public void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback) {
        NetWorkController.getSolutionByReasonCode(code, commonCallback);
    }

    @Override
    public void postImage(String path, CommonCallback<UploadSingleResult> callback) {
        NetWorkController.postImageSingle(path, callback);
    }

    @Override
    public void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> simpleResultCommonCallback) {
        NetWorkController.paymentDelivery(request, simpleResultCommonCallback);
    }

    @Override
    public void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.pushToPNSDelivery(request, callback);
    }

    @Override
    public void getRouteByPoCode(String poCode, CommonCallback<RouteInfoResult> callback) {
        NetWorkController.getDeliveryRoute(poCode, callback);
    }

    @Override
    public void getPostman(String poCode, int routeId, String routeType, CommonCallback<UserInfoResult> callback) {
        NetWorkController.getPostmanByRoute(poCode, routeId, routeType, callback);
    }

    @Override
    public void cancelDivided(List<DingDongCancelDividedRequest> request, CommonCallback<SimpleResult> callback) {
        NetWorkController.cancelDivided(request,callback);
    }
}
