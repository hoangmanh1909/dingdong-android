package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.thongke;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.LadingRefundTotalRequest;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.api.ApiService;

import io.reactivex.Observable;

public class StatisticCompleteTransferInteractor extends Interactor<StatisticCompleteTransferContract.Presenter>
        implements StatisticCompleteTransferContract.Interactor {

    public StatisticCompleteTransferInteractor(StatisticCompleteTransferContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Observable<SimpleResult> ddLadingRefundTotal(LadingRefundTotalRequest ladingRefundTotalRequest) {
        return ApiService.ddLadingRefundTotal(ladingRefundTotalRequest);
    }

    @Override
    public Observable<SimpleResult> ddLadingRefundDetail(LadingRefundTotalRequest ladingRefundTotalRequest) {
        return ApiService.ddLadingRefundDetail(ladingRefundTotalRequest);
    }
}
