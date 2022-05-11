package com.ems.dingdong.functions.mainhome.profile.chitiettaikhoan;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.thauchi.SmartBankConfirmCancelLinkRequest;
import com.ems.dingdong.model.thauchi.SmartBankInquiryBalanceRequest;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class ChiTietTaiKhoanInteractor extends Interactor<ChiTietTaiKhoanContract.Presenter> implements ChiTietTaiKhoanContract.Interactor {

    public ChiTietTaiKhoanInteractor(ChiTietTaiKhoanContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> ddHuyLienKet(SmartBankRequestCancelLinkRequest request) {
        return NetWorkController.smartBankRequestCancelLinkRequest(request);
    }

    @Override
    public Single<SimpleResult> ddXacnhanhuy(SmartBankConfirmCancelLinkRequest request) {
        return NetWorkController.SmartBankConfirmCancelLinkRequest(request);
    }

    @Override
    public Single<SimpleResult> ddTruyVanSodu(SmartBankInquiryBalanceRequest request) {
        return NetWorkController.ddTruyVanSodu(request);
    }

    @Override
    public Single<SimpleResult> vnpCallOTP(CallOTP callOTP) {
        return NetWorkController.ddCallOTP(callOTP);
    }

    @Override
    public Single<SimpleResult> ddSodu(SmartBankInquiryBalanceRequest callOTP) {
        return NetWorkController.ddTruyVanSodu(callOTP);
    }

    @Override
    public Single<SimpleResult> ddTaiKhoanMacDinh(TaiKhoanMatDinh request) {
        return NetWorkController.ddTaiKhoanMacDinh(request);
    }

    @Override
    public Single<SimpleResult> getSmartBankLink(String userName, String unitCode) {
        return NetWorkController.getSmartBankLink(userName,unitCode);
    }
}
