package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list;

import androidx.annotation.NonNull;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list.xacnhan.CompleteTransferPresenter;
import com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.detailstatistic.DetailStatisticTicketPresenter;
import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRespone;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.model.LadingRefundTotalRequest;
import com.ems.dingdong.model.LadingRefundTotalRespone;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.ValidationRequest;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListCompletePresenter extends Presenter<ListCompleteContract.View, ListCompleteContract.Interactor>
        implements ListCompleteContract.Presenter {

    public ListCompletePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public ListCompleteContract.Interactor onCreateInteractor() {
        return new ListCompleteInteractor(this);
    }

    @Override
    public ListCompleteContract.View onCreateView() {
        return ListCompleteFragment.getInstance();
    }


    List<LadingRefundDetailRespone> data;
    String title;

    public ListCompletePresenter setData(List<LadingRefundDetailRespone> data1) {
        data = data1;
        return this;
    }

    public ListCompletePresenter setTitle(String data1) {
        title = data1;
        return this;
    }

    @Override
    public List<LadingRefundDetailRespone> getData() {
        return data;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void ddLadingRefundTotal(LadingRefundDetailRespone ladingRefundTotalRequest) {
        mView.showProgress();
        mInteractor.ddGetLadingRefundDetail(ladingRefundTotalRequest)
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
                                DLVDeliveryUnSuccessRefundRespone searchMode = NetWorkController.getGson().fromJson(simpleResult.getData(), DLVDeliveryUnSuccessRefundRespone.class);
                                new CompleteTransferPresenter(mContainerView).setData(searchMode).setTrangThai(ladingRefundTotalRequest.getTrangThai()).addView();
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
