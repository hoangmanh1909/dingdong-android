package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.network.NetWorkController;

import java.util.List;

public class TimDuongDiInteractor extends Interactor<TimDuongDiContract.Presenter>
        implements TimDuongDiContract.Interactor {
    public TimDuongDiInteractor(TimDuongDiContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getPoint(List<RouteRequest> request, CommonCallback<XacMinhDiaChiResult> callback) {
        NetWorkController.vietmapRoute(request, callback);
    }
}
