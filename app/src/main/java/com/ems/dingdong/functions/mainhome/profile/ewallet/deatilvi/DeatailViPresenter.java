package com.ems.dingdong.functions.mainhome.profile.ewallet.deatilvi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.loadhinhanh.DataModel;
import com.ems.dingdong.functions.mainhome.profile.chitiettaikhoan.ChiTietTaiKhoanPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankFragment;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.LinkHistory;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DeatailViPresenter extends Presenter<DeatailViContract.View, DeatailViContract.Interactor>
        implements DeatailViContract.Presenter {

    public DeatailViPresenter(ContainerView containerView) {
        super(containerView);
    }

    String x;
    String c;
    String BankCode;
    String PIDNumber;
    String PIDType;
    String POCode;
    String PaymentToken;
    String PostmanCode;
    int account;
    boolean defaut;
    List<SmartBankLink> xa;
    SmartBankLink smartBankLink;

    @Override
    public void start() {

    }

    @Override
    public DeatailViContract.Interactor onCreateInteractor() {
        return new DeatailViInteractor(this);
    }

    @Override
    public DeatailViContract.View onCreateView() {
        return DeatailViFragment.getInstance();
    }


    public DeatailViPresenter setSmartBankLink(String c) {
        x = c;
        return this;
    }

    public DeatailViPresenter setTrangThai(String c) {
        this.c = c;
        return this;
    }

    public DeatailViPresenter setBankCode(String c) {
        this.BankCode = c;
        return this;
    }

    public DeatailViPresenter setPIDNumber(String c) {
        this.PIDNumber = c;
        return this;
    }

    public DeatailViPresenter setPIDType(String c) {
        this.PIDType = c;
        return this;
    }

    public DeatailViPresenter setPOCode(String c) {
        this.POCode = c;
        return this;
    }

    public DeatailViPresenter setAccount(int c) {
        this.account = c;
        return this;
    }

    public DeatailViPresenter setPaymentToken(String c) {
        this.PaymentToken = c;
        return this;
    }

    public DeatailViPresenter setPostmancode(String c) {
        this.PostmanCode = c;
        return this;
    }

    public DeatailViPresenter setDefaut(boolean c) {
        this.defaut = c;
        return this;
    }

    public DeatailViPresenter setList(List<SmartBankLink> k) {
        xa = new ArrayList<>();
        this.xa.addAll(k);
        return this;
    }

    public DeatailViPresenter setSmartBankLink(SmartBankLink k) {
        this.smartBankLink = k;
        return this;
    }


    @Override
    public SmartBankLink getSmartBankLink() {
        return smartBankLink;
    }


    @Override
    public void ddTaiKhoanMacDinh(TaiKhoanMatDinh request) {
        mView.showProgress();
        mInteractor.ddTaiKhoanMacDinh(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {

                            mView.capNhatMacDinh();
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());

                        mView.hideProgress();
                    }
                });
    }

    class NameComparator implements Comparator<DeatailMode> {
        public int compare(DeatailMode s1, DeatailMode s2) {
            return s1.getAmndDate().compareTo(s2.getAmndDate());
        }
    }

    @Override
    public void getHistory(LinkHistory request) {
        mView.showProgress();
        mInteractor.getHistory(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {

                            DeatailMode[] modes = NetWorkController.getGson().fromJson(simpleResult.getData(), DeatailMode[].class);
                            List<DeatailMode> ls = Arrays.asList(modes);
//                            Collections.sort(ls, new NameComparator());
                            mView.showHistory(ls);
                        } else {
                            mView.showErrorHistory(simpleResult.getMessage());
                        }
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void SetDefaultPayment(LinkHistory request) {
        mView.showProgress();
        mInteractor.SetDefaultPayment(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            if (request.getBankCode() == null) {
                                smartBankLink.setIsDefaultPayment(false);
                                setSmartBankLink(smartBankLink);
                            } else {
                                smartBankLink.setIsDefaultPayment(true);
                                setSmartBankLink(smartBankLink);
                            }
                            ;
                            mView.capnhat(simpleResult.getMessage());
                        } else {
                            mView.showError(simpleResult.getMessage());
                        }
                        mView.hideProgress();
                    }
                });
    }


    @Override
    public List<SmartBankLink> getList() {
        return xa;
    }

    @Override
    public void ddHuyLienKet(SmartBankRequestCancelLinkRequest request) {
        mView.showProgress();
        mInteractor.ddHuyLienKet(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            back();
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }
}
