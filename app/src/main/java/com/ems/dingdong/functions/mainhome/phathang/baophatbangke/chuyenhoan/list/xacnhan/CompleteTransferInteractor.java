package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list.xacnhan;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.DeliveryRefundRequest;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.api.ApiService;

import io.reactivex.Observable;

public class CompleteTransferInteractor extends Interactor<CompleteTransferContract.Presenter>
        implements CompleteTransferContract.Interactor {

    public CompleteTransferInteractor(CompleteTransferContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Observable<SimpleResult> ddUpdateLadingRefundDetail(DeliveryRefundRequest ladingRefundTotalRequest) {
        return ApiService.ddUpdateLadingRefundDetail(ladingRefundTotalRequest);
    }
}
