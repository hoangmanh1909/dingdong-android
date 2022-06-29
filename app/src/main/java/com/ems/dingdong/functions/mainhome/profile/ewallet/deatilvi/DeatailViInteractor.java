package com.ems.dingdong.functions.mainhome.profile.ewallet.deatilvi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.LinkHistory;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.thauchi.SmartBankConfirmCancelLinkRequest;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class DeatailViInteractor extends Interactor<DeatailViContract.Presenter>
        implements DeatailViContract.Interactor {

    public DeatailViInteractor(DeatailViContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public Single<SimpleResult> ddHuyLienKet(SmartBankRequestCancelLinkRequest request) {
        return NetWorkController.huyLienKetVi(request);
    }

    @Override
    public Single<SimpleResult> ddXacnhanhuy(SmartBankConfirmCancelLinkRequest request) {
        return NetWorkController.SmartBankConfirmCancelLinkRequest(request);
    }

    @Override
    public Single<SimpleResult> ddTaiKhoanMacDinh(TaiKhoanMatDinh request) {
        return NetWorkController.ddTaiKhoanMacDinh(request);
    }

    @Override
    public Single<SimpleResult> getHistory(LinkHistory request) {
        return NetWorkController.getHistory(request);
    }

    @Override
    public Single<SimpleResult> SetDefaultPayment(LinkHistory request) {
        return NetWorkController.SetDefaultPayment(request);
    }

}
