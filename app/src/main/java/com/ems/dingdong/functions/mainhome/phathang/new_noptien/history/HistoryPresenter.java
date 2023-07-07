package com.ems.dingdong.functions.mainhome.phathang.new_noptien.history;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment.HistoryPaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryPresenter extends Presenter<HistoryContract.View, HistoryContract.Interactor>
        implements HistoryContract.Presenter {

    public HistoryPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public HistoryContract.Interactor onCreateInteractor() {
        return new HistoryInteractor(this);
    }

    @Override
    public HistoryContract.View onCreateView() {
        return HistoryFragment.getInstance();
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
                                mView.showConfirmError("Không có dữ liệu lịch sử nộp tiền");
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
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }
}
