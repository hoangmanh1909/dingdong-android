package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ReceiverVpostcodeMode;
import com.ems.dingdong.model.SenderVpostcodeMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.network.NetWorkController;

import java.util.List;

import io.reactivex.Single;

public class TimDuongDiInteractor extends Interactor<TimDuongDiContract.Presenter>
        implements TimDuongDiContract.Interactor {
    public TimDuongDiInteractor(TimDuongDiContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getPoint(List<String> request, CommonCallback<XacMinhDiaChiResult> callback) {
        NetWorkController.vietmapRoute(request, callback);
    }

    @Override
    public Single<SimpleResult> saveToaDoGom(List<SenderVpostcodeMode> request) {
        return NetWorkController.saveToaDoGom(request);
    }

    @Override
    public Single<SimpleResult> saveToaDoPhat(List<ReceiverVpostcodeMode> request) {
        return NetWorkController.saveToaDoPhat(request);
    }
}
