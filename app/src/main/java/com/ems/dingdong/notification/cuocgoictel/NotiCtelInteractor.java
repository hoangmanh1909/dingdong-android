package com.ems.dingdong.notification.cuocgoictel;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;

import io.reactivex.Single;
import retrofit2.Call;

/**
 * The Notification interactor
 */
class NotiCtelInteractor extends Interactor<NotiCtelContract.Presenter>
        implements NotiCtelContract.Interactor {

    NotiCtelInteractor(NotiCtelContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getListTicket(String ticketMode) {
        return NetWorkController.getDetailTicket(ticketMode);
    }

    @Override
    public Single<SimpleResult> getHistoryCall(HistoryRequest historyRequest) {
        return NetWorkController.getCallHistory(historyRequest);
    }

    @Override
    public Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback) {
        return NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POCode, callback);
    }

}
