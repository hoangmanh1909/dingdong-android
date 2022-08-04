package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.VietMapOrderCreateBD13DataRequest;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * The CreateBd13 Contract
 */
public interface CreateBd13Contract {

    interface Interactor extends IInteractor<Presenter> {
        void bD13AddNew(Bd13Create json, CommonCallback<SimpleResult> commonCallback);

        void searchLadingBd13(DingDongGetLadingCreateBD13Request objRequest, CommonCallback<DeliveryPostmanResponse> commonCallback);

        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback);

        void updateMobile(String code, String type, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback);

        Single<SimpleResult> ddLapBD13Vmap(OrderCreateBD13Mode createBD13Mode);
    }

    interface View extends PresentView<Presenter> {
        void showSuccessMessage(String message);

        void showListSuccess(ArrayList<DeliveryPostman> list);

        void showListEmpty();

        void showCallSuccess();

        void showSuccess();

        void showError(String message);

        void showView();

        void showVmap(List<VietMapOrderCreateBD13DataRequest> mList);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void ddLapBD13Vmap(OrderCreateBD13Mode createBD13Mode);

        void showBarcode(BarCodeCallback barCodeCallback);

        void postBD13AddNew(Bd13Create bd13Create);

        void searchLadingBd13(DingDongGetLadingCreateBD13Request objRequest);

        void callForward(String phone, String parcelCode);

        void updateMobile(String phone, String parcelCode);

        ContainerView getContainerView();

        /**
         * Get cancel delivery record.
         *
         * @param postmanCode postman code from UserInfo
         * @param routeCode   route code from RouteInfo
         * @param fromDate    from date.
         * @param toDate      to date
         * @param ladingCode  lading code.
         */
        /**
         * cancel deliver.
         *
         * @param dingDongGetCancelDeliveryRequestList list cancel delivery chosen.
         */
        /**
         * Event refresh nearby tab.
         */
        void onCanceled();

        void cancelDelivery(DingDongCancelDeliveryRequest dingDongGetCancelDeliveryRequestList);

        /**
         * Event set title count.
         */
        void titleChanged(int quantity, int currentSetTab);

        int getCurrentTab();

    }

    interface OnTabListener {
        /**
         * Event when tab cancel delivery success.
         */
        void onCanceledDelivery();

        /**
         * Event when title change.
         */
        void onQuantityChange(int quantity, int currentSetTab);

        int getCurrentTab();
    }
}



