package com.ems.dingdong.functions.mainhome.location;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;


/**
 * The Location Contract
 */
interface LocationContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> findLocation(String ladingCode, String poCode);

        Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber,
                                                 String callForwardType, String hotlineNumber,
                                                 String ladingCode, String PostmanId, String POcode, CommonCallback<SimpleResult> callback);

        Single<SimpleResult> ddCall(CallLiveMode callLiveMode);

        Single<SimpleResult> getHistoryCall(HistoryRequest historyRequest);
    }

    interface View extends PresentView<Presenter> {
        void showFindLocationSuccess(CommonObject commonObject);

        void showEmpty();

        void showCallError(String message);

        void showCallSuccess(String phone);

        void showLog(List<HistoryRespone> l);

        void showCallLive(String phone);

        void showError();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getHistoryCall(HistoryRequest request);

        void showBarcode(BarCodeCallback barCodeCallback);

        void findLocation(String code);

        String getCode();

        void ddCall(CallLiveMode r);

        void callForward(String phone, String parcelCode);
    }
}



