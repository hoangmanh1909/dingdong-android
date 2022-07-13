package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.OrderChangeRouteModel;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.RouteResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.OrderChangeRouteRequest;
import com.ems.dingdong.model.request.OrderChangeRouteDingDongManagementRequest;
import com.ems.dingdong.model.response.OrderChangeRouteDingDongManagementResponse;
import com.ems.dingdong.model.response.RouteResponse;

import java.util.List;

import io.reactivex.Single;

public interface RouteConstract {

    interface View extends PresentView<Presenter> {
        /**
         * Show list success.
         */
        void showListSucces(List<RouteResponse> responseList);

        void showListOrderSucces(OrderChangeRouteDingDongManagementResponse responseList);

        /**
         * Show error message.
         */
        void showListError(String message);

        /**
         * Get all list route change.
         */
        void showChangeRouteCommandSucces();

        /**
         * Get all route.
         *
         * @param list list route.
         */
        void showRoute(List<RouteInfo> list);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        String getMode();

        void getChangeRouteOrder(String fromDate, String toDate);

        void showDetailOrder(OrderChangeRouteModel item);

        /**
         * Get records for approve.
         *
         * @param ladingCode  lading code.
         * @param fromDate    from create date.
         * @param toDate      to create date.
         * @param postmanId   current postman id
         * @param routeId     current route id
         * @param poCode      post code.
         * @param statusCode  "N" - receive waited, "Y" - agreed, "F" disagreed, "" - All
         * @param fromRouteId from route id.
         */
        void searchForApproved(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String poCode, String statusCode, Integer fromRouteId);

        /**
         * Get record for cancel
         *
         * @param ladingCode  lading code.
         * @param fromDate    from create date
         * @param toDate      to create date.
         * @param postmanId   current postman id
         * @param routeId     current route id
         * @param poCode      post code
         * @param statusCode  "N" - receive waited, "Y" - agreed, "F" disagreed, "C" - canceled, "" - All
         * @param fromRouteId from route id.
         */
        void searchForCancel(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String poCode, String statusCode, Integer fromRouteId);

        /**
         * Get type route
         *
         * @return 50 - ROUTE_RECEIVED, 60 - ROUTE_DELIVER
         */
        int getTypeRoute();

        /**
         * Approved agree
         *
         * @param id          id of record.
         * @param ladingCode  lading code.
         * @param postmanId   current postman id
         * @param postmanCode current postman code
         * @param poCode      post code
         * @param routeId     current route id
         * @param routeCode   from route code.
         */
        void approvedAgree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode);

        void approveOrder(OrderChangeRouteRequest request);

        /**
         * Approve disagreed.
         *
         * @param id          id of record.
         * @param ladingCode  lading code.
         * @param postmanId   current postman id
         * @param postmanCode current postman code
         * @param poCode      post code
         * @param routeId     current route id
         * @param routeCode   from route code.
         */
        void approvedDisagree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode);

        void rejectOrder(OrderChangeRouteRequest request);

        /**
         * Cancel current postman request.
         *
         * @param id        id of record.
         * @param postmanId current postman id
         */
        void cancel(Integer id, Integer postmanId);

        void cancelOrder(OrderChangeRouteRequest request);

        void setTitleTab(int quantity);

        /**
         * Show detail lad.
         *
         * @param ladingCode lading code.
         */
        void showDetail(String ladingCode);

        /**
         * Show barcode scan screen.
         *
         * @param barCodeCallback
         */
        void showBarcode(BarCodeCallback barCodeCallback);

        /**
         * Get all route by post code
         *
         * @param poCode post code
         */
        void getRouteByPoCode(String poCode);
    }

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getChangeRouteOrder(OrderChangeRouteDingDongManagementRequest request);

        Single<SimpleResult> cancelOrder(OrderChangeRouteRequest request);

        Single<SimpleResult> approveOrder(OrderChangeRouteRequest request);

        Single<SimpleResult> rejectOrder(OrderChangeRouteRequest request);

        /**
         * @see Presenter
         */
        void searchForApproved(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String pooCode, String statusCode, Integer fromRouteId, CommonCallback<SimpleResult> callback);

        /**
         * @see Presenter
         */
        void searchForCancel(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String pooCode, String statusCode, Integer fromRouteId, CommonCallback<SimpleResult> callback);

        /**
         * @see Presenter
         */
        void approvedAgree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode, CommonCallback<SimpleResult> callback);

        /**
         * @see Presenter
         */
        void approvedDisagree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode, CommonCallback<SimpleResult> callback);

        /**
         * @see Presenter
         */
        void cancel(Integer id, Integer postmanId, CommonCallback<SimpleResult> callback);

        /**
         * @see Presenter
         */
        void getRouteByPoCode(String poCode, CommonCallback<RouteInfoResult> callback);

    }

    /**
     * Interface to handle event between 2 tabs received record and send record.
     */
    interface OnItemClickListenner {
        /**
         * Cancel change route from current postman.
         *
         * @param item item
         */
        void onCancelRequestClick(RouteResponse item);

        /**
         * Cancel change route from other postman to.
         *
         * @param item item
         */
        void onCancelClick(RouteResponse item);

        /**
         * Approve change route from other postman
         *
         * @param item
         */
        void onApproveClick(RouteResponse item);

        /**
         * Event to show detail record.
         *
         * @param item
         */
        void onLadingCodeClick(RouteResponse item);
    }

    interface OnItemOrderClickListenner {
        /**
         * Cancel change route from current postman.
         *
         * @param item item
         */
        void onCancelRequestClick(OrderChangeRouteModel item);

        /**
         * Cancel change route from other postman to.
         *
         * @param item item
         */
        void onCancelClick(OrderChangeRouteModel item);

        /**
         * Approve change route from other postman
         *
         * @param item
         */
        void onApproveClick(OrderChangeRouteModel item);

        /**
         * Event to show detail record.
         *
         * @param item
         */
        void onLadingCodeClick(OrderChangeRouteModel item);
    }
}
