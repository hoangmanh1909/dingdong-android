package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.RouteRequest;

import java.util.List;

public class TimDuongDiContract {
    interface Interactor extends IInteractor<Presenter> {
        void getPoint(List<RouteRequest> request, CommonCallback<XacMinhDiaChiResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(Object object);
        void showError(String mes);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        AddressListModel getAddressListModel();
        void getPoint(List<RouteRequest> request);
    }
}
