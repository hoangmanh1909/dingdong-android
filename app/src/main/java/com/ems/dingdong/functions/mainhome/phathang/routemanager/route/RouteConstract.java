package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.RouteResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.response.RouteResponse;

import java.util.List;

public interface RouteConstract {

    interface View extends PresentView<Presenter> {
        void showListSucces(List<RouteResponse> responseList);

        void showChangeRouteCommandSucces();

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void searchForApproved(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String poCode);

        void searchForCancel(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String poCode);

        int getTypeRoute();

        void approvedAgree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode);

        void approvedDisagree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode);

        void cancel(Integer id, Integer postmanId);

        void setTitleTab(int quantity);

        void showDetail(String ladingCode);

        void showBarcode(BarCodeCallback barCodeCallback);
    }

    interface Interactor extends IInteractor<Presenter> {
        void searchForApproved(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String pooCode, CommonCallback<RouteResult> callback);

        void searchForCancel(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String pooCode, CommonCallback<RouteResult> callback);

        void approvedAgree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode, CommonCallback<SimpleResult> callback);

        void approvedDisagree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode, CommonCallback<SimpleResult> callback);

        void cancel(Integer id, Integer postmanId, CommonCallback<SimpleResult> callback);

    }

    interface OnItemClickListenner {
        void onCancelRequestClick(RouteResponse item);

        void onCancelClick(RouteResponse item);

        void onApproveClick(RouteResponse item);

        void onLadingCodeClick(RouteResponse item);
    }
}
