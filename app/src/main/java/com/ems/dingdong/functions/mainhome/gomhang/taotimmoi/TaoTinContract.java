package com.ems.dingdong.functions.mainhome.gomhang.taotimmoi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.CreateOrderRequest;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticDetailCollect;
import com.ems.dingdong.model.TaoTinReepone;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.model.request.PUGetBusinessProfileRequest;

import java.util.List;

import io.reactivex.Single;

/**
 * The CommonObject Contract
 */
interface TaoTinContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getTinhThanhPho(BaseRequest request);

        Single<SimpleResult> getQuanHuyen(BaseRequest request);

        Single<SimpleResult> getXaPhuong(BaseRequest request);

        Single<SimpleResult> search(PUGetBusinessProfileRequest request);

        Single<SimpleResult> searchdiachi(PUGetBusinessProfileRequest request);

        Single<SimpleResult> themTin(CreateOrderRequest createOrderRequest);

    }

    interface View extends PresentView<Presenter> {
        void showTinhThanhPho(List<ProvinceModels> list);

        void showQuanHuyen(List<DistrictModels> list);

        void showXaPhuong(List<WardModels> list);

        void showListKH(List<TaoTinReepone> list);

        void showDetail(TaoTinReepone taoTinReepone);

        void showSuccess(String mess);

    }

    interface Presenter extends IPresenter<View, Interactor> {

        void getTinhThanhPho();

        void getQuanHuyen(int id);

        void getXaPhuong(int id);

        void search(PUGetBusinessProfileRequest request);

        void searchDiachi(PUGetBusinessProfileRequest request);

        void themTinPresenter (CreateOrderRequest createOrderRequest);
    }
}



