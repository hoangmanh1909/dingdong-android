package com.ems.dingdong.functions.mainhome.phathang.new_noptien.huynop;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.response.EWalletDataResponse;

import java.util.List;

import io.reactivex.Single;

public class HuyNopContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getHistoryPayment(DataRequestPayment dataRequestPayment);

        Single<SimpleResult> cancelPayment(DataRequestPayment dataRequestPayment);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(List<EWalletDataResponse> eWalletDataResponses);

        void showConfirmError(String message);


        void showToast(String mess);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getHistoryPayment(DataRequestPayment dataRequestPayment, int type);

        void showLinkWalletFragment();

        void cancelPayment(List<EWalletDataResponse> list, int type, String lydo, String userId, String poCode, String postmanTel, String routeInfo);

        void showBarcode(BarCodeCallback barCodeCallback);
    }
}
