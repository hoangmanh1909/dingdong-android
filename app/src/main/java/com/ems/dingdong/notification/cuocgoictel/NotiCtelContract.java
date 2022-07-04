package com.ems.dingdong.notification.cuocgoictel;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.NotiCtelModel;

import io.reactivex.Single;
import retrofit2.Call;

/**
 * The Notification Contract
 */
interface NotiCtelContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getListTicket(String ticketMode);

        Single<SimpleResult> getHistoryCall(HistoryRequest historyRequest);

        Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber,
                                                 String callForwardType, String hotlineNumber,
                                                 String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showInfo(NotiCtelModel detailNotifyMode);

        void showInfoError(String text);

        void showCallError(String message);

        void showCallSuccess(String phone);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        String setCodeTicket();


        void getDetail(String ticket);

        void getHistoryCall(HistoryRequest request);

        void callForward(String phone, String parcelCode);

        void showTraCuu(String parcelCode);
    }
}



