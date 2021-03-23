package com.ems.dingdong.calls.calling;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.response.ResponseObject;
import com.ems.dingdong.network.NetWorkController;

public class CallingInteractor extends Interactor<CallingContract.Presenter> implements CallingContract.Interactor {
    public CallingInteractor(CallingContract.Presenter presenter) {
        super(presenter);
    }

//    @Override
//    public void createCallHistoryVHT(String code, String data, String signature, CommonCallback<ResponseObject> callback) {
////        NetWorkController.createCallHistoryVHT(code,data,signature,callback);
//    }
}
