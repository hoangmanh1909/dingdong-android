package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list.xacnhan;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRequest;
import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRespone;
import com.ems.dingdong.model.DeliveryRefundRequest;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.model.RefundRequest;
import com.ems.dingdong.model.SimpleResult;

import io.reactivex.Observable;

public interface CompleteTransferContract {

    interface Interactor extends IInteractor<Presenter> {
        Observable<SimpleResult> ddUpdateLadingRefundDetail(DeliveryRefundRequest ladingRefundTotalRequest);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        DLVDeliveryUnSuccessRefundRespone getData();
        String  getTrangThai();

        void ddUpdateLadingRefundDetail(DeliveryRefundRequest ladingRefundTotalRequest);
    }

}
