package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
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
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;

import java.util.ArrayList;
import java.util.List;

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

        /**
         * delivery success.
         */
        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> simpleResultCommonCallback);

        /**
         * delivery not success.
         */
        void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback);

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
        void cancelDivided(List<DingDongCancelDividedRequest> request, CommonCallback<SimpleResult> callback);

        void changeRouteInsert(ChangeRouteRequest requests, CommonCallback<SimpleResult> callback);
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

        /**
         * Show image.
         *
         * @param file path file.
         */
        void showImage(String file);

        /**
         * Delete file from server and local.
         */
        void deleteFile();

        /**
         * Show error when server emit error.
         *
         */
        void showError(String message);

        /**
         * Show success code.
         */
        void showSuccess(String code);

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

        /**
         * delivery not success.
         */
        void submitToPNS(String reason, String solution, String note, String deliveryImage, String signCapture);

        /**
         * delivery success.
         */
        void paymentDelivery(String deliveryImage, String signCapture, String newReceiverName,
                             String newVatCode, String relationship);

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
        void cancelDivided(int toRouteId, int toPostmanId, String signCapture, String fileImg);

        void changeRouteInsert(int toRouteId, int toPostmanId, String signCapture, String fileImg);

        /**
         * update ListBaoPhatBangKeFragment when deliver success or not.
         */
        void onTabRefresh();
    }
}
