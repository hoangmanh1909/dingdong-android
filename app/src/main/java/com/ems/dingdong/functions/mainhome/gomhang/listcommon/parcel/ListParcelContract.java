package com.ems.dingdong.functions.mainhome.gomhang.listcommon.parcel;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.ParcelCodeInfo;

import java.util.List;

/**
 * The ListParcel Contract
 */
interface ListParcelContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        List<ParcelCodeInfo> getListData();
    }
}



