package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.SimpleResult;

public class ChiTietDiaChiContract {
    interface Interactor extends IInteractor<Presenter> {
        void vietmapVerify(String id, String userId, String layer, CommonCallback<SimpleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showMessageRequest(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        AddressListModel getAddressListModel();
        void vietmapVerify(String id, String userId, String layer);
    }
}
