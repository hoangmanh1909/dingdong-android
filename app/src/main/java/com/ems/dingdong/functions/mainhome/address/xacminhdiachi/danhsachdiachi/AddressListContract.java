package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;

public interface AddressListContract {
    interface Interactor extends IInteractor<Presenter> {
        void vietmapSearch(String address, CommonCallback<XacMinhDiaChiResult> callback);
    }

    interface View extends PresentView<Presenter> {

        void showAddressList(Object object);

        void showError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        Object getObject();

        void showAddressDetail(AddressListModel addressListModel);

        void vietmapSearch(String address);

        int getType();
    }

    public interface OnCloseAuthenAddress {
        void closeAuthorise();
    }
}
