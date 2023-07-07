package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.more.GroupServiceMode;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more.LadingProduct;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital.ThuPhiMode;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRequest;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.DeliveryCheckAmountPaymentResult;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DeliverySuccessRequest;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.InfoVerify;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.model.request.ChangeRouteRequest;
import com.ems.dingdong.model.request.DeliveryPaymentV2;
import com.ems.dingdong.model.request.DeliveryProductRequest;
import com.ems.dingdong.model.request.DeliveryUnSuccessRequest;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PaypostPaymentRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;

public interface XacNhanBaoPhatContract {

    interface Interactor extends IInteractor<Presenter> {
        /**
         * Get all reasons.
         */
        void getReasons(CommonCallback<SimpleResult> commonCallback);

        /**
         * Get all solution when use chose reason.
         *
         * @param code reason code.
         */
        void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback);

        /**
         * Save image to server.
         *
         * @param path path file.
         */
        Observable<UploadSingleResult> postImage(String path);

        Observable<UploadSingleResult> postImageImageSignature(String path);

        ///
        Observable<UploadSingleResult> postImageAvatar(String pathAvatar);

        /**
         * delivery success.
         */
        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> simpleResultCommonCallback);

        /**
         * delivery success.
         */
//        Single<DeliveryCheckAmountPaymentResult> paymentDelivery(List<PaypostPaymentRequest> request);

        Single<DeliveryCheckAmountPaymentResult> checkDeliverySuccess(DeliverySuccessRequest request);

        Single<SimpleResult> paymentV2(DeliveryPaymentV2 request);

        /**
         * delivery not success.
         */
