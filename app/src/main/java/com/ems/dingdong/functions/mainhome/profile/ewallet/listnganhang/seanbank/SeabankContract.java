package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.DanhSachTaiKhoanRequest;
import com.ems.dingdong.model.response.DanhSachTaiKhoanRespone;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.model.thauchi.ThonTinSoTaiKhoanRespone;
import com.ems.dingdong.model.thauchi.YeuCauLienKetRequest;

import java.util.List;

import io.reactivex.Single;

public interface SeabankContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getDanhSachNganHang();

        Single<SimpleResult> getDanhSachTaiKhoan(DanhSachTaiKhoanRequest danhSachTaiKhoanRequest);

        Single<SimpleResult> yeuCauLienKet(YeuCauLienKetRequest request);

        Single<SimpleResult> smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request);

        Single<SimpleResult> vnpCallOTP(CallOTP callOTP);
    }

    interface View extends PresentView<Presenter> {

        void showDanhSach(List<DanhSachNganHangRepsone> list);

        void showThongTinTaiKhoan(SmartBankLink respone);

        void showDanhSachTaiKhoan(List<DanhSachTaiKhoanRespone> list);

        void showThanhcong();

        void showMain();

        void dissmisOTP();

        void showOTP();

    }

    interface Presenter extends IPresenter<View, Interactor> {

        void getDanhSachNganHang();

        void yeuCauLienKet(YeuCauLienKetRequest request);

        void smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request);

        void getDanhSachTaiKhoan(DanhSachTaiKhoanRequest danhSachTaiKhoanRequest);

        void moveToEWallet();

        void ddCallOTP(CallOTP request);
    }
}
