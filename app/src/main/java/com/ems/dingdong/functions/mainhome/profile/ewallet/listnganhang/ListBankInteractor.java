package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class ListBankInteractor extends Interactor<ListBankContract.Presenter> implements ListBankContract.Interactor {


    public ListBankInteractor(ListBankContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public Single<SimpleResult> vnpCallOTP(CallOTP callOTP) {
        return NetWorkController.ddCallOTP(callOTP);
    }

    @Override
    public Single<SimpleResult> smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request) {
        return NetWorkController.smartBankConfirmLinkRequest(request);
    }
}
