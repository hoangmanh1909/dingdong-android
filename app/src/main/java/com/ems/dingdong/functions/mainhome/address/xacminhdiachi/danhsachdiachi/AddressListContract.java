package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;

import java.util.List;

public interface AddressListContract {
    interface Interactor extends IInteractor<Presenter> {

        void vietmapSearchByAddress(String address, CommonCallback<XacMinhDiaChiResult> callback);

        void vietmapSearchByPoint(double longitude, double latitude, CommonCallback<XacMinhDiaChiResult> callback);
    }

    interface View extends PresentView<Presenter> {

        void showAddressList(List<AddressListModel> listObject);

        void showError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void showAddressDetail(AddressListModel addressListModel);

        void vietmapSearch(String address);

        void vietmapSearch();

        int getType();

        String getAddress();
    }

    public interface OnCloseAuthenAddress {
        void closeAuthorise();
    }
}
