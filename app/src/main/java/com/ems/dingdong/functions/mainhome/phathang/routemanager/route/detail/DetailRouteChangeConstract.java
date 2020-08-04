package com.ems.dingdong.functions.mainhome.phathang.routemanager.route.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ChangeRouteResult;
import com.ems.dingdong.model.DetailLadingCode;

import java.util.List;

interface DetailRouteChangeConstract {
    interface Interactor extends IInteractor<Presenter> {
        /**
         * Get detail record.
         * @param ladingCode lading code
         */
        void getChangeRouteDetail(String ladingCode, CommonCallback<ChangeRouteResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showViewDetail(List<DetailLadingCode> item);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        /**
         * Get detail record.
         */
        void getChangeRouteDetail();
    }
}
