package com.ems.dingdong.functions.mainhome.address.danhbadichi.danhsach;

import com.core.base.viper.Interactor;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.DanhBaDiaChiContract;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class ListAddressInteractor extends Interactor<ListAddressContract.Presenter>
        implements ListAddressContract.Interactor {

    public ListAddressInteractor(ListAddressContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getListContractAddress(String request) {
        return NetWorkControllerGateWay.getListContractAdress(request);
    }

    @Override
    public Single<SimpleResult> getDetail(String request) {
        return NetWorkControllerGateWay.getDetail(request);
    }
}
