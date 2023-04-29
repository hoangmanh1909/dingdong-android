package com.ems.dingdong.functions.mainhome.gomhang.sortxacnhantin;

import com.core.base.viper.Interactor;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VM_POSTMAN_ROUTE;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class SortInteractor extends Interactor<SortContract.Presenter>
        implements SortContract.Interactor {
    public SortInteractor(SortContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> ddLoTrinhVmap(OrderCreateBD13Mode createBD13Mode) {
        return NetWorkControllerGateWay.getLoTrinhXacNhanTin(createBD13Mode);
    }

    @Override
    public Single<SimpleResult> ddXacNhanLoTrinhVmap(VM_POSTMAN_ROUTE vm_postman_route) {
        return NetWorkControllerGateWay.getXacNhanLoTrinh(vm_postman_route);

    }
}
