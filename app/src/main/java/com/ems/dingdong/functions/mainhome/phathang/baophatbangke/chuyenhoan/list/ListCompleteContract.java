package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.model.LadingRefundTotalRequest;
import com.ems.dingdong.model.LadingRefundTotalRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.LoginRequest;

import java.util.List;

import io.reactivex.Observable;

public interface ListCompleteContract {
    interface Interactor extends IInteractor<Presenter> {
        Observable<SimpleResult> ddGetLadingRefundDetail(LadingRefundDetailRespone ladingRefundTotalRequest);

    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {

        List<LadingRefundDetailRespone> getData();

        String getTitle();

        void ddLadingRefundTotal(LadingRefundDetailRespone ladingRefundTotalRequest);
    }
}
