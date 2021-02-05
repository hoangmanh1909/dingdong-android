package com.ems.dingdong.functions.mainhome.phathang.noptien.huynop;

import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.LadingCancelPaymentInfo;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.request.PaymentCancelRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CancelPaymentPresenter extends Presenter<CancelPaymentContract.View, CancelPaymentContract.Interactor>
        implements CancelPaymentContract.Presenter {

    private int mPos;
    String id = "";
    String poCode = "";
    String postmanTel = "";
    private List<LadingPaymentInfo> ladingPaymentInfoList;
    private List<LadingCancelPaymentInfo> cancelRequests;
    private CancelPaymentContract.OnTabListener tabListener;
    public CancelPaymentPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public CancelPaymentContract.Interactor onCreateInteractor() {
        return new CancelPaymentInteractor(this);
    }

    @Override
    public CancelPaymentContract.View onCreateView() {
        return CancelPaymentFragment.getInstance();
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void showLinkWalletFragment() {
        new EWalletPresenter(mContainerView).pushView();
    }

    @Override
    public void cancelPayment(List<EWalletDataResponse> list,int type,String lydo) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
        cancelRequests = new ArrayList<>();
        cancelRequests.clear();
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!TextUtils.isEmpty(userJson)) {
            id = NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD();
        }
        if (!TextUtils.isEmpty(postOfficeJson)) {
            poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        }
        if (!TextUtils.isEmpty(userJson)) {
            postmanTel = NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber();
        }

        for (EWalletDataResponse item : list) {
            LadingCancelPaymentInfo info = new LadingCancelPaymentInfo();
            info.setLadingCode(item.getLadingCode());
            info.setRetRefNumber(item.getRetRefNumber());
            info.setAmount(item.getCodAmount());
            cancelRequests.add(info);
        }
        PaymentCancelRequestModel paymentCancelRequestModel = new PaymentCancelRequestModel();
        paymentCancelRequestModel.setCancelBy(id);
        paymentCancelRequestModel.setReasonType(type);
        paymentCancelRequestModel.setReasonText(lydo);
        paymentCancelRequestModel.setPoCode(poCode);
        paymentCancelRequestModel.setPostmanCode(postmanTel);
        paymentCancelRequestModel.setLadingPaymentInfoList(cancelRequests);
        String dataJson = NetWorkController.getGson().toJson(paymentCancelRequestModel);
        dataRequestPayment.setData(dataJson);
        dataRequestPayment.setCode("EWL003");
        mView.showProgress();

        mInteractor.cancelPayment(dataRequestPayment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.showConfirmSuccess(simpleResult.getMessage());
                    } else if (simpleResult != null) {
                        mView.showConfirmError(simpleResult.getMessage());
                    } else {
                        mView.showConfirmError("Lỗi xử lí hệ thống, vui lòng liên hệ ban quản trị.");
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showConfirmError(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public int getPositionTab() {
        return mPos;
    }

    @Override
    public void getHistoryPayment(DataRequestPayment dataRequestPayment,int type) {
        mView.showProgress();
        mInteractor.getHistoryPayment(dataRequestPayment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(walletDataHistoryResult -> {
                    if (walletDataHistoryResult != null && walletDataHistoryResult.getErrorCode().equals("00")) {
                        EWalletDataResponse[] list = NetWorkController.getGson().fromJson(walletDataHistoryResult.getListCodes(), EWalletDataResponse[].class);
                        List<EWalletDataResponse> list1 = Arrays.asList(list);
                        if (getPositionTab() == 1) {
                            for (EWalletDataResponse item : list1)
                                item.setGetPositionTab(getPositionTab());
                        } else if (getPositionTab() == 2) {
                            for (EWalletDataResponse item : list1)
                                item.setGetPositionTab(getPositionTab());
                        }
                        mView.showListSuccess(list1);
                        if (type == 1) {
                            if (list1.size() == 0) {
                                titleChanged(list1.size(), 1);
                                mView.showErrorToast("Không tìm thấy dữ liệu phù hợp");
                            }
                        }
                        mView.hideProgress();
                    }
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public void onCanceled() {
        tabListener.onCanceledDelivery();
    }

    @Override
    public void titleChanged(int quantity, int currentSetTab) {
        tabListener.onQuantityChange(quantity, currentSetTab);
    }

    @Override
    public int getCurrentTab() {
        return tabListener.getCurrentTab();
    }



    public CancelPaymentPresenter setTypeTab(int position) {
        mPos = position;
        return this;
    }

    public CancelPaymentPresenter setOnTabListener(CancelPaymentContract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }
}
