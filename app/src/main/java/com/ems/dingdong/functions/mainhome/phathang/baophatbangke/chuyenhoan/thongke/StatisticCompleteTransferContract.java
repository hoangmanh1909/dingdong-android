package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.thongke;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.model.LadingRefundTotalRequest;
import com.ems.dingdong.model.LadingRefundTotalRespone;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;

import java.util.List;

import io.reactivex.Observable;

public interface StatisticCompleteTransferContract {

    interface Interactor extends IInteractor<Presenter> {
        Observable<SimpleResult> ddLadingRefundTotal(LadingRefundTotalRequest ladingRefundTotalRequest);

        Observable<SimpleResult> ddLadingRefundDetail(LadingRefundTotalRequest ladingRefundTotalRequest);
    }

    interface View extends PresentView<Presenter> {
        void showThanhCong(List<LadingRefundTotalRespone> ls);

        void showDetail(List<LadingRefundDetailRespone> ls);

        void showError(String mess);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void ddLadingRefundTotal(LadingRefundTotalRequest ladingRefundTotalRequest);

        void ddLadingRefundDetail(LadingRefundTotalRequest ladingRefundTotalRequest);
    }

}
