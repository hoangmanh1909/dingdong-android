package com.ems.dingdong.functions.mainhome.profile.chitiettaikhoan;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.thauchi.SmartBankConfirmCancelLinkRequest;
import com.ems.dingdong.model.thauchi.SmartBankInquiryBalanceRequest;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class ChiTietTaiKhoanInteractor extends Interactor<ChiTietTaiKhoanContract.Presenter> implements ChiTietTaiKhoanContract.Interactor {

    public ChiTietTaiKhoanInteractor(ChiTietTaiKhoanContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> ddHuyLienKet(SmartBankRequestCancelLinkRequest request) {
        return NetWorkControllerGateWay.smartBankRequestCancelLinkRequest(request);
    }

    @Override
    public Single<SimpleResult> ddXacnhanhuy(SmartBankConfirmCancelLinkRequest request) {
        return NetWorkControllerGateWay.SmartBankConfirmCancelLinkRequest(request);
    }

    @Override
    public Single<SimpleResult> ddTruyVanSodu(SmartBankInquiryBalanceRequest request) {
        return NetWorkControllerGateWay.ddTruyVanSodu(request);
    }

    @Override
    public Single<SimpleResult> vnpCallOTP(CallOTP callOTP) {
        return NetWorkControllerGateWay.ddCallOTP(callOTP);
    }

    @Override
    public Single<SimpleResult> ddSodu(SmartBankInquiryBalanceRequest callOTP) {
        return NetWorkControllerGateWay.ddTruyVanSodu(callOTP);
    }

    @Override
    public Single<SimpleResult> ddTaiKhoanMacDinh(TaiKhoanMatDinh request) {
        return NetWorkControllerGateWay.ddTaiKhoanMacDinh(request);
    }
}
