package com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.otp;

import android.annotation.SuppressLint;

import com.core.base.viper.Interactor;
import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.eventbus.CustomNoptien;
import com.ems.dingdong.eventbus.CustomTab;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.NopTIenPresenter;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.NopTienContract;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OtpPresenter extends Presenter<OtpContract.View, OtpContract.Interactor>
        implements OtpContract.Presenter {

    public OtpPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public OtpContract.Interactor onCreateInteractor() {
        return new OtpInteractor(this);
    }

    @Override
    public OtpContract.View onCreateView() {
        return OtpFragment.getInstance();
    }

    List<LadingPaymentInfo> list;

    public OtpPresenter setList(List<LadingPaymentInfo> code) {
        this.list = code;
        return this;
    }

    String requestId;

    public OtpPresenter setRequestId(String code) {
        this.requestId = code;
        return this;
    }

    String retRefNumber;

    public OtpPresenter setRetRefNumber(String code) {
        this.retRefNumber = code;
        return this;
    }

    String poCode;

    public OtpPresenter setPoCode(String code) {
        this.poCode = code;
        return this;
    }

    String routeCode;

    public OtpPresenter setRouteCode(String code) {
        this.routeCode = code;
        return this;
    }

    String postmanCode;

    public OtpPresenter setPostmanCode(String code) {
        this.postmanCode = code;
        return this;
    }

    String mobileNumber;

    public OtpPresenter setMobileNumber(String code) {
        this.mobileNumber = code;
        return this;
    }

    String token;

    public OtpPresenter setToken(String code) {
        this.token = code;
        return this;
    }

    int type;

    public OtpPresenter setType(int code) {
        this.type = code;
        return this;
    }

    SmartBankLink bankcode;

    public OtpPresenter setBankcode(SmartBankLink code) {
        this.bankcode = code;
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public void confirmPayment(String otp) {
        PaymentConfirmModel model = new PaymentConfirmModel();
        model.setOtpCode(otp.toUpperCase());
        model.setPaymentToken(token);
        model.setTransId(requestId);
        model.setRetRefNumber(retRefNumber);
        model.setPoCode(poCode);
        model.setRouteCode(routeCode);
        model.setPostmanCode(postmanCode);
        model.setLadingPaymentInfoList(list);
        model.setPostmanTel(mobileNumber);
        model.setAccountType(type);
        model.setBankCode(bankcode.getBankCode());
        mView.showProgress();
        mInteractor.confirmPayment(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
//                        mView.showConfirmSuccess(simpleResult.getMessage());
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    } else if (simpleResult.getErrorCode().equals("101")) {
//                        mView.showConfirmError(simpleResult.getMessage());
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
//                        mView.showConfirmError(simpleResult.getMessage());
//                        mView.showThanhCong();
                    }
                    back();
                    EventBus.getDefault().postSticky(new CustomTab(simpleResult.getErrorCode(), simpleResult.getMessage()));
                    mView.hideProgress();
                }, throwable -> {
//                    mView.showThanhCong();
//                    mView.showConfirmError(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public SmartBankLink getSmartBankLink() {
        return bankcode;
    }
}
