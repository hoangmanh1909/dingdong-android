package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat.listdanhsachbaophat;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VerifyAddress;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.XacMinhRespone;
import com.ems.dingdong.model.request.SMLRequest;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.model.response.VerifyAddressRespone;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;

public interface ListBaoPhatContract {
    interface Interactor extends IInteractor<Presenter> {

        Single<SimpleResult>  searchDeliveryPostman(String postmanID,
                                                            String fromDate,
                                                            String toDate,
                                                            String routeCode,
                                                            Integer searchType);

        /**
         * Call to service center to connect to calleenumber.
         *
         * @param callerNumber  caller number.
         * @param calleeNumber  callee number.
         * @param hotlineNumber hotline.
         * @param ladingCode    lading code.
         */
        Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber,
                                                 String callForwardType, String hotlineNumber,
                                                 String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback);

        /**
         * Update mobile of current lading code.
         *
         * @param code                       lading code.
         * @param phone                      new phone number.
         * @param simpleResultCommonCallback callback from retrofit.
         */
        Call<SimpleResult> updateMobile(String code, String type, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback);

        Call<SimpleResult> updateMobileSender(String code, String type, String phoneSender, CommonCallback<SimpleResult> simpleResultCommonCallback);

        Single<SimpleResult> _phatSml(SMLRequest smlRequest);

        Single<SimpleResult> _huySml(SMLRequest smlRequest);

        Single<VerifyAddressRespone> ddVerifyAddress(VerifyAddress verifyAddress);

        Single<XacMinhRespone> ddCreateVietMapRequest(CreateVietMapRequest createVietMapRequest);

        Single<SimpleResult> ddSreachPhone(PhoneNumber dataRequestPayment);

        Single<XacMinhDiaChiResult> vietmapSearchViTri(Double longitude, Double latitude);

        Single<SimpleResult> ddCall(CallLiveMode callLiveMode);

        Single<SimpleResult> ddQueuChat(RequestQueuChat request);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(List<DeliveryPostman> list);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String toDate,
                                   String routeCode,
                                   Integer deliveryType
        );


    }

    interface OnTabListener {
        /**
         * Event when tab cancel delivery success.
         */

        void getCurrentTab();
    }
}
