package com.ems.dingdong.functions.mainhome.gomhang.gomnhieu;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * The CommonObject Contract
 */
interface ListHoanTatNhieuTinContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchAllOrderPostmanCollect(String orderPostmanID,
                                          String orderID,
                                          String postmanID,
                                          String status,
                                          String fromAssignDate,
                                          String toAssignDate, CommonCallback<CommonObjectListResult> callback);
        void getReasonsHoanTat(CommonCallback<ReasonResult> commonCallback);

        void collectAllOrderPostman(List<HoanTatTinRequest> list, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<CommonObject> list);

        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void searchAllOrderPostmanCollect(String orderPostmanID,
                                          String orderID,
                                          String postmanID,
                                          String status,
                                          String fromAssignDate,
                                          String toAssignDate);

        void showBarcode(BarCodeCallback barCodeCallback);

        void collectAllOrderPostman(List<HoanTatTinRequest> list);
    }
}



