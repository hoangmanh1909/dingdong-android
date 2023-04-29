package com.ems.dingdong.functions.mainhome.gomhang.sortxacnhantin;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.VietMapOrderCreateBD13DataRequest;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VM_POSTMAN_ROUTE;

import java.util.List;

import io.reactivex.Single;

public interface SortContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> ddLoTrinhVmap(OrderCreateBD13Mode createBD13Mode);

        Single<SimpleResult> ddXacNhanLoTrinhVmap(VM_POSTMAN_ROUTE vm_postman_route);
    }

    interface View extends PresentView<Presenter> {
        void showVmap(List<VietMapOrderCreateBD13DataRequest> mList);
    }


    interface Presenter extends IPresenter<View, Interactor> {
        void ddLoTrinhVmap(OrderCreateBD13Mode createBD13Mode);

        void ddXacNhanLoTrinh(VM_POSTMAN_ROUTE vm_postman_route);

        List<CommonObject> getListSort();
    }
}
