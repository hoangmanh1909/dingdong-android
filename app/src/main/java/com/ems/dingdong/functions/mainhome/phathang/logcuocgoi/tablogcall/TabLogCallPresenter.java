package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall;

import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.LogCallPresenter;
import com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data.TabLogCallRespone;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TabLogCallPresenter extends Presenter<TabLogCallContract.View, TabLogCallContract.Interactor>
        implements TabLogCallContract.Presenter {

    public TabLogCallPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public TabLogCallContract.Interactor onCreateInteractor() {
        return new TabLogCallInteractor(this);
    }

    @Override
    public TabLogCallContract.View onCreateView() {
        return TabLogCallFragment.getInstance();
    }


    @Override
    public void showLogCall(int type, TabLogCallRespone tabLogCallRespone) {
        new LogCallPresenter(mContainerView).setType(type).setLogCall(tabLogCallRespone).pushView();
    }

    @Override
    public void getHistoryCall(HistoryRequest request) {
        mView.showProgress();
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        UserInfo userInfo = null;
        RouteInfo routeInfo = null;
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        request.setPOCode(poCode);
        request.setPostmanCode(userInfo.getUserName());
        request.setPODistrictCode(userInfo.getPODistrictCode());
        request.setPOProvinceCode(userInfo.getPOProvinceCode());
//        request.setPostmanTel(userInfo.getMobileNumber());
        request.setRouteId(routeInfo.getRouteId());
        request.setStatus(0);
        mInteractor.getHistoryCallToal(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        TabLogCallRespone[] j = NetWorkController.getGson().fromJson(simpleResult.getData(), TabLogCallRespone[].class);
                        List<TabLogCallRespone> l = Arrays.asList(j);
                        mView.showLog(l);
                    } else {
                        mView.showError(simpleResult.getMessage());
                    }
                    mView.hideProgress();
                });
    }
}
