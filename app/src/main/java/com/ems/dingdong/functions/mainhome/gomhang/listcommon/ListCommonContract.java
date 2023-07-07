package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.DichVuMode;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.Mpit;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * The CommonObject Contract
 */
public interface ListCommonContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate,
                                       CommonCallback<SimpleResult> callback);

        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String route,
                                   String order, CommonCallback<CommonObjectListResult> callback);

        void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<SimpleResult> callback);

        Single<SimpleResult> ddGetDichVuMpit();

        Single<SimpleResult> ddQueuChat(RequestQueuChat request);
    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<CommonObject> list);

        void showDichVuMpit(List<DichVuMode> list);

        void showError(String message);

        void showResult(ConfirmAllOrderPostman allOrderPostman,ArrayList<CommonObject> list);

        void showDichVuMPit(ArrayList<CommonObject> list);

        void showLoi(String mess);

        void showAccountChatInAppGetQueueResponse(AccountChatInAppGetQueueResponse response, VnpostOrderInfo vnpostOrderInfo, int type);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showSort(List<CommonObject> list);

        void ddQueuChat(RequestQueuChat request, VnpostOrderInfo vnpostOrderInfo, int type);

        void showDichVu(List<Mpit> list);

        void ddgetDichVuMpit();

        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate,
                                       int type);

        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String route,
                                   String order);

        void showDetailView(CommonObject commonObject);

        ListCommonPresenter setType(int type);

        int getType();

        void confirmAllOrderPostman(ArrayList<CommonObject> list);

        void showBarcode(BarCodeCallback barCodeCallback);

        int getTab();

        int getPositionTab();

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

        String oderCode();

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



