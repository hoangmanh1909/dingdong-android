package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.SimpleResult;

public class AddressListContract {
    interface Interactor extends IInteractor<Presenter> {

    }

    interface View extends PresentView<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {
        Object getObject();

        void showAddressDetail(AddressListModel addressListModel);


    }
}
