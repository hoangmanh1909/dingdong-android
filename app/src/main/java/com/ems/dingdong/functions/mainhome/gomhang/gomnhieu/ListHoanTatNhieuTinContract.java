package com.ems.dingdong.functions.mainhome.gomhang.gomnhieu;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

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
                                          String toAssignDate, CommonCallback<SimpleResult> callback);

        void getReasonsHoanTat(CommonCallback<SimpleResult> commonCallback);

        void collectAllOrderPostman(List<HoanTatTinRequest> list, CommonCallback<SimpleResult> callback);

        Single<DecodeDiaChiResult> vietmapSearchDecode(String Decode);
    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<CommonObject> list);

        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);

        void showVitringuoinhan(double lat, double lon);
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

        void vietmapDecode(String decode);
    }
}



