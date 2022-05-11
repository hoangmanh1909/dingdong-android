package com.ems.dingdong.functions.mainhome.phathang.routemanager.route.detail;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ChangeRouteResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

public class DetailRouteChangeInteractor extends Interactor<DetailRouteChangeConstract.Presenter> implements DetailRouteChangeConstract.Interactor {

    public DetailRouteChangeInteractor(DetailRouteChangeConstract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getChangeRouteDetail(String ladingCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.getRouteLadingDetail(ladingCode, callback);
    }
}
