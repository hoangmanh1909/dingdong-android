package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital.ThuPhiMode;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.DeliveryCheckAmountPaymentResult;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DeliverySuccessRequest;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.InfoVerify;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.ChangeRouteRequest;
import com.ems.dingdong.model.request.DeliveryPaymentV2;
import com.ems.dingdong.model.request.DeliveryProductRequest;
import com.ems.dingdong.model.request.DeliveryUnSuccessRequest;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PaypostPaymentRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public interface XacNhanBaoPhatContract {
    interface Interactor extends IInteractor<Presenter> {
        /**
         * Get all reasons.
         */
        void getReasons(CommonCallback<ReasonResult> commonCallback);

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
        void postImage(String path, CommonCallback<UploadSingleResult> callback);

        ///
        void postImageAvatar(String pathAvatar, CommonCallback<UploadSingleResult> callback);

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
        void getPostman(String poCode, int routeId, String routeType, CommonCallback<UserInfoResult> callback);

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
    }

    interface View extends PresentView<Presenter> {
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
        void showSuccess(String code,String id);

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
    }


    interface Presenter extends IPresenter<View, Interactor> {

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

        ///
        void postImageAvatar(String pathAvatar);


        /**
         * delivery not success.
         */
        void submitToPNS(String reason, String solution, String note, String deliveryImage, String authenImage, String signCapture, String EstimateProcessTime);

        /**
         * delivery success.
         */
        void paymentDelivery(String deliveryImage, String imageAuthen, String signCapture, String newReceiverName,
                             String relationship, InfoVerify infoVerify, boolean isCod, long codeEdit);


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
        void onTabRefresh(String data,int mType);

        void deliveryPartial(DeliveryProductRequest request);
    }
}