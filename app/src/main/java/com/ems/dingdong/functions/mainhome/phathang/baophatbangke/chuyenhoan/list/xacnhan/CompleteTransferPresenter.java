package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list.xacnhan;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list.ListCompleteFragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list.ListCompletePresenter;
import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRequest;
import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRespone;
import com.ems.dingdong.model.DeliveryRefundRequest;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.CustomToast;
import com.ems.dingdong.utiles.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CompleteTransferPresenter extends Presenter<CompleteTransferContract.View, CompleteTransferContract.Interactor>
        implements CompleteTransferContract.Presenter {

    public CompleteTransferPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public CompleteTransferContract.Interactor onCreateInteractor() {
        return new CompleteTransferInteractor(this);
    }

    @Override
    public CompleteTransferContract.View onCreateView() {
        return CompleteTransferFragment.getInstance();
    }

    DLVDeliveryUnSuccessRefundRespone data;
    String mTrangThai;

    public CompleteTransferPresenter setData(DLVDeliveryUnSuccessRefundRespone data1) {
        data = data1;
        return this;
    }

    public CompleteTransferPresenter setTrangThai(String data1) {
        mTrangThai = data1;
        return this;
    }


    @Override
    public DLVDeliveryUnSuccessRefundRespone getData() {
        return data;
    }

    @Override
    public String getTrangThai() {
        return mTrangThai;
    }

    @Override
    public void ddUpdateLadingRefundDetail(DeliveryRefundRequest ladingRefundTotalRequest) {
        mView.showProgress();
        mInteractor.ddUpdateLadingRefundDetail(ladingRefundTotalRequest)
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
                                CustomToast.makeText(getViewContext(), (int) CustomToast.LONG, simpleResult.getMessage(), Constants.ERROR).show();
                                back();
                                back();
//                                getViewContext().sendBroadcast(new Intent(ListCompleteFragment.ACTION_UPDATE_VIEW));
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
