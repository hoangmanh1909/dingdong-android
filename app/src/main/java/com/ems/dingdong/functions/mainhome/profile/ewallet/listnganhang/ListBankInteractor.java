package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class ListBankInteractor extends Interactor<ListBankContract.Presenter> implements ListBankContract.Interactor {


    public ListBankInteractor(ListBankContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public Single<SimpleResult> vnpCallOTP(CallOTP callOTP) {
        return NetWorkControllerGateWay.ddCallOTP(callOTP);
    }

    @Override
    public Single<SimpleResult> smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request) {
        return NetWorkControllerGateWay.smartBankConfirmLinkRequest(request);
    }

    @Override
    public Single<SimpleResult> getDDsmartBankConfirmLinkRequest(BaseRequestModel request) {
        return NetWorkControllerGateWay.getDDsmartBankConfirmLinkRequest(request);
    }

    @Override
    public Single<SimpleResult> getDanhSachNganHang() {
        return NetWorkControllerGateWay.getDanhSachNganHang();
    }
}
