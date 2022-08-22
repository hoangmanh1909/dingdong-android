package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import java.util.List;

import io.reactivex.Single;

public class ChiTietHoanThanhTinTheoDiaChiInteractor extends Interactor<ChiTietHoanThanhTinTheoDiaChiContract.Presenter> implements ChiTietHoanThanhTinTheoDiaChiContract.Interactor {

    public ChiTietHoanThanhTinTheoDiaChiInteractor(ChiTietHoanThanhTinTheoDiaChiContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void postImage(String path, CommonCallback<UploadSingleResult> callback) {
        NetWorkController.postImageSingle(path, callback);
    }

    @Override
    public void getReasonUnSuccess(CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.getReasonsHoanTat(commonCallback);
    }

    @Override
    public void getReasonFailure(CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.getReasonsHoanTatMiss(commonCallback);
    }

    @Override
    public void collectAllOrderPostman(List<HoanTatTinRequest> list, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.collectAllOrderPostman(list, callback);
    }

    @Override
    public void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.collectOrderPostmanCollect(hoanTatTinRequest, callback);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POcode, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POcode, callback);
    }

    @Override
    public void updateMobile(String code, String type, String mobileNumber, CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.updateMobile(code, type, mobileNumber, commonCallback);
    }

    @Override
    public Single<DecodeDiaChiResult> vietmapSearchDecode(String Decode) {
        return NetWorkControllerGateWay.vietmapSearchDecode(Decode);
    }
}
