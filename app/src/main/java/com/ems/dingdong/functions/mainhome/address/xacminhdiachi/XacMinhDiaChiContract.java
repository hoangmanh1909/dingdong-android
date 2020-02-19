package com.ems.dingdong.functions.mainhome.address.xacminhdiachi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.XacMinhDiaChiResult;

import java.util.ArrayList;

public interface XacMinhDiaChiContract {
    interface Interactor extends IInteractor<Presenter> {
        void  getLocationAddress(double longitude, double latitude, CommonCallback<XacMinhDiaChiResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(Object xacMinhDiaChiResult);
        void showError(String mes);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getLocationAddress(double longitude,double latitude);
        void showAddressList(Object object);
    }
}
