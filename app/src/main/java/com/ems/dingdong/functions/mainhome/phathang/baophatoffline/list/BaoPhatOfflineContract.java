package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * The BaoPhatThanhCong Contract
 */
interface BaoPhatOfflineContract {

    interface Interactor extends IInteractor<Presenter> {

        void searchParcelCodeDelivery(String parcelCode, String signature, CommonCallback<CommonObjectResult> callback);

        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback);

        /**
         * Deliver success.
         */
        Single<SimpleResult> pushToPNSDelivery(PushToPnsRequest request);

        /**
         * Deliver not success.
         */
        Single<SimpleResult> paymentDelivery(PaymentDeviveryRequest request);

        Observable<UploadSingleResult> postImageObservable(String path);
    }

    interface View extends PresentView<Presenter> {

        void showError(String message);

        void showErrorFromRealm();

        void showSuccess(String code, String parcelCode);

        void showListFromSearchDialog(List<CommonObject> list);

    }

    interface Presenter extends IPresenter<View, Interactor> {

        /**
         * get local record.
         *
         * @param fromDate from created date.
         * @param toDate   to created date
         */
        void getLocalRecord(String fromDate, String toDate);

        /**
         * Remove item from local storage.
         *
         * @param parcelCode lading code.
         */
        void removeOfflineItem(String parcelCode);

        /**
         * Offline deliver.
         *
         * @param commonObjects list chosen.
         */
        void offlineDeliver(List<CommonObject> commonObjects, double deliveryLat, double deliveryLon, double receiverLat, double receiverLon);

        List<CommonObject> getOfflineRecord(Date from, Date to);

    }
}



