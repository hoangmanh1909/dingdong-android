package com.ems.dingdong.functions.mainhome.phathang.thongke.sml;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.StatisticSMLDeliveryFailRequest;
import com.ems.dingdong.model.response.StatisticSMLDeliveryFailResponse;

import java.util.List;

import io.reactivex.Single;


public interface SmartlockStatisticContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> statisticSMLDeliveryFail(StatisticSMLDeliveryFailRequest req);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(List<StatisticSMLDeliveryFailResponse> list);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getData(int fromDate,int toDate);
    }
}