//        void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback);
        void pushToDeliveryUnSuccess(DeliveryUnSuccessRequest request, CommonCallback<SimpleResult> callback);

        /**
         * Get all route by Post code.
         *
         * @param poCode post code. This can be get in UserInfo that has been saved in share pref.
         */
        void getRouteByPoCode(String poCode, CommonCallback<RouteInfoResult> callback);

        /**
         * Get all post man in a route.
         *
         * @param poCode  post code. This can be get in UserInfo that has been saved in share pref.
         * @param routeId route id. This can be get in RouteInfo that has been saved in share pref.
         */
        void getPostman(String poCode, int routeId, String routeType, CommonCallback<SimpleResult> callback);

        /**
         * Change route to another postman.
         *
         * @see DingDongCancelDividedRequest
         */
        //chuyen tuyen
        void cancelDivided(List<DingDongCancelDividedRequest> request, CommonCallback<SimpleResult> callback);

        void changeRouteInsert(ChangeRouteRequest requests, CommonCallback<SimpleResult> callback);

        void deliveryPartial(DeliveryProductRequest request, CommonCallback<SimpleResult> callback);

        Single<DecodeDiaChiResult> vietmapSearchDecode(String Decode);

        Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber,
                                                 String callForwardType, String hotlineNumber,
                                                 String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback);

        Call<SimpleResult> CallForwardEditCOD(String callerNumber, String calleeNumber,
                                              String callForwardType, String hotlineNumber,
                                              String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback);

        Single<SimpleResult> getXaPhuong(BaseRequest request);

        Single<SimpleResult> getQuanHuyen(BaseRequest request);

        Single<SimpleResult> getTinhThanhPho(BaseRequest request);

        Single<SimpleResult> getDanhMucHCC(BaseRequest request);

        Single<SimpleResult> getNhomDanhMucHCC(String request);

        Single<SimpleResult> ddCall(CallLiveMode callLiveMode);
    }

    interface View extends PresentView<Presenter> {
        void showCallLive(String phone);

        void showXaPhuong(List<WardModels> list);

        void showGroupService(List<GroupServiceMode> list);

        void showErrorGroupService(String code, String mess);

        void showGroupServicePA(List<GroupServiceMode> list);

        void showXaPhuongNew(List<WardModels> list);

        void showQuanHuyen(List<DistrictModels> list);

        void showQuanHuyenNew(List<DistrictModels> list);

        /**
         * Show list reasons.
         *
         * @param reasonInfos list reasons.
         */
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);

        /**
         * Show list solution.
         *
         * @param solutionInfos list solution.
         */
        void showSolution(ArrayList<SolutionInfo> solutionInfos);

        /**
         * Show list route.
         *
         * @param routeInfos list route.
         */
        void showRoute(ArrayList<RouteInfo> routeInfos);

        /**
         * Show list postman.
         *
         * @param userInfos list postman.
         */
        void showPostman(ArrayList<UserInfo> userInfos);

        void showPaymentV2Success(String message, String Data);

        void showPaymentV2Error(String message);

        /**
         * Show image.
         *
         * @param file path file.
         */
        void showImage(String file, String path);

        /**
         * Delete file from server and local.
         */
        void deleteFile();

        /**
         * Show error when server emit error.
         */
        void showError(String message);


        void showCheckAmountPaymentError(String message, String amountPP, String amountPNS);

        /**
         * Show success code.
         */
        void showSuccess(String code, String id, String mabg, String mess);

        /**
         * Show change route status.
         *
         * @param message message.
         */
        void showCancelDivided(String message);

        /**
         * update ListBaoPhatBangKeFragment when deliver success or not.
         */
        void finishView();

        List<DeliveryPostman> getItemSelected();

        void showCallError(String message);

        void showCallSuccess(String phone);

        void showCallEdit(String x);

        void showTinhThanhPho(List<ProvinceModels> list);
    }


    interface Presenter extends IPresenter<View, Interactor> {
        void ddCall(CallLiveMode r);

        ContainerView getContainerView();

        void getTinhThanhPho();

        void getDanhMucHCC();

        void getNhomDanhMucHCC(String request);

        void getXaPhuong(int id);

        void getXaPhuongNew(int id);

        void getQuanHuyen(int id);

        void getQuanHuyenNew(int id);

        void callForward(String phone, String parcelCode);

        void callForwardEditCOD(String phone, String parcelCode);

        void vietmapDecode(String decode, int posi);

        /**
         * Get list chosen from ListBaoPhatBangKeFragment.
         *
         * @return list chosen.
         */
        List<DeliveryPostman> getBaoPhatBangke();

        /**
         * Get reason.
         */
        void getReasons();

        /**
         * get solutions.
         */
        void loadSolution(String code);

        /**
         * Save image from server.
         *
         * @param path file path.
         */
        void postImage(String path);

        void postImageImageSignature(String path);

        ///
        void postImageAvatar(String pathAvatar);


        /**
         * delivery not success.
         */
        void submitToPNS(String reason, String solution, String note, String deliveryImage, String authenImage,
                         String signCapture, String EstimateProcessTime, boolean ischeck, String lydo, int idXaPhuong, int idQuanhuyen, String diachinew,
                         String hinhthucphat, String ghichu, String doituong, int ngaydukien, DLVDeliveryUnSuccessRefundRequest dlvDeliveryUnSuccessRefundRequest,
                         int isCollectFeeReturn);

        /**
         * delivery success.
         */
        void paymentDelivery(String deliveryImage, String imageAuthen, String signCapture, String newReceiverName,
                             String relationship, InfoVerify infoVerify, boolean isCod, long codeEdit, String note,
                             boolean IsExchange, String ExchangePODeliveryCode, String ExchangeRouteCode, String ExchangeLadingCode,
                             long ExchangeDeliveryDate, int ExchangeDeliveryTime, List<LadingProduct> ExchangeDetails, String imgAnhHoangTra
                , int idXaphuong, int idQuanHUyen, String idCOD,String imgSignCapture);


        void paymentV2(boolean isAutoUpdateCODAmount);

        /**
         * @see Interactor
         */
        void getRouteByPoCode(String poCode);

        /**
         * @see Interactor
         */
        void getPostman(String poCode, int routeId, String routeType);

        /**
         * @see Interactor
         */
        void cancelDivided(String toPOCode, int toRouteId, int toPostmanId, String signCapture, String fileImg);

        void changeRouteInsert(int toRouteId, int toPostmanId, String signCapture, String fileImg);

        /**
         * update ListBaoPhatBangKeFragment when deliver success or not.
         */
        void onTabRefresh(String data, int mType);

        // phat 1 phan
        void deliveryPartial(DeliveryProductRequest request);
    }
}