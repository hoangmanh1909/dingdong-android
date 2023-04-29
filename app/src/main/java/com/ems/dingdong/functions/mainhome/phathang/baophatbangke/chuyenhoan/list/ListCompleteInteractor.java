package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.model.LadingRefundTotalRequest;
import com.ems.dingdong.model.LadingRefundTotalRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.api.ApiService;

import io.reactivex.Observable;

public class ListCompleteInteractor extends Interactor<ListCompleteContract.Presenter>
        implements ListCompleteContract.Interactor {
    public ListCompleteInteractor(ListCompleteContract.Presenter presenter) {
        super(presenter);
    }



    @Override
    public Observable<SimpleResult> ddGetLadingRefundDetail(LadingRefundDetailRespone ladingRefundTotalRequest) {
        return ApiService.ddGetLadingRefundDetail(ladingRefundTotalRequest);
    }
}
