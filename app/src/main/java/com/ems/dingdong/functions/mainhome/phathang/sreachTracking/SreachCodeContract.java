package com.ems.dingdong.functions.mainhome.phathang.sreachTracking;

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

import io.reactivex.Single;
import retrofit2.Call;

public class SreachCodeContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> findLocation(String ladingCode, String poCode);

        Single<SimpleResult> getHistoryCall(HistoryRequest historyRequest);

        Single<SimpleResult> ddCall(CallLiveMode callLiveMode);

        Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber,
                                                 String callForwardType, String hotlineNumber,
                                                 String ladingCode, String PostmanId, String POcode, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showFindLocationSuccess(CommonObject commonObject);

        void showLog(List<HistoryRespone> list);

        void showCallLive(String phone);

        void showCallError(String message);

        void showCallSuccess(String phone);

        void showEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void findLocation(String code);

        void getHistoryCall(HistoryRequest request);

        void ddCall(CallLiveMode r);

        void callForward(String phone, String parcelCode);

        String getCode();

        void showBarcode(BarCodeCallback barCodeCallback);
    }
}
