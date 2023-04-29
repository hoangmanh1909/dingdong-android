package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.thongke;

import androidx.annotation.NonNull;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list.ListCompletePresenter;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.model.LadingRefundTotalRequest;
import com.ems.dingdong.model.LadingRefundTotalRespone;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StatisticCompleteTransferPresenter extends Presenter<StatisticCompleteTransferContract.View, StatisticCompleteTransferContract.Interactor>
        implements StatisticCompleteTransferContract.Presenter {

    public StatisticCompleteTransferPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public StatisticCompleteTransferContract.Interactor onCreateInteractor() {
        return new StatisticCompleteTransferInteractor(this);
    }

    @Override
    public StatisticCompleteTransferContract.View onCreateView() {
        return StatisticCompleteTransferFragment.getInstance();
    }

    @Override
    public void ddLadingRefundTotal(LadingRefundTotalRequest ladingRefundTotalRequest) {
        mView.showProgress();
        mInteractor.ddLadingRefundTotal(ladingRefundTotalRequest)
                .subscribeOn(Schedulers.io())
                .delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull SimpleResult simpleResult) {
                        if (simpleResult.getErrorCode() != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
                                LadingRefundTotalRespone[] searchMode = NetWorkController.getGson().fromJson(simpleResult.getData(), LadingRefundTotalRespone[].class);
                                List<LadingRefundTotalRespone> list1 = Arrays.asList(searchMode);
                                mView.showThanhCong(list1);
                                mView.hideProgress();
                            } else {
                                mView.hideProgress();
                                mView.showError(simpleResult.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        new ApiDisposable(e, getViewContext());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void ddLadingRefundDetail(LadingRefundTotalRequest ladingRefundTotalRequest) {
        mView.showProgress();
        mInteractor.ddLadingRefundDetail(ladingRefundTotalRequest)
                .subscribeOn(Schedulers.io())
                .delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull SimpleResult simpleResult) {
                        if (simpleResult.getErrorCode() != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
                                LadingRefundDetailRespone[] searchMode = NetWorkController.getGson().fromJson(simpleResult.getData(), LadingRefundDetailRespone[].class);
                                List<LadingRefundDetailRespone> list1 = Arrays.asList(searchMode);
                                String string = "";
                                if (ladingRefundTotalRequest.getIsRefund().equals("N"))
                                    string = "N";
                                else string = "Y";
                                for (LadingRefundDetailRespone item : list1)
                                    item.setTrangThai(string);
                                new ListCompletePresenter(mContainerView).setData(list1).setTitle("").addView();
                                mView.hideProgress();
                            } else {
                                mView.hideProgress();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        new ApiDisposable(e, getViewContext());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
