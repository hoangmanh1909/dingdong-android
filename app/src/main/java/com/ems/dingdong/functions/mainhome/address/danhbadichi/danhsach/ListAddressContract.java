package com.ems.dingdong.functions.mainhome.address.danhbadichi.danhsach;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookCreateRequest;
import com.ems.dingdong.model.SimpleResult;

import java.util.List;

import io.reactivex.Single;

public interface ListAddressContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getListContractAddress(String request);

        Single<SimpleResult> getDetail(String request);
    }

    interface View extends PresentView<Presenter> {
        void showAddd(List<DICRouteAddressBookCreateRequest> list);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getListContractAddress(String data);

        void getDetail(String data);

        void showThemDanhBa();

        void showEdit(DICRouteAddressBookCreateRequest v);
    }
}
