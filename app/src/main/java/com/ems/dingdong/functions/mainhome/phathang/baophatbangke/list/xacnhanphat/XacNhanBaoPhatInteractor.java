package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.network.NetWorkController;

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
}
