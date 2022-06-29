package com.ems.dingdong.functions.mainhome.profile.ewallet.deatilvi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.LinkHistory;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.SmartBankConfirmCancelLinkRequest;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;

import java.util.List;

import io.reactivex.Single;

public interface DeatailViContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> ddHuyLienKet(SmartBankRequestCancelLinkRequest request);

        Single<SimpleResult> ddXacnhanhuy(SmartBankConfirmCancelLinkRequest request);

        Single<SimpleResult> ddTaiKhoanMacDinh(TaiKhoanMatDinh request);

        Single<SimpleResult> getHistory(LinkHistory request);

        Single<SimpleResult> SetDefaultPayment(LinkHistory request);
    }

    interface View extends PresentView<Presenter> {

        void showOTP();

        void dissOTP();

        void capNhatMacDinh();

        void showHistory(List<DeatailMode> list);

        void showErrorHistory(String mess);

        void showError(String mess);

        void capnhat(String mess);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void ddHuyLienKet(SmartBankRequestCancelLinkRequest request);


        List<SmartBankLink> getList();

        SmartBankLink getSmartBankLink();

        void ddTaiKhoanMacDinh(TaiKhoanMatDinh request);

        void getHistory(LinkHistory request);

        void SetDefaultPayment(LinkHistory request);


    }
}
