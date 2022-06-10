package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.map;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.ReceiverVpostcodeMode;
import com.ems.dingdong.model.SenderVpostcodeMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.TravelSales;

import java.util.List;

import io.reactivex.Single;

public interface MapContract {
    interface Interactor extends IInteractor<Presenter> {

    }
    interface View extends PresentView<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {
        AddressListModel getAddressListModel();

        List<VpostcodeModel> getListVpostcodeModell();

        TravelSales getApiTravel();

    }
}
