package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.ReceiverVpostcodeMode;
import com.ems.dingdong.model.SenderVpostcodeMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;

import java.util.List;

import io.reactivex.Single;

public interface TimDuongDiContract {
    interface Interactor extends IInteractor<Presenter> {
        void getPoint(List<String> request, CommonCallback<XacMinhDiaChiResult> callback);

        Single<XacMinhDiaChiResult> vietmapTravelSalesmanProblem(TravelSales request);

        Single<SimpleResult> saveToaDoGom(List<SenderVpostcodeMode> request);

        Single<SimpleResult> saveToaDoPhat(List<ReceiverVpostcodeMode> request);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(Object object);

        void showError(String mes);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        AddressListModel getAddressListModel();

        List<VpostcodeModel> getListVpostcodeModell();

        void getPoint(List<String> request);

        void vietmapTravelSalesmanProblem(TravelSales request);

        TravelSales getApiTravel();

        int getType();

        int getTypeBack();


        void saveToaDoGom(List<SenderVpostcodeMode> request);

        void saveToaDoPhat(List<ReceiverVpostcodeMode> request);
    }
}
