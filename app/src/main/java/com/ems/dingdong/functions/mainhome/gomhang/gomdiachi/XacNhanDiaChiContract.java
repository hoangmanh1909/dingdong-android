package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.DichVuMode;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.Mpit;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.AddressModel;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.Values;
import com.ems.dingdong.model.VerifyAddress;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.XacMinhRespone;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.model.response.VerifyAddressRespone;
import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public interface XacNhanDiaChiContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate, CommonCallback<SimpleResult> callback);

        Single<VerifyAddressRespone> ddVerifyAddress(VerifyAddress verifyAddress);

        Single<XacMinhRespone> ddCreateVietMapRequest(CreateVietMapRequest createVietMapRequest);

        Single<SimpleResult> ddSreachPhone(PhoneNumber dataRequestPayment);

        Single<XacMinhDiaChiResult> vietmapSearchViTri(Double longitude, Double latitude);

        Single<SimpleResult> ddGetDichVuMpit();

        Single<SimpleResult> ddQueuChat(RequestQueuChat request);
    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<CommonObject> list);

        void showError(String message);

        void showAddress(Values x);

        void shoSucces(String mess);

        void showDiachi(String x);

        void showList(VpostcodeModel getListVpostV1);

        void showDichVuMPit(ArrayList<CommonObject> list);

        void showDichVuMpit(List<DichVuMode> list);

        void showLoi(String mess);

        void showAccountChatInAppGetQueueResponse(AccountChatInAppGetQueueResponse response, VnpostOrderInfo vnpostOrderInfo, int type);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void ddQueuChat(RequestQueuChat request, VnpostOrderInfo vnpostOrderInfo, int type);

        void showDichVu(List<Mpit> list);

        void ddgetDichVuMpit();

        void vietmapSearch(List<VpostcodeModel> vpostcodeModels);

        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate,
                                       int type);

        XacNhanDiaChiPresenter setType(int type);

        int getType();

        void confirmAllOrderPostman(ArrayList<CommonObject> list, String tenkhachhang);

        void confirmAllOrderPostmanMpit(ArrayList<CommonObject> list, String tenkhachhang, String code);

        void showBarcode(BarCodeCallback barCodeCallback);

        void showConfirmParcelAddress(CommonObject commonObject, ArrayList<ParcelCodeInfo> confirmOrderPostmen);

        void showConfirmParcelAddressNoPostage(CommonObject commonObject);

        void showChiTietHoanThanhTin(CommonObject commonObject);

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


        void getDDVeryAddress(VerifyAddress verifyAddress);

        void ddCreateVietMap(CreateVietMapRequest createVietMapRequest);

        void ddSreachPhone(PhoneNumber dataRequestPayment);

        void showAddressDetail(List<VpostcodeModel> addressListModel, TravelSales ApiTravel);

        void getMapVitri(Double v1, Double v2);
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



