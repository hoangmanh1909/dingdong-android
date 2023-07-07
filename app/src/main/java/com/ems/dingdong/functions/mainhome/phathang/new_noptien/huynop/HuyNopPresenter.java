package com.ems.dingdong.functions.mainhome.phathang.new_noptien.huynop;

import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.LadingCancelPaymentInfo;
import com.ems.dingdong.model.request.PaymentCancelRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HuyNopPresenter extends Presenter<HuyNopContract.View, HuyNopContract.Interactor>
        implements HuyNopContract.Presenter {

    public HuyNopPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public HuyNopContract.Interactor onCreateInteractor() {
        return new HuyNopInteractor(this);
    }

    @Override
    public HuyNopContract.View onCreateView() {
        return HuyNopFragment.getInstance();
    }

    @Override
    public void getHistoryPayment(DataRequestPayment dataRequestPayment, int type) {
        mView.showProgress();
        mInteractor.getHistoryPayment(dataRequestPayment)
                .subscribeOn(Schedulers.io())
                .delay(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SimpleResult simpleResult) {
                        if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                            EWalletDataResponse[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), EWalletDataResponse[].class);
                            List<EWalletDataResponse> list1 = Arrays.asList(list);
                            if (list1.size() == 0)
                                mView.showConfirmError("Không có dữ liệu hủy nộp tiền");
                            else mView.showListSuccess(list1);
                            mView.hideProgress();
                        } else {
                            mView.hideProgress();
                            mView.showConfirmError(simpleResult.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void showLinkWalletFragment() {
        new EWalletPresenter(mContainerView).pushView();
    }


    @Override
    public void cancelPayment(List<EWalletDataResponse> list, int type, String lydo, String userId, String poCode, String postmanTel, String routeInfo) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
        List<LadingCancelPaymentInfo> cancelRequests = new ArrayList<>();
        for (EWalletDataResponse item : list) {
            LadingCancelPaymentInfo info = new LadingCancelPaymentInfo();
            info.setLadingCode(item.getLadingCode());
            info.setRetRefNumber(item.getRetRefNumber());
            info.setAmount(item.getCodAmount());
            info.setFee(item.getFee());
            info.setEWalletTransId(item.getEWalletTransId());
            info.setFeeType(Integer.parseInt(item.getFeeType()));
            info.setCashinChannel(item.getCashinChannel());
            cancelRequests.add(info);
        }
        PaymentCancelRequestModel paymentCancelRequestModel = new PaymentCancelRequestModel();
        paymentCancelRequestModel.setCancelBy(userId);
        paymentCancelRequestModel.setReasonType(type);
        paymentCancelRequestModel.setReasonText(lydo);
        paymentCancelRequestModel.setPoCode(poCode);
        paymentCancelRequestModel.setPostmanCode(postmanTel);
        paymentCancelRequestModel.setLadingPaymentInfoList(cancelRequests);
        paymentCancelRequestModel.setRouteId(routeInfo);
        paymentCancelRequestModel.setRetRefNumber(list.get(0).getRetRefNumber());
        paymentCancelRequestModel.setEWalletTransId(list.get(0).getEWalletTransId());
        paymentCancelRequestModel.setServiceCode(list.get(0).getServiceCode());
        String dataJson = NetWorkController.getGson().toJson(paymentCancelRequestModel);
        dataRequestPayment.setData(dataJson);
        dataRequestPayment.setCode("EWL003");
        mView.showProgress();

        mInteractor.cancelPayment(dataRequestPayment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.showToast(simpleResult.getMessage());
                    } else {
                        mView.showToast(simpleResult.getMessage());
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showConfirmError(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

}
