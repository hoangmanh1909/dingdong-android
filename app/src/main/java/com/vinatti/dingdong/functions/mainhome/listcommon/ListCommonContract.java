package com.vinatti.dingdong.functions.mainhome.listcommon;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.CommonObjectListResult;

import java.util.ArrayList;

/**
 * The CommonObject Contract
 */
interface ListCommonContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate, CommonCallback<CommonObjectListResult> callback);
        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String route,
                                   String order, CommonCallback<CommonObjectListResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<CommonObject> list);

        void showError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate);
        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String route,
                                   String order);
        void showDetailView(CommonObject commonObject);
        ListCommonPresenter setType(int type);

        int getType();
    }
}



