package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank;

import com.core.base.viper.Interactor;
import com.core.base.viper.interfaces.IInteractor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.DanhSachTaiKhoanRequest;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.model.thauchi.YeuCauLienKetRequest;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class SeabankInteractor extends Interactor<SeabankContract.Presenter> implements SeabankContract.Interactor {

    public SeabankInteractor(SeabankContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getDanhSachNganHang() {
        return NetWorkController.getDanhSachNganHang();
    }

    @Override
    public Single<SimpleResult> getDanhSachTaiKhoan(DanhSachTaiKhoanRequest danhSachTaiKhoanRequest) {
        return NetWorkController.getDanhSachTaiKhoan(danhSachTaiKhoanRequest);
    }

    @Override
    public Single<SimpleResult> yeuCauLienKet(YeuCauLienKetRequest request) {
        return NetWorkController.yeuCauLienKet(request);
    }

    @Override
    public Single<SimpleResult> smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request) {
        return NetWorkController.smartBankConfirmLinkRequest(request);
    }

    @Override
    public Single<SimpleResult> vnpCallOTP(CallOTP callOTP) {
        return NetWorkController.ddCallOTP(callOTP);
    }


}
